package org.attalaya.legacyhelper.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Marcel on 04/05/2015.
 */
public class Species extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
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
}
