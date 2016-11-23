package ra.inge.ucr.ucraumentedreality.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.List;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.da.Path;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.location.NavigationHelper;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.utils.Utils;

/**
 * Class to hadle the usage of maps
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener, LocationListener {

    /**
     * The google map components
     */
    private MapView mMapView;
    private GoogleMap googleMap;
    private Marker[] markers;
    /**
     * Amount of buildings we want to have near
     */
    public static final int CLOSEST_AMOUNT = 3;

    private Utils utils;

    /**
     * The lists of the closest target objects
     */
    private TargetObject[] closeMonuments;
    private TargetObject[] closeBuildings;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private LocationRequest mLocationRequest;
    private static final long POLLING_FREQ = 200;
    private static final long FASTEST_UPDATE_FREQ = 1000;
    private LocationHelper locationHelper;
    private boolean hasObjective = false;
    private TargetObject objective;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = new Utils(this, getApplicationContext());
        setContentView(R.layout.fragment_maps);
        setupMap(savedInstanceState);
        locationHelper = new LocationHelper();
        buildGoogleApiClient();

        if(getIntent().hasExtra("Objective index")){
            TargetObject to = Data.getByName(getIntent().getStringExtra("Objective index"));
            hasObjective = true;
            objective = to;
        }

    }


    /**
     * Method that sets up the map
     *
     * @param savedInstanceState
     */
    void setupMap(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method notified when the map is ready
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            utils.requestLocationPermission();
            return;
        }
        this.googleMap.setMyLocationEnabled(true);

        markers = new Marker[CLOSEST_AMOUNT];
        for (int i = 0; i < CLOSEST_AMOUNT; i++) {
            markers[i] = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.building)).visible(false)
                    .position(new LatLng(0.0, 0.0)).title(""));
        }

//        locationHelper = new LocationHelper();
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(locationHelper.getLastLocation().latitude,
//                locationHelper.getLastLocation().longitude), 12);
//        googleMap.animateCamera(cameraUpdate);

    }


    /**
     * On Start Class method
     */
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    /**
     * On Stop Class method
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    /**
     * On Resume Class method
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if(getIntent().hasExtra("Objective index")){
            TargetObject to = Data.targetObjects.get(getIntent().getIntExtra("Objective index", 0));
            hasObjective = true;
            objective = to;
        }
    }

    /**
     * On Pause Class method
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * On Destroy Class method
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * On Low Memory Class method
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        utils.requestLocationPermission();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            String error = getResources().getString(R.string.no_location_detected);
            Log.e("ERROR", error);
        } else {
            locationHelper.updateLastLocation(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 12);
            googleMap.animateCamera(cameraUpdate);
        }
        if(hasObjective){
            newDestination(objective);
        }


    }

    /**
     * Method called when tha connection is suspended
     *
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.i("TAG", "Connection suspended");
        mGoogleApiClient.connect();
    }

    /**
     * Method called when tha connection failed
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        String err = "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode();
        Log.e("ERROR", err);
    }


    /**
     * Method that updates the custom markers when the location changes
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        if (mLastLocation != null) {
            mLastLocation = location;
            LocationHelper.updateLastLocation(new LatLng(location.getLatitude(), location.getLongitude()));

            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            closeBuildings = locationHelper.getClosestBuildings(mLastLocation, CLOSEST_AMOUNT);
            closeMonuments = locationHelper.getClosestMonuments(mLastLocation, CLOSEST_AMOUNT);

            Log.d("konri", "Los edificios son => " + closeBuildings[0].getName() + " " + closeBuildings[1].getName() + " " + closeBuildings[2].getName());
            Log.d("konri", "Los monumentos son => " + closeMonuments[0].getName() + " " + closeMonuments[1].getName() + " " + closeMonuments[2].getName());

            addMarkers();
        }

    }

    /**
     * Method that adds diferent markers to the map
     */
    void addMarkers() {
        for (int i = 0; i < CLOSEST_AMOUNT; i++) {
            markers[i].setPosition(new LatLng(closeBuildings[i].getLatitude(), closeBuildings[i].getLongitude()));
            markers[i].setTitle(closeBuildings[i].getName());
            if (!markers[i].isVisible())
                markers[i].setVisible(true);
        }

//        for (int i = 0; i < CLOSEST_AMOUNT; i++) {
//            markers[i].setPosition(new LatLng(getCloseMonuments()[i].getLatitude(), closeBuildings[i].getLongitude()));
//            markers[i].setTitle(closeBuildings[i].getName());
//            if (!markers[i].isVisible())
//                markers[i].setVisible(true);
//        }

    }

    /**
     * Method called when the user taps the marker info
     *
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    /**
     * Method that handles when a user taps a marker
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void newDestination(TargetObject to) {
        NavigationHelper na = new NavigationHelper(getBaseContext());
        List<Path> paths = null;
        LatLng[] nodes;
        int[] entrances = to.getEntrances();
        if (entrances.length > 0) {
            int entrance = entrances[0];
            if (entrances.length > 1) {
                nodes = na.getNodeParser().getNodes();
                double minDist = Integer.MAX_VALUE;
                for (int e : entrances) {
                    double temp = LocationHelper.distance(nodes[e].latitude,mLastLocation.getLatitude(),nodes[e].longitude,mLastLocation.getLongitude(),0,0);
                    if(temp < minDist){
                        minDist = temp;
                        entrance = e;
                    }
                }
            }
            paths = na.getPathsToPoint(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), entrance);
            if (paths != null && !paths.isEmpty()) {
                na.drawPrimaryLinePath((ArrayList<LatLng>) paths.get(0).getPoints(), googleMap);
            }
        }
    }
}
