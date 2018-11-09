package com.example.maryc.egt;

import java.util.Date;

public class EngineInspectionRecord {
    private int mID;
    private String mESN;
    private Date mRecordDate;
    private String mEngineModel;
    private String mOperator;
    private String mEngineDesignation;
    private int mThrustInK;
    private float mExaustGasTemperature;
    private float mCurrentEGTMargin;
    private float mHoursSinceLastShopVisit;
    private float mHoursSinceNew;
    private int mCyclesSinceNew;
    private float mEstimatedTimeToShopVisit;

    public EngineInspectionRecord() {

    }

    public EngineInspectionRecord(String ESN,
                                  Date RecordDate,
                                  String EngineModel,
                                  String Operator,
                                  String EngineDesignation,
                                  int ThrustInK,
                                  float ExaustGasTemperature,
                                  float CurrentEGTMargin,
                                  float HoursSinceLastShopVisit,
                                  float HoursSinceNew,
                                  int CyclesSinceNew,
                                  float EstimatedTimeToShopVisit) {
        mESN = mESN;
        mRecordDate = mRecordDate;
        mEngineModel = mEngineModel;
        mOperator = mOperator;
        mEngineDesignation = mEngineDesignation;
        mThrustInK = mThrustInK;
        mExaustGasTemperature = mExaustGasTemperature;
        mCurrentEGTMargin = mCurrentEGTMargin;
        mHoursSinceLastShopVisit = mHoursSinceLastShopVisit;
        mHoursSinceNew = mHoursSinceNew;
        mCyclesSinceNew = mCyclesSinceNew;
        mEstimatedTimeToShopVisit = EstimatedTimeToShopVisit;


    }

    public void setRecordDate(Date RecordDate) {
        mRecordDate = RecordDate;
    }

    public Date getRecordDate() {
        return mRecordDate;
    }

    public void setEngineModel(String EngineModel) {
        mEngineModel = EngineModel;
    }

    public String getEngineModel() {
        return mEngineModel;
    }

    public void setOperator(String Operator) {
        mOperator = Operator;
    }

    public String getOperator() {
        return mOperator;
    }

    public void setEngineDesignation(String EngineDesignation) {
        mEngineDesignation = EngineDesignation;
    }

    public String getEngineDesignation() {
        return mEngineDesignation;
    }

    public void setThrustInK(int ThrustInK) {
        mThrustInK = ThrustInK;
    }

    public int getThrustInK() {
        return mThrustInK;
    }

    public void setExaustGasTemperature(float ExaustGasTemperature) {
        mExaustGasTemperature = ExaustGasTemperature;
    }

    public float getExaustGasTemperature() {
        return mExaustGasTemperature;
    }

    public void setCurrentEGTMargin(float CurrentEGTMargin) {
        mCurrentEGTMargin = CurrentEGTMargin;
    }

    public float getCurrentEGTMargin() {
        return mCurrentEGTMargin;
    }

    public void setHoursSinceLastShopVisit(float HoursSinceLastShopVisit) {
        mHoursSinceLastShopVisit = HoursSinceLastShopVisit;
    }

    public float getHoursSinceLastShopVisit() {
        return mHoursSinceLastShopVisit;
    }

    public void setHoursSinceNew(float HoursSinceNew) {
        mHoursSinceNew = HoursSinceNew;
    }

    public float getHoursSinceNew() {
        return mHoursSinceNew;
    }

    public void setCyclesSinceNew(int CyclesSinceNew) {
        mCyclesSinceNew = CyclesSinceNew;
    }

    public int getCyclesSinceNew() {
        return mCyclesSinceNew;
    }

    public void setEstimatedTimeToShopVisit(float EstimatedTimeToShopVisit) {
        mEstimatedTimeToShopVisit = EstimatedTimeToShopVisit;
    }

    public float getEstimatedTimeToShopVisit() {
        return mEstimatedTimeToShopVisit;
    }


}
