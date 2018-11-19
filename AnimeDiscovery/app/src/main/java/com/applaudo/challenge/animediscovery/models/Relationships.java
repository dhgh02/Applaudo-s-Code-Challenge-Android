package com.applaudo.challenge.animediscovery.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Relationships  implements Parcelable {

    private Genres genres;

    public Relationships() {
    }

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.genres, flags);
    }

    protected Relationships(Parcel in) {
        this.genres = in.readParcelable(Genres.class.getClassLoader());
    }

    public static final Creator<Relationships> CREATOR = new Creator<Relationships>() {
        @Override
        public Relationships createFromParcel(Parcel source) {
            return new Relationships(source);
        }

        @Override
        public Relationships[] newArray(int size) {
            return new Relationships[size];
        }
    };
}
