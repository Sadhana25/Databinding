package com.irayasoft.pakkruti.model.network;

import com.irayasoft.pakkruti.model.DogBreed;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
   public  String BASE_URl="https://raw.githubusercontent.com/";
    @GET("DevTides/DogsApi/master/dogs.json")
    Single<List<DogBreed>> getDogList();
    //***************add aditional methode here with diffrent endpoint
}
