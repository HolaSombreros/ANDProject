package com.group2.foodie.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.group2.foodie.R;
import com.group2.foodie.model.Location;
import com.group2.foodie.viewmodel.LocationsViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment{
    private GoogleMap map;
    private LocationsViewModel viewModel;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_map, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                    @Override
                    public void onComplete(@NonNull Task<android.location.Location> task) {
                        android.location.Location loc = task.getResult();
                        if (loc != null) {
                            try {
                                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(
                                        loc.getLatitude(), loc.getLongitude(), 1
                                );
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()), 10.0f));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        }
        else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        viewModel = new ViewModelProvider(getActivity()).get(LocationsViewModel.class);
        viewModel.init();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //locationManager = getS
        if(mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                   map = googleMap;
                   viewModel.getLocations().observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
                       @Override
                        public void onChanged(List<Location> locations) {
                            for (Location location : locations) {
                                Log.w("location", String.valueOf(location.getLatitude()));
                                map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),
                                        location.getLongitude())).title(location.getName()));
                            }
                          // map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.858131, 9.847588), 10.0f));
                       }
                    });
                }
            });
        }
    }

}
