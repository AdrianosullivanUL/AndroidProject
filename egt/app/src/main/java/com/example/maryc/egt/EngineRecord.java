package com.example.maryc.egt;

import java.util.Date;

public class EngineRecord {
    private int mID;
    private String mESN;
    private Date mRecordDate;
    private String mEngineModel;
    private String mOperator;
    private String mEngineDesignation;
    private int mThrustInK;
    private int mCurrentEGT;
    private int mHoursSinceLastShopVisit;
    private int mHoursSinceNew;
    private int mCyclesSinceNew;

    public EngineRecord() {

    }






    public EngineRecord(String ESN,
                        Date RecordDate,
                        String EngineModel,
                        String Operator,
                        String EngineDesignation,
                        int ThrustInK,
                        int CurrentEGT,
                        int HoursSinceLastShopVisit,
                        int HoursSinceNew,
                        int CyclesSinceNew,
                        int EstimatedTimeToShopVisit) {
        mESN = ESN;
        mRecordDate = RecordDate;
        mEngineModel = EngineModel;
        mOperator = Operator;
        mEngineDesignation = EngineDesignation;
        mThrustInK = ThrustInK;
        mCurrentEGT = CurrentEGT;
        mHoursSinceLastShopVisit = HoursSinceLastShopVisit;
        mHoursSinceNew = HoursSinceNew;
        mCyclesSinceNew = CyclesSinceNew;

    }

    public void setESN(String ESN) {
        mESN = ESN;
    }

    public String getESN() {
        return mESN;
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

    public void setCurrentEGT(int CurrentEGT) {
        mCurrentEGT = CurrentEGT;
    }

    public float getCurrentEGT() {
        return mCurrentEGT;
    }

    public void setHoursSinceLastShopVisit(int HoursSinceLastShopVisit) {
        mHoursSinceLastShopVisit = HoursSinceLastShopVisit;
    }

    public float getHoursSinceLastShopVisit() {
        return mHoursSinceLastShopVisit;
    }

    public void setHoursSinceNew(int HoursSinceNew) {
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




}
