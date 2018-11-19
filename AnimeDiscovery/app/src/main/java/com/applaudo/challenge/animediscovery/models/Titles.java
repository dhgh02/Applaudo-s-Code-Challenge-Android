package com.applaudo.challenge.animediscovery.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Titles  implements Parcelable {
    private String en;
    private String en_jp;

    public Titles() {
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getEn_jp() {
        return en_jp;
    }

    public void setEn_jp(String en_jp) {
        this.en_jp = en_jp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.en);
        dest.writeString(this.en_jp);
    }

    protected Titles(Parcel in) {
        this.en = in.readString();
        this.en_jp = in.readString();
    }

    public static final Creator<Titles> CREATOR = new Creator<Titles>() {
        @Override
        public Titles createFromParcel(Parcel source) {
            return new Titles(source);
        }

        @Override
        public Titles[] newArray(int size) {
            return new Titles[size];
        }
    };
}
