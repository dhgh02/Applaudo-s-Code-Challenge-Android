package com.applaudo.challenge.animediscovery.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Links  implements Parcelable {
    private String related;

    public Links() {
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.related);
    }

    protected Links(Parcel in) {
        this.related = in.readString();
    }

    public static final Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel source) {
            return new Links(source);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };
}
