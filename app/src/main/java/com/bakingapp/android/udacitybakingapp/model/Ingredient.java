package com.bakingapp.android.udacitybakingapp.model;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Ingredient extends RealmObject {

    private int entityId;

    private float quantity;

    private String measure;

    @SerializedName("ingredient")
    private String name;


    public Ingredient(int entityId, float quantity, String measure, String name) {
        this.entityId = entityId;
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    public Ingredient() {
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
