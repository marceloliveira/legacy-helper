package org.attalaya.legacyhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.attalaya.legacyhelper.LegacyHelperContract.Legacy;


public class LegacyHelperDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LegacyHelper.db";

    public LegacyHelperDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_LEGACY =
            "CREATE TABLE " + Legacy.TABLE_NAME + " (" +
                    Legacy._ID + " INTEGER PRIMARY KEY," +
                    Legacy.COLUMN_NAME_LEGACY_ID + TEXT_TYPE + COMMA_SEP +
                    Legacy.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    Legacy.COLUMN_NAME_GENDER_LAW + TEXT_TYPE + COMMA_SEP +
                    Legacy.COLUMN_NAME_BLOODLINE_LAW + TEXT_TYPE + COMMA_SEP +
                    Legacy.COLUMN_NAME_HEIR_LAW + TEXT_TYPE + COMMA_SEP +
                    Legacy.COLUMN_NAME_EXEMPLAR_TRAIT + TEXT_TYPE + " )";

    private static final String SQL_DELETE_LEGACYS =
            "DROP TABLE IF EXISTS " + Legacy.TABLE_NAME;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LEGACY);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_LEGACYS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
