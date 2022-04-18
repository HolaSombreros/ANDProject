package com.group2.foodie.viewmodel;

import androidx.lifecycle.ViewModel;

import com.group2.foodie.livedata.LocationMapLiveData;
import com.group2.foodie.repository.LocationRepository;

public class LocationsViewModel extends ViewModel {
    private LocationRepository repository;

    public LocationsViewModel() {
        repository = LocationRepository.getInstance();
    }

    public LocationMapLiveData getLocations(){
        return repository.getLocations();
    }

    public void init(){
        repository.init();
    }

}
