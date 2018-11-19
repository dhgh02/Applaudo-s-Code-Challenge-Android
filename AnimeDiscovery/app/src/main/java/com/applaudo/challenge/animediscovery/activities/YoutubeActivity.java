package com.applaudo.challenge.animediscovery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.applaudo.challenge.animediscovery.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        //Getting Extras
        Intent mediaDeailIntent = getIntent();
        String youtubeVideoId = mediaDeailIntent.getExtras().getString(getString(R.string.EXTRA_YOUTUBE));

        //Starting video
        YouTubePlayerView videoYoutubePlayerView = findViewById(R.id.videoYouTubePlayerView);
        playVideo(youtubeVideoId, videoYoutubePlayerView);

    }

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //Initialize youtube player view
        youTubePlayerView.initialize("AIzaSyAxVKuyx9vk7WdTKWJ2mRQxv4unIknd2ew",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
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
        finish();
    }
}
