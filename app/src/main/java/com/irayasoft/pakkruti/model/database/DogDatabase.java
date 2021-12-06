package com.irayasoft.pakkruti.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.irayasoft.pakkruti.model.DogBreed;
@Database(entities = {DogBreed.class}, version = 1)
public abstract class DogDatabase extends RoomDatabase {
    private static DogDatabase INSTANCE;
    //singleton class
    public static DogDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DogDatabase.class, "dog.db")
                    .build();

        }

        return INSTANCE;
    }
    // accessing every interface methode
    public abstract DogDao dao();
}
