package org.attalaya.legacyhelper.controller;

import android.content.Context;

import org.attalaya.legacyhelper.model.Legacy;

import io.realm.Realm;

/**
 * Created by Marcel on 28/03/2015.
 */
public class LegacyController {

    private static LegacyController instance;
    private Realm realm;

    public static LegacyController getInstance(Context context) {
        if (instance==null) {
            instance = new LegacyController(context);
        }
        return instance;
    }

    private LegacyController(Context context) {
        realm = Realm.getInstance(context);
    }

    public Legacy getLegacyById(int id) {
        return realm.where(Legacy.class).equalTo("id", id).findFirst();
    }
}
