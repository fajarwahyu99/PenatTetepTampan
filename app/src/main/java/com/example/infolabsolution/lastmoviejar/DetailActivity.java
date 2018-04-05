package com.example.infolabsolution.lastmoviejar;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.infolabsolution.lastmoviejar.FavoriteHelper;
import com.example.infolabsolution.lastmoviejar.Favorite;
import com.example.infolabsolution.lastmoviejar.ImageBannerWidget;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.infolabsolution.lastmoviejar.BuildConfig.URL_POSTER;

public class DetailActivity extends AppCompatActivity{
    Context context;
    private FavoriteHelper favoriteHelper;
    private boolean isFavorite = false;
    private int favorite;
    @BindView(R.id.title) TextView teksTitle;
    @BindView(R.id.overview) TextView teksOverview;
    @BindView(R.id.time) TextView teksTime;
    @BindView(R.id.poster) ImageView imagePoster;
    @BindView(R.id.btn_favorite) Button bttnFavor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);


        int id = getIntent().getIntExtra(BuildConfig.EXTRA_ID, 0);
        String title = getIntent().getStringExtra(BuildConfig.EXTRA_TITLE);
        String overview = getIntent().getStringExtra(BuildConfig.EXTRA_OVERVIEW);
        String time = getIntent().getStringExtra(BuildConfig.EXTRA_TIME);
        String poster = getIntent().getStringExtra(BuildConfig.EXTRA_POSTER);
        teksTitle.setText(title);
        teksOverview.setText(overview);
        Date date = null;
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = parser.parse(time);
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM d, yyyy");
            String formattedDate = formatter.format(date);
            teksTime.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Glide.with(this).load(URL_POSTER+poster).into(imagePoster);



        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        favorite = getIntent().getIntExtra(BuildConfig.IS_FAVORITE, 0);
        if (favorite == 1){
            isFavorite = true;
            bttnFavor.setText("hapus favorite");
        }

        bttnFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite){
                    addFavorit();
                    Toast.makeText(DetailActivity.this, "Sukses Difavortikan", Toast.LENGTH_LONG).show();
                }else {
                    deleteFavorite();
                    Toast.makeText(DetailActivity.this, "Favorite Telah Dihapus", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }


    private void addFavorit(){
        Favorite favorites = new Favorite();
        favorites.setTitle(getIntent().getStringExtra(BuildConfig.EXTRA_TITLE));
        favorites.setOverview(getIntent().getStringExtra(BuildConfig.EXTRA_OVERVIEW));
        favorites.setRelease_date(getIntent().getStringExtra(BuildConfig.EXTRA_TIME));
        favorites.setPoster(getIntent().getStringExtra(BuildConfig.EXTRA_POSTER));
        favoriteHelper.insert(favorites);


        int widgetIDs[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), ImageBannerWidget.class));
        for (int id : widgetIDs)
            AppWidgetManager.getInstance(getApplication()).notifyAppWidgetViewDataChanged(id, R.id.stack_view);
    }

    private void deleteFavorite(){
        favoriteHelper.delete(getIntent().getIntExtra(BuildConfig.EXTRA_ID, 0));

        int widgetIDs[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), ImageBannerWidget.class));
        for (int id : widgetIDs)
            AppWidgetManager.getInstance(getApplication()).notifyAppWidgetViewDataChanged(id, R.id.stack_view);
        finish();
    }

}
