package org.attalaya.legacyhelper;

import android.net.Uri;
import android.provider.BaseColumns;

public final class LegacyHelperContract {

    public LegacyHelperContract() {
    }

    public static abstract class Legacy implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://org.attalaya.legacyhelper.provider/legacy");
        public static final String TABLE_NAME = "legacy";
        public static final String COLUMN_NAME_LEGACY_ID = "id_legacy";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_GENDER_LAW = "genderlaw";
        public static final String COLUMN_NAME_BLOODLINE_LAW = "bloodlinelaw";
        public static final String COLUMN_NAME_HEIR_LAW = "heirlaw";
        public static final String COLUMN_NAME_EXEMPLAR_TRAIT = "exemplartrait";
    }

}