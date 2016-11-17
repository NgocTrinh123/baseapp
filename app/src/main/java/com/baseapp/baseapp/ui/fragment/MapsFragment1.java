package com.baseapp.baseapp.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.baseapp.baseapp.R;
import com.baseapp.baseapp.base.BaseMainFragment;
import com.baseapp.baseapp.interfaces.RequestPermissionCallback;
import com.baseapp.baseapp.listener.OnClickOptionMenu;
import com.baseapp.baseapp.service.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tunglam on 11/17/16.
 */
public class MapsFragment1 extends BaseMainFragment {

    @Bind(R.id.map)
    MapView mapView;

    @Bind(R.id.rlMap)
    RelativeLayout rlMap;

    GoogleMap googleMap;
    private LatLng myLocation;
    private GPSTracker gps;

    public static BaseMainFragment create(String name, boolean b) {
        return new MapsFragment1();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLeftText("back", new OnClickOptionMenu() {
            @Override
            public void onClick() {

            }
        });
        setShowHomeMenu(true);
        setTitle("MapsFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(onMapReadyCallback);
    }

    private OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap map) {
            googleMap = map;


            // MapWrapperLayout initialization
            // 61 - default marker heightWrapMap + offset between the default InfoWindow bottom edge and it's content bottom edge
            //get my location
            if (myLocation == null) {
                myLocation = new LatLng(2.754525, 101.7021233);
                getMyLocation();
            }
            gotoLocation(myLocation.latitude, myLocation.longitude);

            //setting map
            settingMap();

        }


    };

    private void gotoLocation(double latitude, double longitude) {
        if (googleMap != null) {
            // create marker
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            // adding marker
            googleMap.addMarker(marker);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14f));
        }
    }

    private void getMyLocation() {
        requestPermissions(new RequestPermissionCallback() {
            @Override
            public void onAccepted() {
                gps = new GPSTracker(getBaseActivity(), new LocationUpdateListener());
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                LocationManager locationManager = (LocationManager)
                        getBaseActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, false);
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    gotoLocation(location.getLatitude(), location.getLongitude());
                } else {
                    // Check if GPS enabled
                    if (gps.canGetLocation()) {
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        myLocation = new LatLng(gps.getLatitude(), gps.getLongitude());
                        gotoLocation(latitude, longitude);
                    }
                }
            }

            @Override
            public void onDenied() {
//                DialogUtils.showDialogWithMessage(getContext(), getString(R.string.can_not_find_your_location));
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private void settingMap() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                GoogleDirection.withServerKey(getString(R.string.google_map_key))
                        .from(myLocation)
                        .to(latLng)
                        .avoid(AvoidType.FERRIES)
                        .avoid(AvoidType.HIGHWAYS)
                        .execute(new DirectionCallback() {
                            @Override
                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                if (direction.isOK()) {
                                    googleMap.addMarker(new MarkerOptions().position(myLocation));
                                    googleMap.addMarker(new MarkerOptions().position(latLng));

                                    ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                                    googleMap.addPolyline(DirectionConverter.createPolyline(getActivity(), directionPositionList, 5, Color.RED));
                                } else {
                                    // Do something
                                }
                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {
                                // Do something
                            }
                        });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private class LocationUpdateListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null && googleMap != null) {
                if (gps != null) {
                    gps.stopUsingGPS();
                }
                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                gotoLocation(location.getLatitude(), location.getLongitude());
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    }

    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyInfoWindowAdapter() {
            myContentsView = getActivity().getLayoutInflater().inflate(R.layout.dialog_info, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
