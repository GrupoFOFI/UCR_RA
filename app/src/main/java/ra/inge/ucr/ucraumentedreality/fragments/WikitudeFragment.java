package ra.inge.ucr.ucraumentedreality.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import java.io.IOException;

import ra.inge.ucr.da.Edificio;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.location.SensorHelper;
import ra.inge.ucr.location.listener.OnLookAtBuildingListener;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.utils.Utils;


/**
 *
 */
public class WikitudeFragment extends Fragment implements OnLookAtBuildingListener {

    private static final int PERMISSION_REQUEST_CAMERA = 999;
    private SensorHelper snsrhlpr;

    private ArchitectView architectView;
    private static final int CLOSEST_AMMOUNT = 3;

    private Utils utils;

    LocationHelper locationHelper = new LocationHelper();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_wikitude, container, false);

        utils = new Utils(getActivity(), getContext());
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

        architectView = (ArchitectView)rootView.findViewById(R.id.architectView); // I am the architect
        final StartupConfiguration config = new StartupConfiguration(getResources().getString(R.string.wikitude_key));
        architectView.onCreate(config);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                architectView.setLocation(location.getLatitude(),location.getLongitude(),100);

                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                Edificio[] cercanos = locationHelper.getClosestBuildings(loc, CLOSEST_AMMOUNT);
                architectView.callJavascript("World.loadPoisFromJsonData(" + cercanos[0].getId() + "," + cercanos[1].getId() + "," + cercanos[2].getId() + ")");
                // + cercanos[0].getId() + "," + cercanos[1].getId() + "," + cercanos[2].getId() +
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);



        return rootView;
    }



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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         architectView.onPostCreate();
        try {
            architectView.load("index.html");
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        snsrhlpr.stop();
        architectView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        snsrhlpr.start();
        architectView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();
    }

    @Override
    public void onStartLookingAtBuilding(Edificio edificio) {
        utils.snack_log(getActivity().getWindow().getDecorView(),"Estoy viendo el edificio " + edificio.getNmbr());
        Log.i("WIKITUDE_FRAGMENT", "Estoy viendo el edificio " + edificio.getNmbr());

    }

    @Override
    public void onStopLookingAtBuilding(Edificio edificio) {
        utils.snack_log(getActivity().getWindow().getDecorView(),"Ya no estoy viendo el edificio " + edificio.getNmbr());
        Log.i("WIKITUDE_FRAGMENT", "Ya no estoy viendo el edificio " + edificio.getNmbr());
    }
}
