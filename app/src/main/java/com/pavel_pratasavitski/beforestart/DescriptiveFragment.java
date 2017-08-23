package com.pavel_pratasavitski.beforestart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Pavel_Pratasavitski on 8/21/2017.
 */

public class DescriptiveFragment extends Fragment {
    private Photo mPhoto;
    private TextView mName;
    private ImageView mImageView;
    private ActionBar mActionBar;

    public static DescriptiveFragment newInstance() {

        Bundle args = new Bundle();

        DescriptiveFragment fragment = new DescriptiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer Id = (Integer) getActivity().getIntent()
                .getSerializableExtra(DescriptiveActivity.EXTRA_DATA);
        mPhoto = PhotoLst.get(getActivity()).getPhoto(Id);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_description, container, false);
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle(mPhoto.getTitle());
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mName = v.findViewById(R.id.text_view_name);
        mName.setText(mPhoto.getTitle());

        mImageView = v.findViewById(R.id.detail_image_view);
        Picasso.with(getActivity()).load(mPhoto.getUrl())
                .into(mImageView);

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
