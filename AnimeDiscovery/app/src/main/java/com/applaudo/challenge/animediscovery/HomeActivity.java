package com.applaudo.challenge.animediscovery;

import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.applaudo.challenge.animediscovery.apis.KitsuApiAdapter;
import com.applaudo.challenge.animediscovery.apis.responses.AnimeResponse;
import com.applaudo.challenge.animediscovery.apis.responses.GenresResponse;
import com.applaudo.challenge.animediscovery.apis.responses.MangaResponse;
import com.applaudo.challenge.animediscovery.fragments.HomeAnimeFragment;
import com.applaudo.challenge.animediscovery.fragments.HomeMangaFragment;
import com.applaudo.challenge.animediscovery.models.Data;
import com.applaudo.challenge.animediscovery.utilities.Connections;
import com.facebook.stetho.Stetho;
import com.gu.toolargetool.TooLargeTool;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    //Variable declaration
    private ProgressBar mWaitProgressBar;
    private TextView mWaitTextView;
    private Button mRetryButton;
    private AppBarLayout mHomeAppBarLayout;
    private ViewPager mHomeViewPager;
    private TabLayout mHomeTabLayout;
    private Connections mConnections;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private GenresResponse mGenresResponse;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Getting TooLarge Bundle key when serialized as a Parcel
        TooLargeTool.startLogging(getApplication());

        //Enabled, developers access to the Chrome Developer Tools feature
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

        //Initialize Variable
        mHomeAppBarLayout = (AppBarLayout) findViewById(R.id.homeAppBarLayout);
        mHomeTabLayout = (TabLayout) findViewById(R.id.homeTabLayout);
        mHomeViewPager = (ViewPager) findViewById(R.id.homeViewPager);
        mWaitProgressBar = (ProgressBar) findViewById(R.id.waitProgressBar);
        mWaitTextView = (TextView) findViewById(R.id.waitTextView);
        mRetryButton = (Button) findViewById(R.id.retryButton);
        mConnections = new Connections(this);
        mRetryButton.setOnClickListener(this);

        //Setting main configurations
        setupViewPager(mHomeViewPager);
        mHomeTabLayout.setupWithViewPager(mHomeViewPager);
        setupTabIcons();
        mHomeAppBarLayout.setVisibility(View.GONE);

        //Executting Api
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
        //Clear all the Disposable, this is necessary to cancel all the running request when user press back
        mDisposable.clear();
        //Avoid reload activity when returns after press back button from main activity
        moveTaskToBack(true);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        //Avoid press a button twice at the same moment
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.retryButton:
                retryButtonOnClick(view);
                break;
        }
    }

    public void retryButtonOnClick(View v) {
        executeGetGenres();
    }

    private void executeGetGenres(){
        //Verify if app is connected to Mobile or Wifi before request http
        if(mConnections.isConnected()) {
            //Requesting http
            disableContainer(getString(R.string.gettin_anime_manga));
            callGetGenres();
        } else {
            responseError(getString(R.string.error_internet_connection));
        }
    }

    private void setupTabIcons() {
        TextView tabDetalle = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_home, null);
        tabDetalle.setText("ANIME");
        tabDetalle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_anime, 0, 0, 0);
        mHomeTabLayout.getTabAt(0).setCustomView(tabDetalle);
        TextView tabCertificados = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_home, null);
        tabCertificados.setText("MANGA");
        tabCertificados.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_manga, 0, 0, 0);
        mHomeTabLayout.getTabAt(1).setCustomView(tabCertificados);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(HomeAnimeFragment.newInstance(mGenresResponse), "ANIME");
        adapter.addFrag(HomeMangaFragment.newInstance(mGenresResponse), "MANGA");
        viewPager.setAdapter(adapter);
    }

    //Returning fragment for each tab
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //getItem es llamado para instancear el fragmento para la pagina dada
            // Retorna un PlaceholderFragment

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    //Creating genres request
    private Observable<GenresResponse> getGenres() {
        return  KitsuApiAdapter.getApiService().getGenres()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //Getting all the animeResponse
    private Observable<Data> getAnimesByGenreObservable(final Data data) {
        return KitsuApiAdapter.getApiService()
                .getAnimesByGenre(data.getAttributes().getName())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<AnimeResponse, Data>() {
                    @Override
                    public Data apply(AnimeResponse animeResponse) throws Exception {
                        data.setAnimeResponse(animeResponse);
                        return data;
                    }
                });
    }

    //Getting all the mangaResponse
    private Observable<Data> getMangasByGenreObservable(final Data data) {
        return KitsuApiAdapter.getApiService()
                .getMangasByGenre(data.getAttributes().getName())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MangaResponse, Data>() {
                    @Override
                    public Data apply(MangaResponse mangaResponse) throws Exception {
                        data.setMangaResponse(mangaResponse);
                        return data;
                    }
                });
    }

    //Call http request
    private void callGetGenres() {
        try {
            mGenresResponse = new GenresResponse();
            ConnectableObservable<GenresResponse> genresObservable = getGenres().replay();

            //Executing maint call
            mDisposable.add(
                    genresObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<GenresResponse>() {

                                @Override
                                public void onNext(GenresResponse genresResponse) {
                                    mGenresResponse.setData(genresResponse.getData());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    responseError(e.getMessage());
                                }

                                @Override
                                public void onComplete() {
                                }
                            }));

            //Executing calls that depends from main call
            mDisposable.add(
                    genresObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            /**
                             * Fetching price on each Ticket emission
                             * */
                            .flatMapIterable(new Function<GenresResponse, Iterable<Data>>() {
                                @Override
                                public Iterable<Data> apply(GenresResponse genresResponse) throws Exception {
                                    return genresResponse.getData();
                                }
                            })
                            .flatMap(new Function<Data, ObservableSource<Data>>() {
                                @Override
                                public ObservableSource<Data> apply(Data data) throws Exception {
                                    return getAnimesByGenreObservable(data);
                                }
                            })
                            .flatMap(new Function<Data, ObservableSource<Data>>() {
                                @Override
                                public ObservableSource<Data> apply(Data data) throws Exception {
                                    return getMangasByGenreObservable(data);
                                }
                            })
                            .subscribeWith(new DisposableObserver<Data>() {

                                @Override
                                public void onNext(Data data) {

                                    int position = mGenresResponse.getData().indexOf(data);
                                    mGenresResponse.getData().set(position, data);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    responseError(e.getMessage());
                                }

                                @Override
                                public void onComplete() {

                                    setupViewPager(mHomeViewPager);
                                    setupTabIcons();
                                    enableContainer();
                                }
                            }));

            // Calling connect to start
            genresObservable.connect();
        } catch (Exception e){
            responseError(e.getMessage());
        }

    }

    private void enableContainer(){
        mWaitTextView.setText("");
        mHomeAppBarLayout.setVisibility(View.VISIBLE);
        mHomeViewPager.setVisibility(View.VISIBLE);
        mWaitProgressBar.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.GONE);
    }

    private void disableContainer(String message) {
        mWaitTextView.setText(message);
        mHomeAppBarLayout.setVisibility(View.GONE);
        mHomeViewPager.setVisibility(View.GONE);
        mWaitProgressBar.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.GONE);
    }

    private void responseError(String message){
        mWaitTextView.setText(message);
        mHomeAppBarLayout.setVisibility(View.GONE);
        mHomeViewPager.setVisibility(View.GONE);
        mWaitProgressBar.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.VISIBLE);
    }
}
