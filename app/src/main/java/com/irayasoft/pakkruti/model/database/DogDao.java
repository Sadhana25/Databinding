package com.irayasoft.pakkruti.model.database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.irayasoft.pakkruti.model.DogBreed;
import java.util.List;
@Dao
public interface DogDao {
    //inserting all data
    @Insert
    List<Long> insertAll(DogBreed... dogBreed);

    // get all row in table dogbreed
    @Query("SELECT * FROM dogbreed ")
    List<DogBreed> getAllDogs();

    // get individual item row with id
    @Query("SELECT * FROM dogbreed WHERE uuid = :id")
    DogBreed getDog(int id);

    //delete
    @Query("DELETE FROM dogbreed")
    void deleteAllDog();
}
