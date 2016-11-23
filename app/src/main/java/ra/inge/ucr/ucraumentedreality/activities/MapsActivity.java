package ra.inge.ucr.ucraumentedreality.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.da.Path;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.location.NavigationHelper;
import ra.inge.ucr.location.SensorHelper;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.Vuforia.VideoPlayback.app.VideoPlayback.Arrow;
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
    private Location previousLocation;
    private LocationRequest mLocationRequest;
    private static final long POLLING_FREQ = 200;
    private static final long FASTEST_UPDATE_FREQ = 1000;
    private LocationHelper locationHelper;
    private SensorHelper sensorHelper;
    private boolean hasObjective = false;
    private TargetObject objective;
    private List<Path> paths;
    private Polyline polyline;
    private ImageView arrowUp, arrowLeft, arrowRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = new Utils(this, getApplicationContext());
        setContentView(R.layout.fragment_maps);
        setupMap(savedInstanceState);
        locationHelper = new LocationHelper();
        sensorHelper = new SensorHelper(getApplicationContext());
        sensorHelper.start();
        buildGoogleApiClient();

        if(getIntent().hasExtra("TargetName")){

            TargetObject to = Data.getByName(getIntent().getStringExtra("TargetName"));
            hasObjective = true;
            objective = to;
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.arrow_view);
        relativeLayout.setBackgroundColor(Color.TRANSPARENT);
        relativeLayout.bringToFront();

        arrowUp = (ImageView) findViewById(R.id.arrow_up);
        arrowUp.bringToFront();
        arrowUp.setVisibility(View.GONE);

        arrowLeft = (ImageView) findViewById(R.id.arrow_left);
        arrowLeft.bringToFront();
        arrowLeft.setVisibility(View.GONE);

        arrowRight = (ImageView) findViewById(R.id.arrow_right);
        arrowRight.bringToFront();
        arrowRight.setVisibility(View.GONE);
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
            paths = newDestination(objective);
        }
        previousLocation = mLastLocation;
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
        if(hasObjective) {
            polyline.remove();
            List<Path> newPaths = newDestination(objective);
            if (paths.get(0).getPoints().size() < newPaths.get(0).getPoints().size()) {
                paths = newPaths;
                Path path = paths.get(0);
                List<LatLng> route = path.getPoints();
                directionSound(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), route.get(0));
                hasObjective = false;
                objective = null;
            }

            if (mLastLocation.distanceTo(previousLocation) > 5) {
                Path path = paths.get(0);
                List<LatLng> route = path.getPoints();
                directionSound(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), route.get(0));
                previousLocation = mLastLocation;
            }
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

    public List<Path> newDestination(TargetObject to) {
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
            paths = na.getPathsToPoint(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), entrance-1);
            if (paths != null && !paths.isEmpty()) {
                polyline = na.drawPrimaryLinePath((ArrayList<LatLng>) paths.get(0).getPoints(), googleMap);
            }
        }
        return paths;
    }

    public void directionSound(LatLng point1, LatLng point2){
        int azimuth = sensorHelper.getAzimuth();

        double dLon = (point2.longitude - point1.longitude);

        double y = Math.sin(dLon) * Math.cos(point2.latitude);
        double x = Math.cos(point1.latitude) * Math.sin(point2.latitude) - Math.sin(point1.latitude)
                * Math.cos(point2.latitude) * Math.cos(dLon);

        double angle = Math.atan2(y, x);

        angle = Math.toDegrees(angle);
        angle = ((angle + 360) % 360)-90;

        double direccion = Math.abs(azimuth - angle);
        if(direccion >= 315 || direccion < 45) {
            showArrow(Arrow.UP);
            try {
                AssetFileDescriptor afd = getAssets().openFd("Continue.mp3");
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.start();
            }catch (Exception e){}
        }
        else if(direccion >= 45 && direccion < 135) {
            showArrow(Arrow.RIGHT);
            try {
                AssetFileDescriptor afd = getAssets().openFd("Derecha.mp3");
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.start();
            }catch (Exception e){}
        }
        else if(direccion >= 135 && direccion < 225) {
            arrowLeft.setVisibility(View.GONE);
            arrowRight.setVisibility(View.GONE);
            arrowUp.setVisibility(View.GONE);
            try {
                AssetFileDescriptor afd = getAssets().openFd("Vuelta.mp3");
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.start();
            }catch (Exception e){}
        }
        else {
            showArrow(Arrow.LEFT);
            try {
                AssetFileDescriptor afd = getAssets().openFd("Izquierda.mp3");
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.start();
            }catch (Exception e){}
        }
    }

    public void showArrow(Arrow arrow) {
        switch (arrow) {
            case LEFT:
                arrowLeft.setVisibility(View.VISIBLE);
                arrowRight.setVisibility(View.GONE);
                arrowUp.setVisibility(View.GONE);
                break;
            case RIGHT:
                arrowRight.setVisibility(View.VISIBLE);
                arrowLeft.setVisibility(View.GONE);
                arrowUp.setVisibility(View.GONE);
                break;
            case UP:
                arrowUp.setVisibility(View.VISIBLE);
                arrowLeft.setVisibility(View.GONE);
                arrowRight.setVisibility(View.GONE);
                break;
        }
    }
}
