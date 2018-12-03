package com.example.maryc.egt;

public class EngineDesignation {
    private String mId;
    private String mEngineModel;
    private String mConfiguration;
    private int mThrustInK;
    private int mRedLineTemperature;
    private int mDegreesPerCyclesThousandCycles;

    public EngineDesignation(String engineModel, String engineDesignation,int thrushInK,
                             int redLineTemperature, int degreesPerCyclesThousandCycles) {
        mEngineModel = engineModel;
        mConfiguration = engineDesignation;
        mThrustInK = thrushInK;
        mRedLineTemperature = redLineTemperature;
        mDegreesPerCyclesThousandCycles = degreesPerCyclesThousandCycles;
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

    public void setThrustInK(int ThrustInK) {
        mThrustInK = ThrustInK;
    }

    public int getThrustInK() {
        return mThrustInK;
    }

    public void setRedLineTemperature(int RedLineTemperature) {
        mRedLineTemperature = RedLineTemperature;
    }

    public int getRedLineTemperature() {
        return mRedLineTemperature;
    }

    public void setDegreesPerCyclesThousandCycles(int DegreesPerCyclesThousandCycles) {
        mDegreesPerCyclesThousandCycles = DegreesPerCyclesThousandCycles;
    }

    public int getDegreesPerCyclesThousandCycles() {
        return mDegreesPerCyclesThousandCycles;
    }

}
