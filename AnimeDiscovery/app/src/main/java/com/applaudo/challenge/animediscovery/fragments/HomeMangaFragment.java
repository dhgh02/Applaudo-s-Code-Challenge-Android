package com.applaudo.challenge.animediscovery.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.applaudo.challenge.animediscovery.R;
import com.applaudo.challenge.animediscovery.apis.responses.GenresResponse;
import com.applaudo.challenge.animediscovery.base_adapters.GenresBaseAdapter;

public class HomeMangaFragment extends Fragment implements SearchView.OnQueryTextListener{

    //Variable declaration
    private static final String ARG_GENRE = "genre";
    private GenresResponse mGenreResponse;
    private GenresBaseAdapter mGenresBaseAdapter;
    private final boolean IS_ANIME = false;
    private SearchView mSearchMediaSearchView;
    private ListView mGenreListView;


    public HomeMangaFragment() {
        // Required empty public constructor
    }

    //Creating instance
    public static HomeMangaFragment newInstance(GenresResponse genreResponse) {
        HomeMangaFragment fragment = new HomeMangaFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_GENRE, genreResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenreResponse = getArguments().getParcelable(ARG_GENRE);
            if (!isStateSaved()){
                setArguments(null);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View myInflatedView = inflater.inflate(R.layout.fragment_home_manga, container,false);

        mSearchMediaSearchView =  (SearchView) myInflatedView.findViewById(R.id.searchMediaSearchView);
        mGenreListView =  (ListView) myInflatedView.findViewById(R.id.genreListView);
        mGenresBaseAdapter = new GenresBaseAdapter(getActivity(), mGenreResponse.getData(),IS_ANIME);
        mGenreListView.setAdapter(mGenresBaseAdapter);

        //Setting listener for Search Media
        mSearchMediaSearchView.setOnQueryTextListener(this);

        // Inflate the layout for this fragment
        return myInflatedView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        //Executing filter inside the base adapter
        mGenresBaseAdapter.filter(text);
        return false;
    }
}
