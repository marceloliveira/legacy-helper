package org.attalaya.legacyhelper.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Marcel on 07/03/2015.
 */
public class Legacy extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private Law genderLaw;
    private Law bloodlineLaw;
    private Law heirLaw;
    private Law speciesLaw;
    private Trait exemplarTrait;

    public Legacy() {
    }

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

    public Law getGenderLaw() {
        return genderLaw;
    }

    public void setGenderLaw(Law genderLaw) {
        this.genderLaw = genderLaw;
    }

    public Law getBloodlineLaw() {
        return bloodlineLaw;
    }

    public void setBloodlineLaw(Law bloodlineLaw) {
        this.bloodlineLaw = bloodlineLaw;
    }

    public Law getHeirLaw() {
        return heirLaw;
    }

    public void setHeirLaw(Law heirLaw) {
        this.heirLaw = heirLaw;
    }

    public Trait getExemplarTrait() {
        return exemplarTrait;
    }

    public void setExemplarTrait(Trait exemplarTrait) {
        this.exemplarTrait = exemplarTrait;
    }

    public Law getSpeciesLaw() {
        return speciesLaw;
    }

    public void setSpeciesLaw(Law speciesLaw) {
        this.speciesLaw = speciesLaw;
    }
}
