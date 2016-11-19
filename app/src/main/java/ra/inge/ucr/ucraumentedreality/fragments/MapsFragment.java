package ra.inge.ucr.ucraumentedreality.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.internal.Command;
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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import ra.inge.ucr.da.Edificio;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.location.SensorHelper;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.utils.CommandHandler;
import ra.inge.ucr.ucraumentedreality.utils.Utils;

/**
 * <h1> ViewPager Adapter </h1>
 * <p>
 * Adapter for the fragments on the Application
 * </p>
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener, LocationListener {

    private MapView mMapView;
    private GoogleMap googleMap;
    private Marker[] markers;
    private static final int CLOSEST_AMOUNT = 3;

    private View rootView;
    private Utils utils;
    private Edificio[] cercanos;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private LocationRequest mLocationRequest;
    private static final long POLLING_FREQ = 200;
    private static final long FASTEST_UPDATE_FREQ = 1000;
    private LocationHelper locationHelper;
    private SensorHelper sensorHelper;
    private TextView textView;
    private CommandHandler commandHandler;

    /**
     * Empty constructor for the class
     */
    public MapsFragment() {
    }


    /**
     * Method that prepares the components that google maps need to work
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        utils = new Utils(getActivity(), getContext());
        setupMap(savedInstanceState);
        sensorHelper = new SensorHelper(getContext());
        //sensorHelper.setOnLookAtBuildingListener(this);
        sensorHelper.start();

        buildGoogleApiClient();
        commandHandler = new CommandHandler(getContext());
        return rootView;
    }


    /**
     * Method that sets up the map
     *
     * @param savedInstanceState
     */
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

    /**
     * Method notified when the map is ready
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            utils.requestLocationPermission();
            return;
        }
        this.googleMap.setMyLocationEnabled(true);
        markers = new Marker[CLOSEST_AMOUNT];
        for (int i = 0; i < CLOSEST_AMOUNT; i++) {
            markers[i] = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.building)).visible(false)
                    .position(new LatLng(0.0, 0.0)).title(""));
        }
        locationHelper = new LocationHelper();
        textView = (TextView)getView().findViewById(R.id.angulo);
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
     * Method that builds a GoogleApiClient and uses the addApi() method to request the LocationServices API.
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
            Log.e("ERROR", error);
        } else {
            locationHelper.updateLastLocation(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 12);
            googleMap.animateCamera(cameraUpdate);
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
//        mGoogleApiClient.connect();
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
        mLastLocation = location;
        locationHelper.updateLastLocation(new LatLng(location.getLatitude(), location.getLongitude()));
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        cercanos = locationHelper.getClosestBuildings(loc, CLOSEST_AMOUNT);
        //Log.i("", cercanos[0].getNmbr() + " " + cercanos[1].getNmbr() + " " + cercanos[2].getNmbr());
        for (int i = 0; i < CLOSEST_AMOUNT; i++) {
            markers[i].setPosition(new LatLng(cercanos[i].getLat(), cercanos[i].getLng()));
            markers[i].setTitle(cercanos[i].getNmbr());
            if (!markers[i].isVisible())
                markers[i].setVisible(true);
        }
        LatLng point1 = new LatLng(9.993123, -84.098054);
        LatLng point2 = new LatLng(9.993931, -84.098034);
        directionSound(point1,point2);
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

    /**
     * Method that handles when a user drags a marker
     *
     * @param marker
     */
    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    /**
     * Method that handles when a user drags a marker
     *
     * @param marker
     */
    @Override
    public void onMarkerDrag(Marker marker) {

    }

    /**
     * Method that handles when a user ends dragging a marker
     *
     * @param marker
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    /**
     * Method that retrieves the closest buildings so the ClosesBuildingFragment can use them
     * {@link CloseBuildingsFragment};
     * .}
     *
     * @return
     */
    public Edificio[] getCercanos() {
        return cercanos;
    }

    public void drawRoute(ArrayList points){
        PolylineOptions lineOptions = new PolylineOptions();

        lineOptions.addAll(points);
        if (lineOptions != null) {
            googleMap.addPolyline(lineOptions);
        } else {
            Log.d("onPostExecute", "without Polylines drawn");
        }
    }

    public void directionSound(LatLng point1, LatLng point2){
        int azimuth = sensorHelper.getAzimuth();

        double dLon = (point2.longitude - point1.longitude);

        double y = Math.sin(dLon) * Math.cos(point2.latitude);
        double x = Math.cos(point1.latitude) * Math.sin(point2.latitude) - Math.sin(point1.latitude)
                * Math.cos(point2.latitude) * Math.cos(dLon);

        double angle = Math.atan2(y, x);

        angle = Math.toDegrees(angle);
        angle = (angle + 360) % 360;

        double direccion = Math.abs(azimuth - angle);
        if(direccion >= 315 || direccion < 45) {
            textView.setText("Continue");
            try {
                AssetFileDescriptor afd = getContext().getAssets().openFd("Continue.mp3");
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.start();
            }catch (Exception e){}
        }
        else if(direccion >= 45 && direccion < 135) {
            textView.setText("Derecha");
            try {
                AssetFileDescriptor afd = getContext().getAssets().openFd("Derecha.mp3");
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.start();
            }catch (Exception e){}
        }
        else if(direccion >= 135 && direccion < 225) {
            textView.setText("De la vuelta");
            try {
                AssetFileDescriptor afd = getContext().getAssets().openFd("Vuelta.mp3");
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.start();
            }catch (Exception e){}
        }
        else {
            textView.setText("Izquierda");
            try {
                AssetFileDescriptor afd = getContext().getAssets().openFd("Izquierda.mp3");
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.start();
            }catch (Exception e){}
        }
    }
}

