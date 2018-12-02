package com.example.maryc.egt;

public class EngineDesignation {
    private String mId;
    private String mEngineModel;
    private String mConfiguration;
    private float mThrustInK;
    private float mRedLineTemperature;
    private int mCyclesPerDegree;

    public EngineDesignation(String engineModel, String engineDesignation,int thrushInK, int redLineTemperature, int cyclesPerDegree) {
        mEngineModel = engineModel;
        mConfiguration = engineDesignation;
        mThrustInK = thrushInK;
        mRedLineTemperature = redLineTemperature;
        mCyclesPerDegree = cyclesPerDegree;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setEngineModel(String EngineModel) {
        mEngineModel = EngineModel;
    }

    public String getEngineModel() {
        return mEngineModel;
    }

    public void setConfiguration(String Configuration) {
        mConfiguration = Configuration;
    }

    public String getConfiguration() {
        return mConfiguration;
    }

    public void setThrustInK(float ThrustInK) {
        mThrustInK = ThrustInK;
    }

    public float getThrustInK() {
        return mThrustInK;
    }

    public void setRedLineTemperature(float RedLineTemperature) {
        mRedLineTemperature = RedLineTemperature;
    }

    public float getRedLineTemperature() {
        return mRedLineTemperature;
    }

    public void setCyclesPerDegree(int CyclesPerDegree) {
        mCyclesPerDegree = CyclesPerDegree;
    }

    public int getCyclesPerDegree() {
        return mCyclesPerDegree;
    }

}
