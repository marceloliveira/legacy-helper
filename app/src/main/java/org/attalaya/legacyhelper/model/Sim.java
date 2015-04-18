package org.attalaya.legacyhelper.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Marcel on 07/03/2015.
 */
public class Sim extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String surname;
    private int generation;
    private int type;
    private int age;
    private Trait trait1;
    private Trait trait2;
    private Trait trait3;
    private boolean memorialized;
    private boolean antiAgingUsed;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Trait getTrait1() {
        return trait1;
    }

    public void setTrait1(Trait trait1) {
        this.trait1 = trait1;
    }

    public Trait getTrait2() {
        return trait2;
    }

    public void setTrait2(Trait trait2) {
        this.trait2 = trait2;
    }

    public Trait getTrait3() {
        return trait3;
    }

    public void setTrait3(Trait trait3) {
        this.trait3 = trait3;
    }

    public boolean isMemorialized() {
        return memorialized;
    }

    public void setMemorialized(boolean memorialized) {
        this.memorialized = memorialized;
    }

    public boolean isAntiAgingUsed() {
        return antiAgingUsed;
    }

    public void setAntiAgingUsed(boolean antiAgingUsed) {
        this.antiAgingUsed = antiAgingUsed;
    }
}
