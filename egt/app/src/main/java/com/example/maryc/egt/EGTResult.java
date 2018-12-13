package com.example.maryc.egt;

public class EGTResult {
    private String mDesignation;
    private int mThrust;
    private int mEGTMargin;
    private int mRemainingCycles;
    private int mShopVisitYear;

    public EGTResult(String Designation, int Thrust, int EGTMargin, int RemainingCycles, int ShopVisitYear) {

        this.mDesignation = Designation;
        this.mThrust = Thrust;
        this.mEGTMargin = EGTMargin;
        this.mRemainingCycles = RemainingCycles;
        this.mShopVisitYear = ShopVisitYear;
    }

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String Designation) {
        this.mDesignation = Designation;
    }

    public int getThrust() {
        return mThrust;
    }

    public void setThrust(int Thrust) {
        this.mThrust = Thrust;
    }

    public int getEGTMargin() {
        return mEGTMargin;
    }

    public void setEGTMargin(int EGTMargin) {
        this.mEGTMargin = EGTMargin;
    }

    public int getRemainingCycles() {
        return mRemainingCycles;
    }

    public void setRemainingCycles(int RemainingCycles) {
        this.mRemainingCycles = RemainingCycles;
    }

    public int getShopVisitYear() {
        return mShopVisitYear;
    }

    public void setShopVisitYear(int ShopVisitYear) {
        this.mShopVisitYear = ShopVisitYear;
    }


}
