package com.pavel_pratasavitski.beforestart;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Pavel_Pratasavitski on 8/21/2017.
 */

public class DescriptiveActivity extends SingleFragmentActivity {
    public static final String EXTRA_DATA = "com.pavel_pratasavitski.beforestart.descriptive";

    @Override
    protected Fragment createFragment() {
        return DescriptiveFragment.newInstance();
    }

    public static Intent newIntent(Context context, Integer Id) {

        Intent intent = new Intent(context, DescriptiveActivity.class);
        intent.putExtra(EXTRA_DATA, Id);
        return intent;
    }
}
