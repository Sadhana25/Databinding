package com.irayasoft.pakkruti.viewmodel;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;

import com.irayasoft.pakkruti.model.DogBreed;
import com.irayasoft.pakkruti.model.database.DogDao;
import com.irayasoft.pakkruti.model.database.DogDatabase;
import com.irayasoft.pakkruti.model.network.RetrofitService;
import com.irayasoft.pakkruti.util.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {
    private static final String TAG = ListViewModel.class.getName();
    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<>();
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private RetrofitService retrofitService = new RetrofitService();
    //rxjava
    private CompositeDisposable disposable = new CompositeDisposable();
    //AsyncTask
    AsyncTask<List<DogBreed>, Void, List<DogBreed>> insertTask;
    AsyncTask<Void,Void,List<DogBreed>> retrieveTask;
    private SharedPreferenceHelper sharedPreferenceHelper=SharedPreferenceHelper.getInstance(getApplication());
    private  long refresh_time=5*60*1000*1000*1000L;
    public ListViewModel(@NonNull Application application) {
        super(application);
    }
    //managing refresh rate
    public void refresh() {
        //cache time logic
        Long updateTime= sharedPreferenceHelper.getUpdatedTime();
        Long currentTime=System.nanoTime();
        if(updateTime!=0  && currentTime-updateTime<refresh_time){
            fetchFromDatabase();
        }
        else {
           fetchFromRemote();
        }
        // raw data if web api not available
//        Log.d(TAG, "refresh: ");
//        DogBreed dogBreed=new DogBreed("1","dogbreed","34",
//                "german","secure","ghjjhjhk","hgj/hjhjhk");
//        DogBreed dogBreed2=new DogBreed("2","dogbreed","34",
//                "german","secure","ghjjhjhk","hgj/hjhjhk");
//        DogBreed dogBreed3=new DogBreed("2","dogbreed","34",
//                "german","secure","ghjjhjhk","hgj/hjhjhk");
//        ArrayList<DogBreed> dogList=new ArrayList<>();
//        dogList.add(dogBreed);
//        dogList.add(dogBreed2);
//        dogList.add(dogBreed2);
//        Log.d(TAG, "refresh: "+dogList.size());
//        dogs.setValue(dogList);
//        dogLoadError.setValue(false);
//        loading.setValue(false);

    }


    //managing refresh bypass cache
    public void refreshBypassCache(){
        fetchFromRemote();
    }
    //fetchfrom Database
    private void fetchFromDatabase() {
      loading.setValue(true);
      retrieveTask=new RetrieveDogsTask();
      retrieveTask.execute();
    }



    private void fetchFromRemote() {
        //initially for any api call it shows loading
        loading.setValue(true);

        retrofitService.getDogs().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
                        //set live data to this receive data
                        //receive data should be stored to local db
//                        dogs.setValue(dogBreeds);
//                        //set disable parameter
//                        dogLoadError.setValue(false);
//                        loading.setValue(false);
                        insertTask = new InserDogTask();
                        insertTask.execute(dogBreeds);
                        Toast.makeText(getApplication(), "dog retrived from network",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        dogLoadError.setValue(true);
                        //shows data error and process of loading
                        loading.setValue(false);
                    }
                });
    }

    private void dogsRetrieved(List<DogBreed> dogBreeds) {
        Log.d(TAG, "dogsRetrieved: "+dogBreeds.size());
        dogs.setValue(dogBreeds);
        dogLoadError.setValue(false);
        loading.setValue(false);
    }

    // viewmodel methode
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
        //thread management
        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }
        if (retrieveTask != null) {
            retrieveTask.cancel(true);
            retrieveTask = null;
        }
    }
    //AsyncTask to process database
    //*******insertTask
    private class InserDogTask extends AsyncTask<List<DogBreed>, Void, List<DogBreed>> {
        @Override
        protected List<DogBreed> doInBackground(List<DogBreed>... lists) {
            List<DogBreed> list = lists[0];
            DogDao dao = DogDatabase.getInstance(getApplication()).dao();
            //delete all dog
            dao.deleteAllDog();
            ArrayList<DogBreed> newList = new ArrayList<>(list);
            List<Long> result = dao.insertAll(newList.toArray(new DogBreed[0]));
            //logic to set uuid

            int i = 0;
            while (i < list.size())
            {
                list.get(i).uuid = result.get(i).intValue();
                ++i;
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreeds) {
            // super.onPostExecute(dogBreeds);
            dogsRetrieved(dogBreeds);
            sharedPreferenceHelper.saveUpdateTime(System.nanoTime());

        }
    }
    //retrieve task
    private class RetrieveDogsTask extends AsyncTask<Void,Void,List<DogBreed>>{

        @Override
        protected List<DogBreed> doInBackground(Void... voids) {
            return DogDatabase.getInstance(getApplication()).dao().getAllDogs();
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreeds) {
            //super.onPostExecute(dogBreeds);
            dogsRetrieved(dogBreeds);
            Log.d(TAG, "onPostExecute: Dogs retrieved from database ");
        }
    }
}
