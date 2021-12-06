package com.irayasoft.pakkruti.model.network;

import com.irayasoft.pakkruti.model.DogBreed;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitService {
    Api api;
    public RetrofitService() {
        api = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(Api.class);
    }
    // function to get All data from api with RXjava
    public Single<List<DogBreed>> getDogs(){
       return api.getDogList();
    }

}
