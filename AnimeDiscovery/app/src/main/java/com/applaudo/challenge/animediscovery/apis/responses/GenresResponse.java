package com.applaudo.challenge.animediscovery.apis.responses;

import android.os.Parcel;
import android.os.Parcelable;
import com.applaudo.challenge.animediscovery.models.Data;
import java.util.List;

public class GenresResponse implements Parcelable {

    private List<Data> data;

    public GenresResponse() {
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

    protected GenresResponse(Parcel in) {
        this.data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<GenresResponse> CREATOR = new Creator<GenresResponse>() {
        @Override
        public GenresResponse createFromParcel(Parcel source) {
            return new GenresResponse(source);
        }

        @Override
        public GenresResponse[] newArray(int size) {
            return new GenresResponse[size];
        }
    };
}
