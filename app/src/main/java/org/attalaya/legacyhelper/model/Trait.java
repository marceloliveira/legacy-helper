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
    private String mName;
    private String fName;
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

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
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
