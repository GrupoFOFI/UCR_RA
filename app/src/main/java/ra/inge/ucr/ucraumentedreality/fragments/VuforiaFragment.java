package ra.inge.ucr.ucraumentedreality.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import ra.inge.ucr.da.Edificio;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.location.SensorHelper;
import ra.inge.ucr.location.listener.OnLookAtBuildingListener;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.Vuforia.VideoPlayback.app.VideoPlayback.VideoPlayback;
import ra.inge.ucr.ucraumentedreality.utils.Utils;


public class VuforiaFragment extends Fragment implements OnLookAtBuildingListener {

    private static final int PERMISSION_REQUEST_CAMERA = 999;
    private SensorHelper snsrhlpr;

    private static final int CLOSEST_AMMOUNT = 3;

    private Utils utils;

    LocationHelper locationHelper = new LocationHelper();


    View rootView;


    /**
     * Method that prepares the components the fragment needs
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.content_vuforia, container, false);
        utils = new Utils(getActivity(), getContext());

        addVideoPlayBack();

        // request camera permission
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }

        snsrhlpr = new SensorHelper(getContext());
        snsrhlpr.setOnLookAtBuildingListener(this);
        snsrhlpr.start();

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                Edificio[] cercanos = locationHelper.getClosestBuildings(loc, CLOSEST_AMMOUNT);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        return rootView;
    }


    void addVideoPlayBack() {

        VideoPlayback videoPlayback = new VideoPlayback();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        fragmentTransaction.replace(R.id.content_vuforia_fragment, videoPlayback);
//        fragmentTransaction.commit();

    }


    /**
     * Method that requests for permision to use the Camera
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                }
                break;
            }
        }
    }

    /**
     * On Pause Fragment Method
     */
    @Override
    public void onPause() {
        super.onPause();
        snsrhlpr.stop();
    }

    /**
     * Method to warn in case the app has low memory to run
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * On Resume Fragment Method
     */
    @Override
    public void onResume() {
        super.onResume();
        snsrhlpr.start();
    }


    /**
     * On Destroy Fragment Method
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Method used to create UX when the user is looking at a building
     *
     * @param edificio
     */
    @Override
    public void onStartLookingAtBuilding(Edificio edificio) {
        utils.snack_log(getActivity().getWindow().getDecorView(), "Estoy viendo el edificio " + edificio.getNmbr());
        Log.i("VUFORIA_FRAGMENT", "Estoy viendo el edificio " + edificio.getNmbr());

    }

    /**
     * Method used to create de UX when the user stops looking at a building
     *
     * @param edificio
     */
    @Override
    public void onStopLookingAtBuilding(Edificio edificio) {
        utils.snack_log(getActivity().getWindow().getDecorView(), "Ya no estoy viendo el edificio " + edificio.getNmbr());
        Log.i("VUFORIA_FRAGMENT", "Ya no estoy viendo el edificio " + edificio.getNmbr());
    }


}
