package com.pavel_pratasavitski.beforestart;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Pavel_Pratasavitski on 8/23/2017.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    GoogleMap mGoogleMap;
    List<Photo> mPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        mPhotos = PhotoLst.get(getApplicationContext()).getPhotos();
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        for (final Photo photo : mPhotos) {

            Glide.with(getApplicationContext())
                    .load(photo.getUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new SimpleTarget<Bitmap>(100, 100) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mGoogleMap.addMarker(
                                    new MarkerOptions().position(
                                            new LatLng(photo.getLatitude(), photo.getLongitude()))
                                            .title(photo.getTitle())
                                            .icon(BitmapDescriptorFactory.fromBitmap(resource)));
                        }
                    });
        }
    }
}
