package com.samrak.model;

/**
 * Created by sametoztoprak on 31.12.2017.
 */

public class EntityMaterial {
    private long materialId;
    private String materialName;
    private String materialSurname;
    private String materialData;

    public EntityMaterial() {
    }

    public EntityMaterial(long materialId, String materialName, String materialSurname, String materialData) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.materialSurname = materialSurname;
        this.materialData = materialData;
    }

    public long getMaterialId() {
        return materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getMaterialSurname() {
        return materialSurname;
    }

    public String getMaterialData() {
        return materialData;
    }
}
