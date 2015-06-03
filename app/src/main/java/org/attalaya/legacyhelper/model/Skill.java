package org.attalaya.legacyhelper.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Marcel on 07/03/2015.
 */
public class Skill extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private boolean child;
    private Skill childUnlock;
    private Package pack;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    public Skill getChildUnlock() {
        return childUnlock;
    }

    public void setChildUnlock(Skill childUnlock) {
        this.childUnlock = childUnlock;
    }
}
