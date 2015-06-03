package org.attalaya.legacyhelper.controller;

import android.content.Context;

import org.attalaya.legacyhelper.model.Law;
import org.attalaya.legacyhelper.model.Legacy;
import org.attalaya.legacyhelper.model.Trait;

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

    public void createOrUpdateLegacy(int id, String legacyName, Law genderLaw, Law bloodlineLaw, Law heirLaw, Trait exemplarTrait) {

        realm.beginTransaction();

        Legacy legacy = new Legacy();
        if (realm.where(Legacy.class).equalTo("id", id).findFirst()==null) {
            legacy.setId(realm.allObjects(Legacy.class).size() + 1);
        } else {
            legacy.setId(id);
        }
        legacy.setName(legacyName);
        legacy.setGenderLaw(genderLaw);
        legacy.setBloodlineLaw(bloodlineLaw);
        legacy.setHeirLaw(heirLaw);
        legacy.setExemplarTrait(exemplarTrait);

        realm.copyToRealmOrUpdate(legacy);

        realm.commitTransaction();
    }
}
