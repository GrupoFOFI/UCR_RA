package ra.inge.ucr.ucraumentedreality.activities;

import android.app.AlertDialog;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.Manifest;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.pm.PackageManager;
import android.location.Location;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.Marker;

import ra.inge.ucr.da.Edificio;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.ucraumentedreality.R;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener, LocationListener {

    private int count;
    private GoogleMap mMap;
    private Marker[] markers;
    private static final int CLOSEST_AMOUNT = 3;

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
    LocationHelper locationHelper = new LocationHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        buildGoogleApiClient();
        markers = new Marker[CLOSEST_AMOUNT];
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Wat", "here");
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        Edificio[] cercanos = locationHelper.getClosestBuildings(loc, CLOSEST_AMOUNT);
        Log.i("", cercanos[0].getNmbr() + " " + cercanos[1].getNmbr() + " " + cercanos[2].getNmbr());
        for (int i = 0; i < CLOSEST_AMOUNT; i++) {
            if (markers[i] != null)
                markers[i].remove();
            markers[i] = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(cercanos[i].getLat(), cercanos[i].getLng()))
                    .title(cercanos[i].getNmbr())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        }
    }

    public void drawMarker(double varLat, double varLong) {

        String times = "Location # " + count;
        count++;
        String location = "Location has " + varLat + " latitude and " + varLong + " longitude";
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(varLat, varLong))
                .title(times)
                .snippet(location));
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        requestLocationPermission();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            String error = getResources().getString(R.string.no_location_detected);
            toastLog(error);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        String err = "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode();
        toastLog(err);

    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i("TAG", "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        toastLog("Click Info Window");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        toastLog("Touched marker!!");
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        toastLog("onMarkerDragStart");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        toastLog("onMarkerDragEnd");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        String str = "onMarkerDrag.  Current Position: " + marker.getPosition();
        toastLog(str);
    }

    void toastLog(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    new AlertDialog.Builder(this).setTitle("Permiso de ubicación").setMessage("Necesitamos tu ubicación para que el app funcione").show();

                } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        }
    }


}