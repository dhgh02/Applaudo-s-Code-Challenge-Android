package com.applaudo.challenge.animediscovery.base_adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.applaudo.challenge.animediscovery.R;
import com.applaudo.challenge.animediscovery.adapters.MediaAdapter;
import com.applaudo.challenge.animediscovery.models.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GenresBaseAdapter extends BaseAdapter {

    //Variable declaration
    private Context mContext;
    private List<Data> mDataList;
    private ArrayList<Data> mDataArrayList;
    private boolean mIsAnime;
    private MediaAdapter mMediaAdapter;

    public GenresBaseAdapter  (Context context, List<Data> dataList,boolean isAnime) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mIsAnime = isAnime;
        this.mDataArrayList = new ArrayList<Data>();
        this.mDataArrayList.addAll(dataList);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.base_adapter_genres, null);
        }

        TextView genreTextView = (TextView) convertView.findViewById(R.id.genreTextView);
        RecyclerView mediaRecyclerView = (RecyclerView) convertView.findViewById(R.id.mediaRecyclerView);

        genreTextView.setText(mDataList.get(position).getAttributes().getName());
        mMediaAdapter = new MediaAdapter();
        //Verify if is Anime or Manga to deside which data send to the Adapter
        if(mIsAnime) {
            mMediaAdapter = new MediaAdapter(mContext, mDataList.get(position).getAnimeResponse().getData());
        }else{
            mMediaAdapter = new MediaAdapter(mContext, mDataList.get(position).getMangaResponse().getData());
        }
        //Setting RecyclerView parameters
        mediaRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);
        mediaRecyclerView.setLayoutManager(layoutManager);
        mediaRecyclerView.setAdapter(mMediaAdapter);

        return convertView;
    }

    // Filter methos by Genre
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mDataList.clear();
        if (charText.length() == 0) {
            mDataList.addAll(mDataArrayList);
        } else {
            for (Data search : mDataArrayList) {
                if (search.getAttributes().getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mDataList.add(search);
                }
            }
        }
        notifyDataSetChanged();
    }
}
