package com.phc.cim.ParcelableModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * This parcelable class is used to transfer data from MainActivity to MapActivity
 */

public class CarParcelable implements Parcelable {
    public double mLat;
    public double mLon;
    public String mCarName;
    public String mAddress;

    // Constructor
    public CarParcelable(double lat, double lon, String carName, String address){
        this.mLat = lat;
        this.mLon = lon;
        this.mCarName = carName;
        this.mAddress = address;
    }


    // Parcelling part
    public CarParcelable(Parcel in){
        this.mLat = in.readDouble();
        this.mLon = in.readDouble();
        this.mCarName = in.readString();
        this.mAddress = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mLat);
        dest.writeDouble(mLon);
        dest.writeString(mCarName);
        dest.writeString(mAddress);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CarParcelable createFromParcel(Parcel in) {
            return new CarParcelable(in);
        }

        public CarParcelable[] newArray(int size) {
            return new CarParcelable[size];
        }
    };
}
