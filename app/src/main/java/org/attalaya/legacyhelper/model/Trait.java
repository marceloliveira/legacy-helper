package org.attalaya.legacyhelper.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Marcel on 07/03/2015.
 */
public class Trait extends RealmObject {

    @PrimaryKey
    private int id;
    private String maleName;
    private String femaleName;
    private Package pack;
    private RealmList<Trait> incompatibleTraits;
    private boolean child;
    private boolean teen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<Trait> getIncompatibleTraits() {
        return incompatibleTraits;
    }

    public void setIncompatibleTraits(RealmList<Trait> incompatibleTraits) {
        this.incompatibleTraits = incompatibleTraits;
    }

    public String getMaleName() {
        return maleName;
    }

    public void setMaleName(String maleName) {
        this.maleName = maleName;
    }

    public String getFemaleName() {
        return femaleName;
    }

    public void setFemaleName(String femaleName) {
        this.femaleName = femaleName;
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    public boolean isTeen() {
        return teen;
    }

    public void setTeen(boolean teen) {
        this.teen = teen;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }
}
