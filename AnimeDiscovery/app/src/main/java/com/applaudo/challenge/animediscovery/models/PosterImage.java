package com.applaudo.challenge.animediscovery.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PosterImage  implements Parcelable {

    private String tiny;

    public PosterImage() {
    }

    public String getTiny() {
        return tiny;
    }

    public void setTiny(String tiny) {
        this.tiny = tiny;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tiny);
    }

    protected PosterImage(Parcel in) {
        this.tiny = in.readString();
    }

    public static final Creator<PosterImage> CREATOR = new Creator<PosterImage>() {
        @Override
        public PosterImage createFromParcel(Parcel source) {
            return new PosterImage(source);
        }

        @Override
        public PosterImage[] newArray(int size) {
            return new PosterImage[size];
        }
    };
}
