package ra.inge.ucr.ucraumentedreality.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import java.io.IOException;

import ra.inge.ucr.da.Edificio;
import ra.inge.ucr.location.SensorHelper;
import ra.inge.ucr.location.listener.OnLookAtBuildingListener;
import ra.inge.ucr.ucraumentedreality.R;

/**
 * Created by Konrad on 10/12/16.
 */

public class WikitudeFragment extends Fragment implements OnLookAtBuildingListener {
    ArchitectView architectView;

    private static final int PERMISSION_REQUEST_CAMERA = 999;
    private SensorHelper snsrhlpr;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_wikitude, container, false);
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
        Log.i("WIKITUDE_FRAGMENT", "Estoy viendo el edificio " + edificio.getNmbr());
    }

    @Override
    public void onStopLookingAtBuilding(Edificio edificio) {
        Log.i("WIKITUDE_FRAGMENT", "Ya no estoy viendo el edificio " + edificio.getNmbr());
    }
}
