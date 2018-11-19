package com.applaudo.challenge.animediscovery.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Genres  implements Parcelable {

    private Links links;

    public Genres() {
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.links, flags);
    }

    protected Genres(Parcel in) {
        this.links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Creator<Genres> CREATOR = new Creator<Genres>() {
        @Override
        public Genres createFromParcel(Parcel source) {
            return new Genres(source);
        }

        @Override
        public Genres[] newArray(int size) {
            return new Genres[size];
        }
    };
}
