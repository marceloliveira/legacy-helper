package org.attalaya.legacyhelper;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.attalaya.legacyhelper.model.Legacy;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class LegacyListFragment extends Fragment {

    protected ActionMode mActionMode;
    protected ListAdapter mAdapter;
    protected ListView legacys;
    protected Legacy selectedLegacy;
    protected Realm realm;
    private static final String[] LEGACY_FIELDS = new String[]{LegacyHelperContract.Legacy._ID, LegacyHelperContract.Legacy.COLUMN_NAME_NAME, LegacyHelperContract.Legacy.COLUMN_NAME_GENDER_LAW, LegacyHelperContract.Legacy.COLUMN_NAME_BLOODLINE_LAW, LegacyHelperContract.Legacy.COLUMN_NAME_HEIR_LAW};

    public LegacyListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getInstance(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_legacy_list, container, false);
        setHasOptionsMenu(true);
        legacys = (ListView)rootView.findViewById(R.id.legacys);
        legacys.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        legacys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(parent.getContext(), LegacyActivity.class);
                intent.putExtra(LegacyHelperContract.Legacy._ID, id);
                startActivity(intent);
            }
        });
        legacys.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                parent.setSelection(position);
                ((ListView) parent).setItemChecked(position, true);
                selectedLegacy = (Legacy)parent.getItemAtPosition(position);
                view.setSelected(true);
                return true;
            }
        });
        mAdapter = new LegacyAdapter(getActivity(),realm.allObjects(Legacy.class));
        legacys.setAdapter(mAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View addButton = rootView.findViewById(R.id.add_button);
            addButton.setOutlineProvider(new ViewOutlineProvider() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
                    newLegacy();
                }
            });
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.legacy_list, menu);
        MenuItem newLegacy = menu.findItem(R.id.action_new_legacy);
        if (newLegacy!=null) {
            newLegacy.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            newLegacy.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    newLegacy();
                    return true;
                }
            });
        }
    }

    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

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
                    editLegacy();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            legacys.setItemChecked(-1,true);
            mActionMode = null;
        }
    };

    private void editLegacy() {
        getFragmentManager().beginTransaction().replace(R.id.container, EditLegacyFragment.newInstance("edit",selectedLegacy.getId())).addToBackStack(null).commit();
        getFragmentManager().executePendingTransactions();
    }

    public void newLegacy() {
        getFragmentManager().beginTransaction().replace(R.id.container, EditLegacyFragment.newInstance("new",-1)).addToBackStack(null).commit();
        getFragmentManager().executePendingTransactions();
    }

    /*protected class LegacyLoaderTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            MatrixCursor cursor = new MatrixCursor(LEGACY_FIELDS);
            String[] queryFields = Arrays.copyOf(LEGACY_FIELDS,LEGACY_FIELDS.length+1);
            queryFields[queryFields.length-1] = Legacy.COLUMN_NAME_EXEMPLAR_TRAIT;
            Cursor mCursor = getActivity().getContentResolver().query(Legacy.CONTENT_URI, queryFields, null, null, null);
            Resources res = getActivity().getApplicationContext().getResources();
            String[] genderlaws = res.getStringArray(R.array.gender_law_array);
            String[] bloodlinelaws = res.getStringArray(R.array.bloodline_law_array);
            String[] heirlaws = res.getStringArray(R.array.heir_law_array);
            String[] traits = res.getStringArray(R.array.m_trait_array);
            if (mCursor.getCount()>0) {
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    int legacyId = mCursor.getInt(0);
                    String legacyName = mCursor.getString(1);
                    String genderlaw = genderlaws[mCursor.getInt(2)];
                    String bloodlinelaw = bloodlinelaws[mCursor.getInt(3)];
                    String heirlaw = heirlaws[mCursor.getInt(4)];
                    if (mCursor.getInt(4) == 7) {
                        String exemplarTrait = mCursor.getInt(5) != -1 ? traits[mCursor.getInt(5)] : "";
                        heirlaw += " - "+exemplarTrait;
                    }
                    cursor.addRow(new Object[]{legacyId, legacyName, genderlaw, bloodlinelaw, heirlaw});
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
    }*/

    protected class LegacyAdapter extends RealmBaseAdapter<Legacy> implements ListAdapter {

        private class LegacyViewHolder {
            TextView name;
            TextView genderlaw;
            TextView bloodlinelaw;
            TextView heirlaw;
        }

        public LegacyAdapter(Context context,
                             RealmResults<Legacy> realmResults) {
            super(context, realmResults, true);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LegacyViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_legacy,
                        parent, false);
                viewHolder = new LegacyViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.legacyListItemName);
                viewHolder.genderlaw = (TextView) convertView.findViewById(R.id.legacyListItemGenderLaw);
                viewHolder.bloodlinelaw = (TextView) convertView.findViewById(R.id.legacyListItemBloodlineLaw);
                viewHolder.heirlaw = (TextView) convertView.findViewById(R.id.legacyListItemHeirLaw);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (LegacyViewHolder) convertView.getTag();
            }

            Legacy item = realmResults.get(position);
            viewHolder.name.setText(item.getName());
            viewHolder.genderlaw.setText(item.getGenderLaw().getName());
            viewHolder.bloodlinelaw.setText(item.getBloodlineLaw().getName());
            viewHolder.heirlaw.setText(item.getHeirLaw().getName()+(item.getExemplarTrait()!=null?" - "+item.getExemplarTrait().getmName():""));
            return convertView;
        }

        public RealmResults<Legacy> getRealmResults() {
            return realmResults;
        }
    }
}
