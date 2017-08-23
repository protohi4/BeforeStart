package com.pavel_pratasavitski.beforestart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

public class BeforeStartActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        if (isInternetAvailable(this)) {
            return BeforeStartFragment.newInstance();
        } else {
            if (PhotoLst.get(getApplicationContext()).getPhotoCount() == 0) {
                return NoConnectionFragment.newInstance();
            }
            return BeforeStartFragment.newInstance();
        }
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) return false;

        switch (activeNetwork.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                if ((activeNetwork.getState() == NetworkInfo.State.CONNECTED ||
                        activeNetwork.getState() == NetworkInfo.State.CONNECTING))
                    return true;
                break;
            case ConnectivityManager.TYPE_MOBILE:
                if ((activeNetwork.getState() == NetworkInfo.State.CONNECTED ||
                        activeNetwork.getState() == NetworkInfo.State.CONNECTING))
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }
}
