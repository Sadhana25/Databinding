package com.irayasoft.pakkruti.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.irayasoft.pakkruti.model.DogBreed;
import com.irayasoft.pakkruti.model.database.DogDatabase;

public class DetailViewModel extends AndroidViewModel {
    public MutableLiveData<DogBreed> dogLivedata = new MutableLiveData<DogBreed>();
    private RetrieveDogTask task;
    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetch(int uuid) {
     task=new RetrieveDogTask();
     task.execute(uuid);
        //raw data
//        DogBreed dogBreed=new DogBreed("breed id1","dogbreed","lifespan",
//                "breedgrp","breedfor","temp","url");

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(task!=null){
            task.cancel(true);
            task=null;

        }
    }

    //************************Asyntask
    private class RetrieveDogTask extends AsyncTask<Integer,Void,DogBreed>{
        @Override
        protected DogBreed doInBackground(Integer... integers) {
            int uuid=integers[0];
            return DogDatabase.getInstance(getApplication()).dao().getDog(uuid);
        }

        @Override
        protected void onPostExecute(DogBreed dogBreed) {
            dogLivedata.setValue(dogBreed);
        }
    }
}
