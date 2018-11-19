package com.applaudo.challenge.animediscovery.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.applaudo.challenge.animediscovery.R;
import com.applaudo.challenge.animediscovery.activities.MediaDetailActivity;
import com.applaudo.challenge.animediscovery.models.Data;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder>{

    //Variable declaration
    private Context mContext;
    private List<Data> mDataList;
    private LayoutInflater mInflater;

    public MediaAdapter(){

    }

    public MediaAdapter(Context context, List<Data> dataList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDataList = dataList;
    }

    @Override
    public MediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount()  {
        return mDataList.size();
    }

    @Override
    public void onBindViewHolder(MediaAdapter.ViewHolder holder, int position) {
        holder.canonicalTitleTextView.setText(mDataList.get(position).getAttributes().getCanonicalTitle());

        //Using Glide to load images
        Glide.with(mContext)
                .load(mDataList.get(position).getAttributes().getPosterImage().getTiny())
                .priority(Priority.IMMEDIATE)
                .override(500,700)
                .centerCrop()
                .into(holder.posterImageImageView);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView canonicalTitleTextView;
        ImageView posterImageImageView;

        ViewHolder(View itemView) {
            super(itemView);
            canonicalTitleTextView = itemView.findViewById(R.id.canonicalTitleTextView);
            posterImageImageView = itemView.findViewById(R.id.posterImageImageView);
            canonicalTitleTextView.setOnClickListener(this);
            posterImageImageView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.posterImageImageView:
                    showMediaDetail(getAdapterPosition());
                    break;
                case R.id.canonicalTitleTextView:
                    showMediaDetail(getAdapterPosition());
                    break;
            }
        }

        //Show detail then click media
        private void showMediaDetail(int position){
            Intent mediaDeailIntent = new Intent(mContext, MediaDetailActivity.class);
            mediaDeailIntent.putExtra(mContext.getString(R.string.EXTRA_MEDIA_DETAIL),mDataList.get(position));
            mContext.startActivity(mediaDeailIntent);
        }
    }
}
