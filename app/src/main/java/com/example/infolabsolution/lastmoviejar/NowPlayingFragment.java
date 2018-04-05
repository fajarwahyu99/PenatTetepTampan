package com.example.infolabsolution.lastmoviejar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.infolabsolution.lastmoviejar.DetailActivity;
import com.example.infolabsolution.lastmoviejar.R;
import com.example.infolabsolution.lastmoviejar.MoviesAdapter;
import com.example.infolabsolution.lastmoviejar.MovieModel;
import com.example.infolabsolution.lastmoviejar.MovieResponse;
import com.example.infolabsolution.lastmoviejar.ApiBuilder;
import com.example.infolabsolution.lastmoviejar.ApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.infolabsolution.lastmoviejar.BuildConfig.API_KEY;

public class NowPlayingFragment extends Fragment{

    private static final String TAG = NowPlayingFragment.class.getSimpleName();


    public NowPlayingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadMovie();
        setRetainInstance(true);
    }

    private void loadMovie() {
        ApiService apiService = ApiBuilder.getClient(getContext()).create(ApiService.class);

        final RecyclerView recyclerView = getActivity().findViewById(R.id.movie_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<MovieResponse> call = apiService.getNowPlaying(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {
                final List<MovieModel> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.content_main, getContext()));
                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        public boolean onSingleTapUp(MotionEvent e){
                            return true;
                        }
                    });

                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        View child = rv.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && gestureDetector.onTouchEvent(e)){
                            int position = rv.getChildAdapterPosition(child);
                            Intent i = new Intent(getContext(), DetailActivity.class);
                            i.putExtra(BuildConfig.EXTRA_TITLE, movies.get(position).getTitle());
                            i.putExtra(BuildConfig.EXTRA_OVERVIEW, movies.get(position).getOverview());
                            i.putExtra(BuildConfig.EXTRA_TIME, movies.get(position).getReleaseDate());
                            i.putExtra(BuildConfig.EXTRA_POSTER, movies.get(position).getPosterPath());
                            getContext().startActivity(i);
                        }
                        return false;
                    }

                    @Override
                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                });
            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {

            }
        });
    }

}
