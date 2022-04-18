package com.group2.foodie.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.LocationMapLiveData;

public class LocationRepository {
    private static LocationRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private LocationMapLiveData locations;

    public LocationRepository() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
    }

    public static LocationRepository getInstance() {
        if(instance == null)
            instance = new LocationRepository();
        return instance;
    }
    public void init() {
        locations = new LocationMapLiveData(dbRef.child("locations"));
    }

    public LocationMapLiveData getLocations() {
        return locations;
    }
}
