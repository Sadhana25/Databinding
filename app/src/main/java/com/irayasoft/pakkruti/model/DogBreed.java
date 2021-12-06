package com.irayasoft.pakkruti.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "dogbreed")
public class DogBreed {
    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    public String breed_id;

    @ColumnInfo(name = "breed_name")
    @SerializedName("name")
    public String breed_name;

    @ColumnInfo(name = "lifespan")
    @SerializedName("life_span")
    public String lifespan;

    @ColumnInfo(name = "breedGroup")
    @SerializedName("breed_group")
    public String breedGroup;

    @ColumnInfo(name = "breedFor")
    @SerializedName("bred_for")
    public String breedFor;

    @ColumnInfo(name = "temparament")
    @SerializedName("temperament")
    public String temparament;

    @ColumnInfo(name = "imgUrl")
    @SerializedName("url")
    public String imgUrl;
    //this create unique row id for table in database
    @PrimaryKey(autoGenerate = true)
    public int uuid;

    public String getBreed_name() {
        return breed_name;
    }

    public void setBreed_name(String breed_name) {
        this.breed_name = breed_name;
    }

    public int getUuid() {
        return uuid;
    }

    public DogBreed(String breed_id, String breed_name, String lifespan, String breedGroup, String breedFor, String temparament, String imgUrl) {
        this.breed_id = breed_id;
        this.breed_name = breed_name;
        this.lifespan = lifespan;
        this.breedGroup = breedGroup;
        this.breedFor = breedFor;
        this.temparament = temparament;
        this.imgUrl = imgUrl;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getBreed_id() {
        return breed_id;
    }

    public void setBreed_id(String breed_id) {
        this.breed_id = breed_id;
    }

    public String getLifespan() {
        return lifespan;
    }

    public void setLifespan(String lifespan) {
        this.lifespan = lifespan;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public void setBreedGroup(String breedGroup) {
        this.breedGroup = breedGroup;
    }

    public String getBreedFor() {
        return breedFor;
    }

    public void setBreedFor(String breedFor) {
        this.breedFor = breedFor;
    }

    public String getTemparament() {
        return temparament;
    }

    public void setTemparament(String temparament) {
        this.temparament = temparament;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
