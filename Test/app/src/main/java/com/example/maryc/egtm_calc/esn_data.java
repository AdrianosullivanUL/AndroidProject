package com.example.maryc.egtm_calc;

import java.util.GregorianCalendar;

public class esn_data {

    private String mESN;
    private String mType;
    private String mConfiguration;
    private int mTSN;
    private int mCSN;
    private int mTSV;
    private int mCSV;
    private int mEGTM;
    private GregorianCalendar mRecordDate;


    public esn_data() {
        mESN = "_____________";
        mType = "_____________";;
        mConfiguration = "_____________";;
        mTSN =0;;
        mCSN = 0;
        mTSV = 0;
        mCSV = 0;
        mEGTM = 0;
        mRecordDate = new GregorianCalendar();
    }

    public esn_data(String esn, String type, String config, int tsn, int csn,int tsv, int csv, int egtm, GregorianCalendar recordDate){
    mESN = esn;
    mType = type;
    mConfiguration = config;
    mTSN = tsn;
    mCSN = csn;
    mTSN = tsv;
    mCSN = csv;
    mEGTM = egtm;
    mRecordDate =recordDate;
}

    public static esn_data getDefaultItem() {
        return new esn_data("ESN", "CFM56", "5B", 0, 0,0, 0, 999, new GregorianCalendar());
    }

    public String getmESN() {
        return mESN;
    }

    public void setmESN(String mESN) {
        this.mESN = mESN;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmConfiguration() {
        return mConfiguration;
    }

    public void setmConfiguration(String mConfiguration) {
        this.mConfiguration = mConfiguration;
    }

    public int getmTSN() {
        return mTSN;
    }

    public void setmTSN(int mTSN) {
        this.mTSN = mTSN;
    }

    public int getmCSN() {
        return mCSN;
    }

    public void setmCSN(int mCSN) {
        this.mCSN = mCSN;
    }

    public int getmTSV() {
        return mTSV;
    }

    public void setmTSV(int mTSV) {
        this.mTSV = mTSV;
    }

    public int getmCSV() {
        return mCSV;
    }

    public void setmCSV(int mCSV) {
        this.mCSV = mCSV;
    }

    public int getmEGTM() {
        return mEGTM;
    }

    public void setmEGTM(int mEGTM) {
        this.mEGTM = mEGTM;
    }

    public GregorianCalendar getmRecordDate() {
        return mRecordDate;
    }

    public void setmRecordDate(GregorianCalendar mRecordDate) {
        this.mRecordDate = mRecordDate;
    }
}


