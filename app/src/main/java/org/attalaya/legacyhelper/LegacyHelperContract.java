package org.attalaya.legacyhelper;

import android.net.Uri;
import android.provider.BaseColumns;

public final class LegacyHelperContract {

    public LegacyHelperContract() {
    }

    public static abstract class Legacy implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://org.attalaya.legacyhelper.provider/legacy");
        public static final String TABLE_NAME = "legacy";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_GENDER_LAW = "genderlaw";
        public static final String COLUMN_NAME_BLOODLINE_LAW = "bloodlinelaw";
        public static final String COLUMN_NAME_HEIR_LAW = "heirlaw";
        public static final String COLUMN_NAME_EXEMPLAR_TRAIT = "exemplartrait";
    }

    public static abstract class Sim implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://org.attalaya.legacyhelper.provider/sim");
        public static final String TABLE_NAME = "sim";
        public static final String COLUMN_NAME_LEGACY = "legacy";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_GENERATION = "generation";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SURNAME = "surname";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_CHILD_TRAIT = "childtrait";
        public static final String COLUMN_NAME_TEEN_TRAIT = "teentrait";
        public static final String COLUMN_NAME_ADULT_TRAIT = "adulttrait";
        public static final String COLUMN_NAME_DEATH = "death";
        public static final String COLUMN_NAME_MEMORIALIZED = "memorialized";
        public static final String COLUMN_NAME_ANTI_AGING = "antiaging";
    }

    public static abstract class SimSkill implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://org.attalaya.legacyhelper.provider/simskill");
        public static final String TABLE_NAME = "simskill";
        public static final String COLUMN_NAME_SIM = "sim";
        public static final String COLUMN_NAME_SKILL = "skill";
        public static final String COLUMN_NAME_LEVEL = "level";
    }

    public static abstract class SimAspiration implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://org.attalaya.legacyhelper.provider/simaspiration");
        public static final String TABLE_NAME = "simaspiration";
        public static final String COLUMN_NAME_SIM = "sim";
        public static final String COLUMN_NAME_ASPIRATION = "skill";
        public static final String COLUMN_NAME_LEVEL = "level";
    }

}