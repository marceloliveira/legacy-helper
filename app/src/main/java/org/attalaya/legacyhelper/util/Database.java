package org.attalaya.legacyhelper.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.attalaya.legacyhelper.R;
import org.attalaya.legacyhelper.model.Law;
import org.attalaya.legacyhelper.model.LawType;
import org.attalaya.legacyhelper.model.Package;
import org.attalaya.legacyhelper.model.Skill;
import org.attalaya.legacyhelper.model.Trait;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Marcel on 04/05/2015.
 */
public class Database {

    public static final String DATABASE_VERSION = "0.1";

    public static String populateDataRealm(Context context, String currentVersion) {
        Realm realm = Realm.getInstance(context, "data.realm");

        RealmResults<Law> r = realm.where(Law.class).findAll();
        for (Law o: r) {
            Log.d("LEGACYHELPER",o.getId()+" "+o.getName()+" "+o.getPack().getName()+" "+o.getType());
        }

        if (DATABASE_VERSION.equals(currentVersion)) {
            return DATABASE_VERSION;
        }
        Resources res = context.getResources();

        realm.beginTransaction();

        realm.clear(Package.class);
        int i = 1;
        String[] packages = res.getStringArray(R.array.game_package_array);
        for (String s: packages) {
            Package pack = new Package();
            pack.setId(i);
            pack.setName(s);
            realm.copyToRealm(pack);
            i++;
        }

        realm.clear(Law.class);
        i = 1;
        String[] laws = res.getStringArray(R.array.base_gender_law_array);
        for (String s: laws) {
            Law law = new Law();
            law.setId(i);
            law.setName(s);
            law.setType(LawType.GENDER.ordinal());
            law.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.base_package)).findFirst());
            realm.copyToRealm(law);
            i++;
        }
        laws = res.getStringArray(R.array.base_bloodline_law_array);
        for (String s: laws) {
            Law law = new Law();
            law.setId(i);
            law.setName(s);
            law.setType(LawType.BLOODLINE.ordinal());
            law.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.base_package)).findFirst());
            realm.copyToRealm(law);
            i++;
        }
        laws = res.getStringArray(R.array.base_heir_law_array);
        for (String s: laws) {
            Law law = new Law();
            law.setId(i);
            law.setName(s);
            law.setType(LawType.HEIR.ordinal());
            law.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.base_package)).findFirst());
            realm.copyToRealm(law);
            i++;
        }
        laws = res.getStringArray(R.array.ep01_species_law_array);
        for (String s: laws) {
            Law law = new Law();
            law.setId(i);
            law.setName(s);
            law.setType(LawType.SPECIES.ordinal());
            law.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.ep01_package)).findFirst());
            realm.copyToRealm(law);
            i++;
        }

        realm.clear(Trait.class);
        i=1;
        String[] mTraits = res.getStringArray(R.array.base_m_trait_array);
        String[] fTraits = res.getStringArray(R.array.base_f_trait_array);
        for (int j=0;j<mTraits.length;j++) {
            Trait trait = new Trait();
            trait.setId(i);
            trait.setmName(mTraits[j]);
            trait.setfName(fTraits[j]);
            trait.setChild(false);
            trait.setTeen(false);
            trait.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.base_package)).findFirst());
            realm.copyToRealm(trait);
            i++;
        }
        mTraits = res.getStringArray(R.array.gp01_m_trait_array);
        fTraits = res.getStringArray(R.array.gp01_f_trait_array);
        for (int j=0;j<mTraits.length;j++) {
            Trait trait = new Trait();
            trait.setId(i);
            trait.setmName(mTraits[j]);
            trait.setfName(fTraits[j]);
            trait.setChild(false);
            trait.setTeen(false);
            trait.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.gp01_package)).findFirst());
            realm.copyToRealm(trait);
            i++;
        }
        int[] age = res.getIntArray(R.array.child_trait_array);
        for (int c: age) {
            Trait trait = realm.where(Trait.class).equalTo("id", c+1).findFirst();
            trait.setChild(true);
        }
        age = res.getIntArray(R.array.teen_trait_array);
        for (int c: age) {
            Trait trait = realm.where(Trait.class).equalTo("id", c+1).findFirst();
            trait.setTeen(true);
        }
        String[] pair = res.getStringArray(R.array.incompatible_trait_array);
        for (String p: pair) {
            String[] traits = p.split(",");
            Trait trait1 = realm.where(Trait.class).equalTo("id", Integer.parseInt(traits[0])+1).findFirst();
            Trait trait2 = realm.where(Trait.class).equalTo("id", Integer.parseInt(traits[1])+1).findFirst();
            trait1.getIncompatibleTraits().add(trait2);
            trait2.getIncompatibleTraits().add(trait1);
        }

        realm.clear(Skill.class);
        i=1;
        String[] skills = res.getStringArray(R.array.base_child_skill_array);
        for (int j=0;j<skills.length;j++) {
            Skill skill = new Skill();
            skill.setId(i);
            skill.setName(skills[j]);
            skill.setChild(true);
            skill.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.base_package)).findFirst());
            realm.copyToRealm(skill);
            i++;
        }
        skills = res.getStringArray(R.array.base_adult_skill_array);
        for (int j=0;j<skills.length;j++) {
            Skill skill = new Skill();
            skill.setId(i);
            skill.setName(skills[j]);
            skill.setChild(false);
            skill.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.base_package)).findFirst());
            realm.copyToRealm(skill);
            i++;
        }
        skills = res.getStringArray(R.array.gp01_adult_skill_array);
        for (int j=0;j<skills.length;j++) {
            Skill skill = new Skill();
            skill.setId(i);
            skill.setName(skills[j]);
            skill.setChild(false);
            skill.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.gp01_package)).findFirst());
            realm.copyToRealm(skill);
            i++;
        }
        skills = res.getStringArray(R.array.ep01_adult_skill_array);
        for (int j=0;j<skills.length;j++) {
            Skill skill = new Skill();
            skill.setId(i);
            skill.setName(skills[j]);
            skill.setChild(false);
            skill.setPack(realm.where(Package.class).equalTo("name", res.getString(R.string.ep01_package)).findFirst());
            realm.copyToRealm(skill);
            i++;
        }
        pair = res.getStringArray(R.array.child_unlock_skill_array);
        for (String p: pair) {
            String[] unlockskills = p.split(",");
            Skill childSkill = realm.where(Skill.class).equalTo("id", Integer.parseInt(unlockskills[0])+1).findFirst();
            Skill adultSkill = realm.where(Skill.class).equalTo("id", Integer.parseInt(unlockskills[1])+1).findFirst();
            adultSkill.setChildUnlock(childSkill);
        }

        realm.commitTransaction();

        return DATABASE_VERSION;
    }
}
