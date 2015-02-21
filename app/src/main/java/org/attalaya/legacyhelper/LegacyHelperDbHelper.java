package org.attalaya.legacyhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.attalaya.legacyhelper.LegacyHelperContract.Legacy;
import org.attalaya.legacyhelper.LegacyHelperContract.Sim;
import org.attalaya.legacyhelper.LegacyHelperContract.SimAspiration;
import org.attalaya.legacyhelper.LegacyHelperContract.SimSkill;


public class LegacyHelperDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "LegacyHelper.db";

    public LegacyHelperDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_LEGACY =
            "CREATE TABLE " + Legacy.TABLE_NAME + " (" +
                    Legacy._ID + " INTEGER PRIMARY KEY," +
                    Legacy.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    Legacy.COLUMN_NAME_GENDER_LAW + TEXT_TYPE + COMMA_SEP +
                    Legacy.COLUMN_NAME_BLOODLINE_LAW + TEXT_TYPE + COMMA_SEP +
                    Legacy.COLUMN_NAME_HEIR_LAW + TEXT_TYPE + COMMA_SEP +
                    Legacy.COLUMN_NAME_EXEMPLAR_TRAIT + TEXT_TYPE + " )";
    private static final String SQL_DELETE_LEGACY =
            "DROP TABLE IF EXISTS " + Legacy.TABLE_NAME;

    private static final String SQL_CREATE_SIM =
            "CREATE TABLE " + Sim.TABLE_NAME + " (" +
                    Sim._ID + " INTEGER PRIMARY KEY," +
                    Sim.COLUMN_NAME_LEGACY + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_SURNAME + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_GENDER + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_GENERATION + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_AGE + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_CHILD_TRAIT + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_TEEN_TRAIT + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_ADULT_TRAIT + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_DEATH + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_MEMORIALIZED + TEXT_TYPE + COMMA_SEP +
                    Sim.COLUMN_NAME_ANTI_AGING + TEXT_TYPE + " )";
    private static final String SQL_DELETE_SIM =
            "DROP TABLE IF EXISTS " + Sim.TABLE_NAME;

    private static final String SQL_CREATE_SIMSKILL =
            "CREATE TABLE " + SimSkill.TABLE_NAME + " (" +
                    SimSkill._ID + " INTEGER PRIMARY KEY," +
                    SimSkill.COLUMN_NAME_SIM + TEXT_TYPE + COMMA_SEP +
                    SimSkill.COLUMN_NAME_SKILL + TEXT_TYPE + COMMA_SEP +
                    SimSkill.COLUMN_NAME_LEVEL + TEXT_TYPE + " )";
    private static final String SQL_DELETE_SIMSKILL =
            "DROP TABLE IF EXISTS " + SimSkill.TABLE_NAME;

    private static final String SQL_CREATE_SIMASPIRATION =
            "CREATE TABLE " + SimAspiration.TABLE_NAME + " (" +
                    SimAspiration._ID + " INTEGER PRIMARY KEY," +
                    SimAspiration.COLUMN_NAME_SIM + TEXT_TYPE + COMMA_SEP +
                    SimAspiration.COLUMN_NAME_ASPIRATION + TEXT_TYPE + COMMA_SEP +
                    SimAspiration.COLUMN_NAME_LEVEL + TEXT_TYPE + " )";
    private static final String SQL_DELETE_SIMASPIRATION =
            "DROP TABLE IF EXISTS " + SimAspiration.TABLE_NAME;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LEGACY);
        db.execSQL(SQL_CREATE_SIM);
        db.execSQL(SQL_CREATE_SIMSKILL);
        db.execSQL(SQL_CREATE_SIMASPIRATION);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_LEGACY);
        db.execSQL(SQL_DELETE_SIM);
        db.execSQL(SQL_DELETE_SIMSKILL);
        db.execSQL(SQL_DELETE_SIMASPIRATION);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
