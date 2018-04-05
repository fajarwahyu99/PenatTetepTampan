package com.example.infolabsolution.lastmoviejar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.infolabsolution.lastmoviejar.DetailActivity;
import com.example.infolabsolution.lastmoviejar.R;
import com.example.infolabsolution.lastmoviejar.Favorite;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.infolabsolution.lastmoviejar.BuildConfig.URL_POSTER;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private LinkedList<Favorite> daftarFavorite;
    private Context context;
    private Activity activity;


    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public LinkedList<Favorite> getListFavorite() {
        return daftarFavorite;
    }

    public void setListFavorite(LinkedList<Favorite> daftarFavorite) {
        this.daftarFavorite = daftarFavorite;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.filmTitle.setText(daftarFavorite.get(position).getTitle());

        String time = daftarFavorite.get(position).getRelease_date();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = parser.parse(time);
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM d, yyyy");
            String formattedDate = formatter.format(date);
            holder.data.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.movieDescription.setText(daftarFavorite.get(position).getOverview());
        Glide.with(context).load(URL_POSTER + daftarFavorite.get(position).getPoster()).into(holder.backbg);


        holder.RvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pindahActivity(position, v);
            }
        });


    }

    private void pindahActivity(int position, View v) {

        Intent i = new Intent(v.getContext(), DetailActivity.class);
        i.putExtra(BuildConfig.EXTRA_ID, daftarFavorite.get(position).getId());
        i.putExtra(BuildConfig.EXTRA_TITLE, daftarFavorite.get(position).getTitle());
        i.putExtra(BuildConfig.EXTRA_OVERVIEW, daftarFavorite.get(position).getOverview());
        i.putExtra(BuildConfig.EXTRA_TIME, daftarFavorite.get(position).getRelease_date());
        i.putExtra(BuildConfig.EXTRA_POSTER, daftarFavorite.get(position).getPoster());
        i.putExtra(BuildConfig.IS_FAVORITE, 1);
        v.getContext().startActivity(i);

    }


    @Override
    public int getItemCount() {
        return getListFavorite().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.title) TextView filmTitle;
        @BindView(R.id.subtitle) TextView data;
        @BindView(R.id.description) TextView movieDescription;
        @BindView(R.id.backbg) ImageView backbg;

        public CardView RvMain;

        public ViewHolder(View v) {
            super(v);
            RvMain = (CardView) v.findViewById(R.id.movies_layout);
            ButterKnife.bind(this, v);
        }
    }
}