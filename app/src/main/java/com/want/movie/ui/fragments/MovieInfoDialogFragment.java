package com.want.movie.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.want.movie.R;
import com.want.movie.model.entities.Movie;

/**
 * Created by Slavik on 25-Nov-17.
 * Yippie-Kay-Yay!
 */

public class MovieInfoDialogFragment extends DialogFragment {

    private static final String MOVIE_KEY = "movie";
    private TextView movieTitle;
    private ImageView movieImage;
    private TextView movieDescription;
    private TextView movieGenreYear;
    private View watchNow;

    private RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_background)
                    .error(R.drawable.placeholder_background);


    public static MovieInfoDialogFragment newInstance(Movie movie) {
        MovieInfoDialogFragment fragment = new MovieInfoDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(MovieInfoDialogFragment.MOVIE_KEY, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        Movie movie = getArguments().getParcelable(MOVIE_KEY);
        applyMovieToView(movie);
    }

    private void initViews(View view) {
        movieTitle = view.findViewById(R.id.movieTitle);
        movieImage = view.findViewById(R.id.movieImage);
        movieDescription = view.findViewById(R.id.movieDetails);
        movieGenreYear = view.findViewById(R.id.movieGenreYear);
        watchNow = view.findViewById(R.id.watchNow);
    }

    @SuppressLint("SetTextI18n")
    private void applyMovieToView(final Movie movie) {
        movieTitle.setText(movie.getTitle());
        Glide
                .with(this)
                .setDefaultRequestOptions(options)
                .load(movie.getCover())
                .apply(RequestOptions.fitCenterTransform())
                .into(movieImage);
        watchNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowser(movie.getUrl());
                dismissAllowingStateLoss();
            }
        });
        movieDescription.setText(movie.getDescription());
        movieGenreYear.setText(movie.getGenre() +
                System.getProperty("line.separator")
                + movie.getYear());
    }


    private void openBrowser(String url) {
        //Do nothing
    }

    @Override
    public void onResume() {
        super.onResume();

        int width = getContext().getResources().getDimensionPixelSize(R.dimen.dialog_width);
        int height = getContext().getResources().getDimensionPixelSize(R.dimen.dialog_height);

        Window window = getDialog().getWindow();
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }
}
