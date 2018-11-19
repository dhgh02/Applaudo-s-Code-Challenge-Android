package com.applaudo.challenge.animediscovery.apis.responses;

import android.os.Parcel;
import android.os.Parcelable;
import com.applaudo.challenge.animediscovery.models.Data;
import java.util.List;

public class AnimeResponse  implements Parcelable {

    private List<Data> data;

    public AnimeResponse() {
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.data);
    }

    protected AnimeResponse(Parcel in) {
        this.data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<AnimeResponse> CREATOR = new Creator<AnimeResponse>() {
        @Override
        public AnimeResponse createFromParcel(Parcel source) {
            return new AnimeResponse(source);
        }

        @Override
        public AnimeResponse[] newArray(int size) {
            return new AnimeResponse[size];
        }
    };
}
