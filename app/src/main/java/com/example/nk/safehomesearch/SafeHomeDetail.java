package com.example.nk.safehomesearch;

import android.os.Parcel;
import android.os.Parcelable;

public class SafeHomeDetail implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SafeHomeDetail createFromParcel(Parcel in) {
            return new SafeHomeDetail(in);
        }

        public SafeHomeDetail[] newArray(int size) {
            return new SafeHomeDetail[size];
        }
    };

    private String address;
    private double safeScore;
    private double quietScore;
    private double trafficScore;
    private double errandScore;
    private double entertainmentScore;

    public SafeHomeDetail() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSafeScore() {
        return safeScore;
    }

    public void setSafeScore(double safeScore) {
        this.safeScore = safeScore;
    }

    public double getQuietScore() {
        return quietScore;
    }

    public void setQuietScore(double quietScore) {
        this.quietScore = quietScore;
    }

    public double getTrafficScore() {
        return trafficScore;
    }

    public void setTrafficScore(double trafficScore) {
        this.trafficScore = trafficScore;
    }

    public double getErrandScore() {
        return errandScore;
    }

    public void setErrandScore(double errandScore) {
        this.errandScore = errandScore;
    }

    public double getEntertainmentScore() {
        return entertainmentScore;
    }

    public void setEntertainmentScore(double entertainmentScore) {
        this.entertainmentScore = entertainmentScore;
    }

    public SafeHomeDetail(Parcel in) {
        this.address = in.readString();
        this.safeScore = in.readDouble();
        this.quietScore = in.readDouble();
        this.trafficScore = in.readDouble();
        this.errandScore = in.readDouble();
        this.entertainmentScore = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeDouble(this.safeScore);
        dest.writeDouble(this.quietScore);
        dest.writeDouble(this.trafficScore);
        dest.writeDouble(this.errandScore);
        dest.writeDouble(this.entertainmentScore);
    }

    @Override
    public String toString() {
        return "";
    }
}

