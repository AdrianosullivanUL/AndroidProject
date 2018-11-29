package com.example.maryc.egt;

public class EngineDesignation {
    private String mId;
    private String mEngineModel;
    private String mConfiguration;
    private float mThrustInK;
    private float mRedLineTemprature;
    private int mCyclesPerDegree;

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

    public void setRedLineTemprature(float RedLineTemprature) {
        mRedLineTemprature = RedLineTemprature;
    }

    public float getRedLineTemprature() {
        return mRedLineTemprature;
    }

    public void setCyclesPerDegree(int CyclesPerDegree) {
        mCyclesPerDegree = CyclesPerDegree;
    }

    public int getCyclesPerDegree() {
        return mCyclesPerDegree;
    }

}
