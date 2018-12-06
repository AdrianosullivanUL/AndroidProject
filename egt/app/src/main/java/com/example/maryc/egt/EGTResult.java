package com.example.maryc.egt;

public class EGTResult {
    private int mThrust;
    private int mEGTMargin;
    private int mRemainingCycles;
    private int mShopVisitYear;

    public EGTResult(int Thrust, int EGTMargin, int RemainingCycles, int ShopVisitYear) {
        this.mThrust = Thrust;
        this.mEGTMargin = EGTMargin;
        this.mRemainingCycles = RemainingCycles;
        this.mShopVisitYear = ShopVisitYear;
    }

    public int getmThrust() {
        return mThrust;
    }


    public void setmThrust(int mThrust) {
        this.mThrust = mThrust;
    }

    public int getmEGTMargin() {
        return mEGTMargin;
    }

    public void setmEGTMargin(int mEGTMargin) {
        this.mEGTMargin = mEGTMargin;
    }

    public int getmRemainingCycles() {
        return mRemainingCycles;
    }

    public void setmRemainingCycles(int mRemainingCycles) {
        this.mRemainingCycles = mRemainingCycles;
    }

    public int getmShopVisitYear() {
        return mShopVisitYear;
    }

    public void setmShopVisitYear(int mShopVisitYear) {
        this.mShopVisitYear = mShopVisitYear;
    }



}
