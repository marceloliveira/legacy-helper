package org.attalaya.legacyhelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class LegacyHelperContentProvider extends ContentProvider {

    //authority org.attalaya.legacyhelper.provider

    private LegacyHelperDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new LegacyHelperDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        String table = LegacyHelperContract.Legacy.TABLE_NAME;
        return dbHelper.getReadableDatabase().query(table,strings,s,strings2,null,null,s2);
    }

    @Override
    public String getType(Uri uri) {
        String table = LegacyHelperContract.Legacy.TABLE_NAME;
        return "vnd.android.cursor.dir/vnd.org.attalaya.legacyprovider."+table;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        String table = LegacyHelperContract.Legacy.TABLE_NAME;
        long id = dbHelper.getWritableDatabase().insert(table,null,contentValues);
        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        String table = LegacyHelperContract.Legacy.TABLE_NAME;
        return dbHelper.getWritableDatabase().delete(table,s,strings);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        String table = LegacyHelperContract.Legacy.TABLE_NAME;
        return dbHelper.getWritableDatabase().update(table,contentValues,s,strings);
    }
}
