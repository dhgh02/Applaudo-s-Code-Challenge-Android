package com.applaudo.challenge.animediscovery.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Attributes  implements Parcelable {

    private String name;
    private String slug;
    private String synopsis;
    private Titles titles;
    private String startDate;
    private String endDate;
    private double episodeCount;
    private double episodeLength;
    private double averageRating;
    private String ageRating;
    private String status;
    private Relationships relationships;
    private String canonicalTitle;
    private PosterImage posterImage;
    private String youtubeVideoId;

    public Attributes() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Titles getTitles() {
        return titles;
    }

    public void setTitles(Titles titles) {
        this.titles = titles;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(double episodeCount) {
        this.episodeCount = episodeCount;
    }

    public double getEpisodeLength() {
        return episodeLength;
    }

    public void setEpisodeLength(double episodeLength) {
        this.episodeLength = episodeLength;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Relationships getRelationships() {
        return relationships;
    }

    public void setRelationships(Relationships relationships) {
        this.relationships = relationships;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public PosterImage getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(PosterImage posterImage) {
        this.posterImage = posterImage;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.slug);
        dest.writeString(this.synopsis);
        dest.writeParcelable(this.titles, flags);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeDouble(this.episodeCount);
        dest.writeDouble(this.episodeLength);
        dest.writeDouble(this.averageRating);
        dest.writeString(this.ageRating);
        dest.writeString(this.status);
        dest.writeParcelable(this.relationships, flags);
        dest.writeString(this.canonicalTitle);
        dest.writeParcelable(this.posterImage, flags);
        dest.writeString(this.youtubeVideoId);
    }

    protected Attributes(Parcel in) {
        this.name = in.readString();
        this.slug = in.readString();
        this.synopsis = in.readString();
        this.titles = in.readParcelable(Titles.class.getClassLoader());
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.episodeCount = in.readDouble();
        this.episodeLength = in.readDouble();
        this.averageRating = in.readDouble();
        this.ageRating = in.readString();
        this.status = in.readString();
        this.relationships = in.readParcelable(Relationships.class.getClassLoader());
        this.canonicalTitle = in.readString();
        this.posterImage = in.readParcelable(PosterImage.class.getClassLoader());
        this.youtubeVideoId = in.readString();
    }

    public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
        @Override
        public Attributes createFromParcel(Parcel source) {
            return new Attributes(source);
        }

        @Override
        public Attributes[] newArray(int size) {
            return new Attributes[size];
        }
    };
}
