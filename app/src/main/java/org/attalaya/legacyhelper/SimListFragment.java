package org.attalaya.legacyhelper;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Outline;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.attalaya.legacyhelper.LegacyHelperContract.Sim;

public class SimListFragment extends Fragment {

    protected ActionMode mActionMode;
    protected SimpleCursorAdapter mAdapter;
    protected ListView sims;
    protected long legacyId;
    private static final String[] SIM_FIELDS = new String[]{Sim._ID, Sim.COLUMN_NAME_TYPE, Sim.COLUMN_NAME_NAME, Sim.COLUMN_NAME_GENERATION, Sim.COLUMN_NAME_GENDER};

    public SimListFragment() {
    }

    public static SimListFragment newInstance(long legacyId) {
        SimListFragment f = new SimListFragment();
        f.legacyId = legacyId;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sim_list, container, false);
        setHasOptionsMenu(true);
        sims = (ListView)rootView.findViewById(R.id.sims);
        sims.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        sims.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(parent.getContext(), LegacyActivity.class);
                intent.putExtra(Sim._ID, id);
                startActivity(intent);
            }
        });
        sims.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                parent.setSelection(position);
                ((ListView) parent).setItemChecked(position, true);
                view.setSelected(true);
                return true;
            }
        });
        mAdapter = new SimpleCursorAdapter(getActivity(),R.layout.list_item_sim,null,SIM_FIELDS,new int[]{android.R.id.empty,R.id.simListItemType,R.id.simListItemName,R.id.simListItemGeneration,R.id.simListItemGender},0);
        SimLoaderTask task = new SimLoaderTask();
        task.execute();
        sims.setAdapter(mAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View addButton = rootView.findViewById(R.id.add_button);
            addButton.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int diameter = getResources().getDimensionPixelSize(R.dimen.floating_action_button_diameter);
                    outline.setOval(0, 0, diameter, diameter);
                }
            });
            addButton.setClipToOutline(true);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createSim();
                }
            });
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SimLoaderTask task = new SimLoaderTask();
        task.execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.legacy_list, menu);
        MenuItem newLegacy = menu.findItem(R.id.action_new_legacy);
        if (newLegacy!=null) {
            newLegacy.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_legacy_list_item, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit_legacy:
                    createSim();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            sims.setItemChecked(-1, true);
            mActionMode = null;
        }
    };

    private void createSim() {
        getFragmentManager().beginTransaction().replace(R.id.container, CreateSimFragment.newInstance("")).addToBackStack(null).commit();
        getFragmentManager().executePendingTransactions();
    }

    protected class SimLoaderTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            String[] simFields = new String[]{Sim._ID, Sim.COLUMN_NAME_TYPE, Sim.COLUMN_NAME_NAME, Sim.COLUMN_NAME_SURNAME, Sim.COLUMN_NAME_GENERATION, Sim.COLUMN_NAME_GENDER};
            MatrixCursor cursor = new MatrixCursor(simFields);
            Cursor mCursor = getActivity().getContentResolver().query(Sim.CONTENT_URI, null, null, null, null);
            Resources res = getActivity().getApplicationContext().getResources();
            String[] types = res.getStringArray(R.array.sim_type_array);
            if (mCursor.getCount()>0) {
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    int simId = mCursor.getInt(0);
                    String simType = types[Integer.parseInt(mCursor.getString(1))];
                    String simName = mCursor.getString(2)+" "+mCursor.getString(3);
                    String simGeneration = "Generation "+mCursor.getString(4);
                    String simGender = mCursor.getString(5);
                    cursor.addRow(new Object[]{simId, simType, simName, simGeneration, simGender});
                    mCursor.moveToNext();
                }
            }
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mAdapter.swapCursor(cursor);
        }
    }
}
