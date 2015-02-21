package org.attalaya.legacyhelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class LegacyHelperContentProvider extends ContentProvider {

    //authority org.attalaya.legacyhelper.provider
    private static final UriMatcher sUriMatcher = new UriMatcher(0);
    private LegacyHelperDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new LegacyHelperDbHelper(getContext());
        sUriMatcher.addURI("org.attalaya.legacyhelper.provider", LegacyHelperContract.Legacy.TABLE_NAME, 1);
        sUriMatcher.addURI("org.attalaya.legacyhelper.provider", LegacyHelperContract.Sim.TABLE_NAME, 2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        String table = "";
        switch (sUriMatcher.match(uri)) {
            case 1: table = LegacyHelperContract.Legacy.TABLE_NAME; break;
            case 2: table = LegacyHelperContract.Sim.TABLE_NAME; break;
        }
        return dbHelper.getReadableDatabase().query(table,strings,s,strings2,null,null,s2);
    }

    @Override
    public String getType(Uri uri) {
        String table = "";
        switch (sUriMatcher.match(uri)) {
            case 1: table = LegacyHelperContract.Legacy.TABLE_NAME; break;
            case 2: table = LegacyHelperContract.Sim.TABLE_NAME; break;
        }
        return "vnd.android.cursor.dir/vnd.org.attalaya.legacyprovider."+table;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        String table = "";
        switch (sUriMatcher.match(uri)) {
            case 1: table = LegacyHelperContract.Legacy.TABLE_NAME; break;
            case 2: table = LegacyHelperContract.Sim.TABLE_NAME; break;
        }
        long id = dbHelper.getWritableDatabase().insert(table,null,contentValues);
        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        String table = "";
        switch (sUriMatcher.match(uri)) {
            case 1: table = LegacyHelperContract.Legacy.TABLE_NAME; break;
            case 2: table = LegacyHelperContract.Sim.TABLE_NAME; break;
        }
        return dbHelper.getWritableDatabase().delete(table,s,strings);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        String table = "";
        switch (sUriMatcher.match(uri)) {
            case 1: table = LegacyHelperContract.Legacy.TABLE_NAME; break;
            case 2: table = LegacyHelperContract.Sim.TABLE_NAME; break;
        }
        return dbHelper.getWritableDatabase().update(table,contentValues,s,strings);
    }
}
