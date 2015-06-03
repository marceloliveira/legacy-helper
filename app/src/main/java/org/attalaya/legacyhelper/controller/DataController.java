package org.attalaya.legacyhelper.controller;

import android.content.Context;
import android.preference.PreferenceManager;

import org.attalaya.legacyhelper.R;
import org.attalaya.legacyhelper.model.Aspiration;
import org.attalaya.legacyhelper.model.Law;
import org.attalaya.legacyhelper.model.LawType;
import org.attalaya.legacyhelper.model.Package;
import org.attalaya.legacyhelper.model.Skill;
import org.attalaya.legacyhelper.model.Species;
import org.attalaya.legacyhelper.model.Trait;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Marcel on 11/05/2015.
 */
public class DataController {

    private static DataController instance;
    private Realm realm;
    private Context context;

    public static DataController getInstance(Context context) {
        if (instance==null) {
            instance = new DataController(context);
        }
        return instance;
    }

    private DataController(Context context) {
        this.context = context;
        this.realm = Realm.getInstance(this.context,"data.realm");
    }

    private <E extends RealmObject> RealmQuery<E> getQuery(Class<E> type) {
        RealmQuery<E> result = realm.where(type).beginGroup().equalTo("pack.name", context.getString(R.string.base_package));
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("gp01",false)) {
            result.or().equalTo("pack.name", this.context.getString(R.string.gp01_package));
        }
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("ep01",false)) {
            result.or().equalTo("pack.name", this.context.getString(R.string.ep01_package));
        }
        return result.endGroup();
    }

    public RealmResults<Law> getGenderLaws() {
        return getQuery(Law.class).equalTo("type", LawType.GENDER.ordinal()).findAll();
    }

    public RealmResults<Law> getBloodlineLaws() {
        return getQuery(Law.class).equalTo("type", LawType.BLOODLINE.ordinal()).findAll();
    }

    public RealmResults<Law> getHeirLaws() {
        return getQuery(Law.class).equalTo("type", LawType.HEIR.ordinal()).findAll();
    }

    public RealmResults<Law> getSpeciesLaws() {
        return getQuery(Law.class).equalTo("type", LawType.SPECIES.ordinal()).findAll();
    }

    public RealmResults<Skill> getSkills() {
        return getQuery(Skill.class).findAll();
    }

    public RealmResults<Trait> getTraits() {
        return getQuery(Trait.class).findAll();
    }

    public String getDefaultName(RealmObject item) {
        if (item instanceof Package) {
            return ((Package)item).getName();
        } else if (item instanceof Law) {
            return ((Law)item).getName();
        } else if (item instanceof Species) {
            return ((Species)item).getName();
        } else if (item instanceof Skill) {
            return ((Skill)item).getName();
        } else if (item instanceof Trait) {
            return ((Trait)item).getmName();
        } else if (item instanceof Aspiration) {
            return ((Aspiration)item).getmName();
        } else {
            return "";
        }
    }

    public boolean equalsObjects(RealmObject item1, RealmObject item2) {
        if (item1.getClass().equals(item2.getClass())) {
            if (item1 instanceof Package) {
                return ((Package)item1).getId() == ((Package)item2).getId();
            } else if (item1 instanceof Law) {
                return ((Law)item1).getId() == ((Law)item2).getId();
            } else if (item1 instanceof Species) {
                return ((Species)item1).getId() == ((Species)item2).getId();
            } else if (item1 instanceof Skill) {
                return ((Skill)item1).getId() == ((Skill)item2).getId();
            } else if (item1 instanceof Trait) {
                return ((Trait)item1).getId() == ((Trait)item2).getId();
            } else if (item1 instanceof Aspiration) {
                return ((Aspiration)item1).getId() == ((Aspiration)item2).getId();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
