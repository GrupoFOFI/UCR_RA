package ra.inge.ucr.ucraumentedreality.fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import ra.inge.ucr.da.Path;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.location.NavigationHelper;
import ra.inge.ucr.ucraumentedreality.R;
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
public class MapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener, LocationListener {

    private MapView mMapView;
    private GoogleMap googleMap;
    private Marker[] markers;
    public static final int CLOSEST_AMOUNT = 3;

    private View rootView;
    private Utils utils;

    private TargetObject[] closeMonuments;
    private TargetObject[] closeBuildings;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private LocationRequest mLocationRequest;
    private static final long POLLING_FREQ = 200;
    private static final long FASTEST_UPDATE_FREQ = 1000;
    private LocationHelper locationHelper;
    private NavigationHelper navigationHelper;

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
        navigationHelper = new NavigationHelper(this.getContext());
        List<Path> routes = navigationHelper.getPathsToPoint(new LatLng(9.938747, -84.052131),37);
        /*for(int i = 0;i<routes.size();i++){
            List<LatLng> route = routes.get(i).getPoints();
            drawRoute(route);
        }*/
//        List<LatLng> route = routes.get(0).getPoints();
//        for(int i = 0;i<route.size();i++){
//            Log.d("Tag", route.get(i).toString());
//        }
//        drawRoute(route);
        /*for(int i = 0;i<route.size();i++){
            Log.d("Tag", route.get(i).toString());
        }*/
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
     * Method that updates the custom markers when the location changes
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        locationHelper.updateLastLocation(new LatLng(location.getLatitude(), location.getLongitude()));

        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        closeBuildings = locationHelper.getClosestBuildings(mLastLocation, CLOSEST_AMOUNT);
        closeMonuments = locationHelper.getClosestMonuments(mLastLocation, CLOSEST_AMOUNT);

        Log.d("konri", "Los edificios son => " + closeBuildings[0].getName() + " " + closeBuildings[1].getName() + " " + closeBuildings[2].getName());
        Log.d("konri", "Los monumentos son => " + closeMonuments[0].getName() + " " + closeMonuments[1].getName() + " " + closeMonuments[2].getName());

        addMarkers();

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


    public void drawRoute(List points){
        PolylineOptions lineOptions = new PolylineOptions();

        lineOptions.addAll(points);
        if (lineOptions != null) {
            googleMap.addPolyline(lineOptions);
        } else {
            Log.d("onPostExecute", "without Polylines drawn");
        }
    }
}

