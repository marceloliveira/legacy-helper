package org.attalaya.legacyhelper;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.attalaya.legacyhelper.LegacyHelperContract.Legacy;


public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LEGACY_LOADER = 0;
    private ListView legacys;
    private SimpleCursorAdapter mAdapter;
    private static final String[] LEGACY_FIELDS = new String[]{Legacy._ID,Legacy.COLUMN_NAME_NAME,Legacy.COLUMN_NAME_GENDER_LAW,Legacy.COLUMN_NAME_BLOODLINE_LAW,Legacy.COLUMN_NAME_HEIR_LAW};
    private static final String[] LAWS_FIELDS = new String[]{"GENDERLAW","BLOODLINELAW","HEIRLAW"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        legacys = (ListView)findViewById(R.id.legacys);
        mAdapter = new SimpleCursorAdapter(this,R.layout.list_item_legacy,null,new String[]{Legacy._ID,Legacy.COLUMN_NAME_NAME,"GENDERLAW","BLOODLINELAW","HEIRLAW"},new int[]{android.R.id.empty,R.id.legacyListItemName,R.id.legacyListItemGenderLaw,R.id.legacyListItemBloodlineLaw,R.id.legacyListItemHeirLaw},0);
        getLoaderManager().initLoader(LEGACY_LOADER,null,this);
        legacys.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(LEGACY_LOADER,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_new_legacy: openNewLegacy(); return true;
            case R.id.action_settings: return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openNewLegacy() {
        Intent intent = new Intent(this, NewLegacyActivity.class);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, Legacy.CONTENT_URI,
                LEGACY_FIELDS,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        MatrixCursor mc = new MatrixCursor(LAWS_FIELDS);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            mc.addRow(new Object[]{getResources().getStringArray(R.array.gender_law_array)[
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(Legacy.COLUMN_NAME_GENDER_LAW)))],
                    getResources().getStringArray(R.array.bloodline_law_array)[
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex(Legacy.COLUMN_NAME_BLOODLINE_LAW)))],
                    getResources().getStringArray(R.array.heir_law_array)[
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex(Legacy.COLUMN_NAME_HEIR_LAW)))]});
        }
        mAdapter.swapCursor(new MergeCursor(new Cursor[]{cursor,mc}));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }
}
