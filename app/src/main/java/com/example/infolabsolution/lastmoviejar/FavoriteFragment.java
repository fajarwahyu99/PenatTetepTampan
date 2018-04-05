package com.example.infolabsolution.lastmoviejar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.infolabsolution.lastmoviejar.R;
import com.example.infolabsolution.lastmoviejar.FavoriteAdapter;
import com.example.infolabsolution.lastmoviejar.FavoriteHelper;
import com.example.infolabsolution.lastmoviejar.Favorite;

import java.util.ArrayList;
import java.util.LinkedList;

public class FavoriteFragment extends Fragment {

    RecyclerView rvFavorite;
    private LinkedList<Favorite> list;
    private FavoriteHelper favoriteHelper;
    private FavoriteAdapter favoriteAdapter;


    public FavoriteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);
        rvFavorite = (RecyclerView) view.findViewById(R.id.rv_favorite);
        return view;
    }

    private class LoadDB extends AsyncTask<Void, Void, ArrayList<Favorite>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (list.size() > 0 ){
                list.clear();
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Favorite> favorites) {
            super.onPostExecute(favorites);
            list.addAll(favorites);
            favoriteAdapter.setListFavorite(list);
            favoriteAdapter.notifyDataSetChanged();

            if (list.size() == 0){
                Toast.makeText(getActivity(), "tidak ada data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected ArrayList<Favorite> doInBackground(Void... voids) {
            return favoriteHelper.query();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favoriteHelper != null){
            favoriteHelper.close();
        }
    }

    @Override
    public void onResume() {

        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorite.setHasFixedSize(true);

        favoriteHelper = new FavoriteHelper(getActivity());
        favoriteHelper.open();

        list = new LinkedList<>();

        favoriteAdapter = new FavoriteAdapter(getActivity());
        favoriteAdapter.setListFavorite(list);
        rvFavorite.setAdapter(favoriteAdapter);
        new LoadDB().execute();
        super.onResume();
    }


}
