package org.attalaya.legacyhelper.controller;

import android.content.Context;

import org.attalaya.legacyhelper.model.Law;
import org.attalaya.legacyhelper.model.Legacy;
import org.attalaya.legacyhelper.model.Trait;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

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

    public RealmResults<Legacy> getAllLegacys() {
        return realm.where(Legacy.class).findAll();
    }

    public String getStringFieldByName(RealmObject item, String field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String value = "";
        if (item!=null) {
            String[] fields = field.split("\\.", 2);
            String methodName = ("" + fields[0].charAt(0)).toUpperCase();
            methodName = "get" + methodName + fields[0].substring(1);
            Method method = item.getClass().getMethod(methodName);
            if (fields.length == 2) {
                RealmObject object = (RealmObject) method.invoke(item);
                value = getStringFieldByName(object, fields[1]);
            } else {
                value = method.invoke(item).toString();
            }
        }
        return value;
    }

    public void createOrUpdateLegacy(int id, String legacyName, Law genderLaw, Law bloodlineLaw, Law heirLaw, Trait exemplarTrait, Law speciesLaw) {

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
        legacy.setSpeciesLaw(speciesLaw);

        realm.copyToRealmOrUpdate(legacy);

        realm.commitTransaction();
    }
}
