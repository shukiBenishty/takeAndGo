package com.shuki.myapplication.entities;

import com.shuki.myapplication.backend.BackendFactory;

public class CarModel {
    protected int codeModel;
    protected String manufacturerName;
    protected String modelName;
    protected int engineCapacity;
    protected GEAR_BOX gearBox;
    protected int seating;
    protected String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }



    public CarModel(String manufacturerName, String modelName, int engineCapacity, GEAR_BOX gearBox, int seating) {
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.engineCapacity = engineCapacity;
        this.gearBox = gearBox;
        this.seating = seating;
        this.codeModel = 1;
        for (CarModel carModel: BackendFactory.getDataSource().getCarModelList()) {
            if (carModel.getCodeModel() >= this.codeModel){
                this.codeModel = carModel.getCodeModel() + 1;
            }
        }

    }

    public CarModel(int codeModel, String manufacturerName, String modelName, int engineCapacity, GEAR_BOX gearBox, int seating) {
        this.codeModel = codeModel;
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.engineCapacity = engineCapacity;
        this.gearBox = gearBox;
        this.seating = seating;
    }

    public CarModel(CarModel oldCarModel) {
        this.codeModel = oldCarModel.codeModel;
        this.manufacturerName = oldCarModel.manufacturerName;
        this.modelName = oldCarModel.modelName;
        this.engineCapacity = oldCarModel.engineCapacity;
        this.gearBox = oldCarModel.gearBox;
        this.seating = oldCarModel.seating;
    }


    public CarModel() {
    }

    public enum GEAR_BOX{
        MANUAL,
        AUTOMATIC
    }

    public int getCodeModel() {
        return codeModel;
    }

    public void setCodeModel(int codeModel) {
        this.codeModel = codeModel;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public GEAR_BOX getGearBox() {
        return gearBox;
    }

    public void setGearBox(GEAR_BOX gearBox) {
        this.gearBox = gearBox;
    }

    public int getSeating() {
        return seating;
    }

    public void setSeating(int seating) {
        this.seating = seating;
    }



}
