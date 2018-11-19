package com.applaudo.challenge.animediscovery.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.applaudo.challenge.animediscovery.apis.responses.AnimeResponse;
import com.applaudo.challenge.animediscovery.apis.responses.MangaResponse;

public class Data implements Parcelable {

    private String id;
    private String type;
    private Attributes attributes;
    private Relationships relationships;
    private AnimeResponse animeResponse;
    private MangaResponse mangaResponse;

    public Data() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Relationships getRelationships() {
        return relationships;
    }

    public void setRelationships(Relationships relationships) {
        this.relationships = relationships;
    }

    public AnimeResponse getAnimeResponse() {
        return animeResponse;
    }

    public void setAnimeResponse(AnimeResponse animeResponse) {
        this.animeResponse = animeResponse;
    }

    public MangaResponse getMangaResponse() {
        return mangaResponse;
    }

    public void setMangaResponse(MangaResponse mangaResponse) {
        this.mangaResponse = mangaResponse;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeParcelable(this.attributes, flags);
        dest.writeParcelable(this.relationships, flags);
        dest.writeParcelable(this.animeResponse, flags);
        dest.writeParcelable(this.mangaResponse, flags);
    }

    protected Data(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.attributes = in.readParcelable(Attributes.class.getClassLoader());
        this.relationships = in.readParcelable(Relationships.class.getClassLoader());
        this.animeResponse = in.readParcelable(AnimeResponse.class.getClassLoader());
        this.mangaResponse = in.readParcelable(MangaResponse.class.getClassLoader());
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
