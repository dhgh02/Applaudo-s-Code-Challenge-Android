package com.applaudo.challenge.animediscovery.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.applaudo.challenge.animediscovery.R;
import com.applaudo.challenge.animediscovery.apis.KitsuApiAdapter;
import com.applaudo.challenge.animediscovery.apis.responses.GenresResponse;
import com.applaudo.challenge.animediscovery.models.Data;
import com.applaudo.challenge.animediscovery.utilities.Connections;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import java.text.DecimalFormat;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MediaDetailActivity extends AppCompatActivity  implements View.OnClickListener {

    //Show detail then click media
    private Data mData;
    private String mRelatedGenres;
    private ScrollView mContainerScrollView;
    private ProgressBar mWaitProgressBar;
    private TextView mWaitTextView;
    private Button mRetryButton;
    private Connections mConnections;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private ImageView mPosterImageImageView;
    private Button mYouTubeButton;
    private TextView mMainTitleTextView;
    private TextView mCanonicalTitleTextView;
    private TextView mTypeTextView;
    private TextView mYearTextView;
    private TextView mGenresTextView;
    private TextView mAverageRatingTextView;
    private TextView mEpisodeDurationTextView;
    private TextView mAgeRatingTextView;
    private TextView mAiringStatusTextView;
    private TextView mSynopsisTextView;
    private long mLastClickTime = 0;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,##0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);

        Intent mediaDeailIntent = getIntent();
        mData = mediaDeailIntent.getExtras().getParcelable(getString(R.string.EXTRA_MEDIA_DETAIL));

        mContainerScrollView = (ScrollView) findViewById(R.id.containerScrollView);
        mWaitProgressBar = (ProgressBar) findViewById(R.id.waitProgressBar);
        mWaitTextView = (TextView) findViewById(R.id.waitTextView);
        mRetryButton = (Button) findViewById(R.id.retryButton);
        mConnections = new Connections(this);
        mPosterImageImageView = (ImageView) findViewById(R.id.posterImageImageView);
        mYouTubeButton = (Button) findViewById(R.id.youtubeButton);
        mMainTitleTextView = (TextView) findViewById(R.id.mainTitleTextView);
        mCanonicalTitleTextView = (TextView) findViewById(R.id.canonicalTitleTextView);
        mTypeTextView = (TextView) findViewById(R.id.typeTextView);
        mYearTextView = (TextView) findViewById(R.id.yearTextView);
        mGenresTextView = (TextView) findViewById(R.id.genresTextView);
        mAverageRatingTextView = (TextView) findViewById(R.id.averageRatingTextView);
        mEpisodeDurationTextView = (TextView) findViewById(R.id.episodeDurationTextView);
        mAgeRatingTextView = (TextView) findViewById(R.id.ageRatingTextView);
        mAiringStatusTextView = (TextView) findViewById(R.id.airingStatusTextView);
        mSynopsisTextView = (TextView) findViewById(R.id.synopsisTextView);
        mYouTubeButton.setOnClickListener(this);

        executeGetGenres();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        mDisposable.clear();
        finish();
    }

    @Override
    public void onClick(View view) {
        //Desactivando el teclado
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (view.getId()) {
            case R.id.retryButton:
                retryButtonOnClick(view);
                break;
            case R.id.youtubeButton:
                youtubeButtonOnClick(view);
                break;
        }
    }

    public void retryButtonOnClick(View v) {
        executeGetGenres();
    }

    private void youtubeButtonOnClick(View view){
        if(mData.getAttributes().getYoutubeVideoId()!=null){
            Intent youtubeIntent = new Intent(this,YoutubeActivity.class);
            youtubeIntent.putExtra(getString(R.string.EXTRA_YOUTUBE),mData.getAttributes().getYoutubeVideoId());
            startActivity(youtubeIntent);
        }else {
            Toast.makeText(getBaseContext(), getString(R.string.message_unavailable_video), Toast.LENGTH_SHORT).show();
        }
    }
    private void setField(){
        try {
            Glide.with(this)
                    .load(mData.getAttributes().getPosterImage().getTiny())
                    .priority(Priority.IMMEDIATE)
                    .override(500,700)
                    .centerCrop()
                    .into(mPosterImageImageView);
            mMainTitleTextView.setText(mData.getAttributes().getTitles().getEn_jp());
            mCanonicalTitleTextView.setText(mData.getAttributes().getCanonicalTitle());
            mTypeTextView.setText("Show "+mData.getType()+", Number of Episodes "+
                    ((String.valueOf(mData.getAttributes().getEpisodeCount()) == null) ? ""
                            : mDecimalFormat.format(mData.getAttributes().getEpisodeCount())));
            mYearTextView.setText(mData.getAttributes().getStartDate()+" till "+mData.getAttributes().getEndDate());
            mGenresTextView.setText(mRelatedGenres);
            mAverageRatingTextView.setText( (String.valueOf(mData.getAttributes().getAverageRating()) == null)? ""
                    : String.valueOf(mData.getAttributes().getAverageRating()));
            mEpisodeDurationTextView.setText(((String.valueOf(mData.getAttributes().getEpisodeLength()) == null) ? ""
                    : mDecimalFormat.format(mData.getAttributes().getEpisodeLength())));
            mAgeRatingTextView.setText(((mData.getAttributes().getAgeRating() == null) ? ""
                    : mData.getAttributes().getAgeRating()));
            mAiringStatusTextView.setText(mData.getAttributes().getStatus());
            mSynopsisTextView.setText(mData.getAttributes().getSynopsis());
        }catch (Exception e){
            responseError(e.getMessage());
        }

    }
    //Creating genres request
    private Observable<GenresResponse> getGenres() {
        return  KitsuApiAdapter.getApiService().getMediaGenres(mData.getRelationships()
                .getGenres()
                .getLinks()
                .getRelated()
        )
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void callGetGenres() {
        try {
            ConnectableObservable<GenresResponse> genresObservable = getGenres().replay();

            mDisposable.add(
                    genresObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<GenresResponse>() {

                                @Override
                                public void onNext(GenresResponse genresResponse) {
                                    for(int i = 0; i<genresResponse.getData().size();i++){
                                        if(i==0){
                                            mRelatedGenres = genresResponse
                                                    .getData().get(i).getAttributes().getName();
                                        }else {
                                            mRelatedGenres = mRelatedGenres +", "+ genresResponse
                                                    .getData().get(i).getAttributes().getName();
                                        }
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                    responseError(e.getMessage());
                                }

                                @Override
                                public void onComplete() {
                                    setField();
                                    enableContainer();
                                }
                            }));
            // Calling connect to start
            genresObservable.connect();
        }catch (Exception e){
            responseError(e.getMessage());
        }

    }

    private void executeGetGenres(){
        //Verify if app is connected to Mobile or Wifi before request http
        if(mConnections.isConnected()) {
            //Requesting http
            disableContainer(getString(R.string.getting_information));
            callGetGenres();
        } else {
            responseError(getString(R.string.error_internet_connection));
        }
    }

    private void enableContainer(){
        mWaitTextView.setText("");
        mContainerScrollView.setVisibility(View.VISIBLE);
        mWaitProgressBar.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.GONE);
    }

    private void disableContainer(String message) {
        mWaitTextView.setText(message);
        mContainerScrollView.setVisibility(View.GONE);
        mWaitProgressBar.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.GONE);
    }

    private void responseError(String message){
        mWaitTextView.setText(message);
        mContainerScrollView.setVisibility(View.GONE);
        mWaitProgressBar.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.VISIBLE);
    }
}
