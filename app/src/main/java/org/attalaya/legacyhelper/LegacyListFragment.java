package org.attalaya.legacyhelper;

import android.app.Fragment;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.attalaya.legacyhelper.LegacyHelperContract.Legacy;

public class LegacyListFragment extends Fragment {

    protected SimpleCursorAdapter mAdapter;
    private static final String[] LEGACY_FIELDS = new String[]{Legacy._ID, Legacy.COLUMN_NAME_NAME, Legacy.COLUMN_NAME_GENDER_LAW, Legacy.COLUMN_NAME_BLOODLINE_LAW, Legacy.COLUMN_NAME_HEIR_LAW, Legacy.COLUMN_NAME_EXEMPLAR_TRAIT};

    public LegacyListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_legacy_list, container, false);
        setHasOptionsMenu(true);
        ListView legacys = (ListView)rootView.findViewById(R.id.legacys);
        legacys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mAdapter = new SimpleCursorAdapter(getActivity(),R.layout.list_item_legacy,null,LEGACY_FIELDS,new int[]{android.R.id.empty,R.id.legacyListItemName,R.id.legacyListItemGenderLaw,R.id.legacyListItemBloodlineLaw,R.id.legacyListItemHeirLaw,R.id.legacyListItemExemplarTrait},0);
        LegacyLoaderTask task = new LegacyLoaderTask();
        task.execute();
        legacys.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LegacyLoaderTask task = new LegacyLoaderTask();
        task.execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_legacy_list, menu);
        menu.findItem(R.id.action_new_legacy).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    protected class LegacyLoaderTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            MatrixCursor cursor = new MatrixCursor(LEGACY_FIELDS);
            Cursor mCursor = getActivity().getContentResolver().query(Legacy.CONTENT_URI,LEGACY_FIELDS,null,null,null);
            Resources res = getActivity().getApplicationContext().getResources();
            String[] genderlaws = res.getStringArray(R.array.gender_law_array);
            String[] bloodlinelaws = res.getStringArray(R.array.bloodline_law_array);
            String[] heirlaws = res.getStringArray(R.array.heir_law_array);
            String[] traits = res.getStringArray(R.array.m_trait_array);
            if (mCursor.getCount()>0) {
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    cursor.addRow(new Object[]{mCursor.getInt(0),mCursor.getString(1),genderlaws[mCursor.getInt(2)],bloodlinelaws[mCursor.getInt(3)],heirlaws[mCursor.getInt(4)],mCursor.getInt(5)==-1?"":traits[mCursor.getInt(5)]});
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
