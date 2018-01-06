package com.samrak.model;

/**
 * Created by samet on 30-May-16.
 */
public class EntityPerson {

    private long keyId;
    private String keyName;
    private String keySurname;
    private String keyData;
    private int keyInt;
    private float keyFloat;

    public EntityPerson() {
    }

    public EntityPerson(long keyId, String keyName, String keySurname, String keyData, int keyInt, float keyFloat) {
        this.keyId = keyId;
        this.keyName = keyName;
        this.keySurname = keySurname;
        this.keyData = keyData;
        this.keyInt = keyInt;
        this.keyFloat = keyFloat;
    }

    public long getKeyId() {
        return keyId;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getKeySurname() {
        return keySurname;
    }

    public String getKeyData() {
        return keyData;
    }

    public int getKeyInt() {
        return keyInt;
    }

    public float getKeyFloat() {
        return keyFloat;
    }
}
