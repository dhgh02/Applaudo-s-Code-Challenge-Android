package com.applaudo.challenge.animediscovery.apis.responses;

import android.os.Parcel;
import android.os.Parcelable;
import com.applaudo.challenge.animediscovery.models.Data;
import java.util.List;

public class MangaResponse  implements Parcelable {

    private List<Data> data;

    public MangaResponse() {
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

    protected MangaResponse(Parcel in) {
        this.data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<MangaResponse> CREATOR = new Creator<MangaResponse>() {
        @Override
        public MangaResponse createFromParcel(Parcel source) {
            return new MangaResponse(source);
        }

        @Override
        public MangaResponse[] newArray(int size) {
            return new MangaResponse[size];
        }
    };
}
