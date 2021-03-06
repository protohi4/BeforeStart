package com.pavel_pratasavitski.beforestart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pavel_Pratasavitski on 8/21/2017.
 */

public class BeforeStartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Photo> mPhotos;

    public static BeforeStartFragment newInstance() {

        Bundle args = new Bundle();

        BeforeStartFragment fragment = new BeforeStartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        downloadPhotos();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle SavedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_before_start, container, false);

        mRecyclerView = view.findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        mPhotos = PhotoLst.get(getActivity()).getPhotos();
        setupAdapter(new MyAdapter(mPhotos));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_window_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.google_map_view:
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void downloadPhotos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.myjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        service.loadPhotos().enqueue(new Callback<PhotoList>() {
            @Override
            public void onResponse(Call<PhotoList> call, Response<PhotoList> response) {
                mPhotos = response.body().getPhotos();
                PhotoLst.get(getActivity()).setPhotos(mPhotos);
                setupAdapter(new MyAdapter(mPhotos));
            }

            @Override
            public void onFailure(Call<PhotoList> call, Throwable t) {
                mPhotos = PhotoLst.get(getActivity()).getPhotos();
                setupAdapter(new MyAdapter(mPhotos));
            }
        });
    }

    public void setupAdapter(RecyclerView.Adapter adapter) {
        if (isAdded()) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder
                    implements View.OnClickListener{
        private TextView mNameTextView;
        private ImageView mImageView;
        private Photo mPhoto;

        public MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.item_name_view);
            mImageView = itemView.findViewById(R.id.photo_view);
        }

        public void bindItem(Photo photo) {
            mPhoto = photo;
            mNameTextView.setText(mPhoto.getTitle());
            Glide.with(getActivity().getApplicationContext())
                    .load(mPhoto.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mImageView);
        }

        @Override
        public void onClick(View view) {
            Intent intent = DescriptiveActivity.newIntent(getActivity(), mPhoto.getId());
            startActivity(intent);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private List<Photo> mPhotos;

        public MyAdapter(List<Photo> photos) {
            mPhotos = photos;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.one_item_layout, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            Photo photo = mPhotos.get(position);
            holder.bindItem(photo);
        }

        @Override
        public int getItemCount() {
            return mPhotos.size();
        }
    }
}
