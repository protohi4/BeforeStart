package com.pavel_pratasavitski.beforestart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Pavel_Pratasavitski on 8/22/2017.
 */

public interface APIService {
    @GET("bins/upt7z")
    Call<PhotoList> loadPhotos();
}
