package ra.inge.ucr.ucraumentedreality.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ra.inge.ucr.da.Edificio;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.utils.Utils;


public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener, LocationListener {

    private MapView mMapView;
    private GoogleMap googleMap;
    private Marker[] markers;
    private LocationManager locationManager;
    private static final int CLOSEST_AMOUNT = 3;

    private View rootView;
    private Utils utils;

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    private LocationRequest mLocationRequest;
    private static final long POLLING_FREQ = 200;
    private static final long FASTEST_UPDATE_FREQ = 1000;
    private LocationHelper locationHelper;

    public MapsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        setupMap(savedInstanceState);

        utils = new Utils(getActivity(), getContext());

        buildGoogleApiClient();
        return rootView;
    }


    void setupMap(Bundle savedInstanceState) {
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();
        mMapView.getMapAsync(this);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            utils.requestLocationPermission();
            return;
        }
        this.googleMap.setMyLocationEnabled(true);
        markers = new Marker[CLOSEST_AMOUNT];
        for(int i = 0;i< CLOSEST_AMOUNT;i++){
            markers[i] = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.building)).visible(false)
                    .position(new LatLng(0.0,0.0)).title(""));
        }
        locationHelper  = new LocationHelper();
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Fragment methods
     */

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    /**
     * Location Methods
     */

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        utils.requestLocationPermission();
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            String error = getResources().getString(R.string.no_location_detected);
            Log.e("ERROR",error);
        }else{
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 12);
            googleMap.animateCamera(cameraUpdate);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i("TAG", "Connection suspended");
//        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        String err = "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode();
        Log.e("ERROR", err);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        locationHelper.updateLastLocation(new LatLng(location.getLatitude(), location.getLongitude()));
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        Edificio[] cercanos = locationHelper.getClosestBuildings(loc, CLOSEST_AMOUNT);
        //Log.i("", cercanos[0].getNmbr() + " " + cercanos[1].getNmbr() + " " + cercanos[2].getNmbr());
        for (int i = 0; i < CLOSEST_AMOUNT; i++) {
            markers[i].setPosition(new LatLng(cercanos[i].getLat(), cercanos[i].getLng()));
            markers[i].setTitle(cercanos[i].getNmbr());
            if(!markers[i].isVisible())
                markers[i].setVisible(true);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }


}

