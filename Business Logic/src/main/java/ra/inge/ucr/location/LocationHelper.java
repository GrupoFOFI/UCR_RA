package ra.inge.ucr.location;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.da.entity.TargetType;
import ra.inge.ucr.location.listener.OnBuildingsSetListener;
import ra.inge.ucr.location.listener.OnLocationSetListener;
import ra.inge.ucr.location.listener.OnMonumentsSetListener;

/**
 * <h1> Location Helper </h1>
 * <p>
 * Class used to handle the location methods
 * </p>
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class LocationHelper implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    public static final int TARGET_AMMOUNT = 3;
    public int topidx[];
    public double mindist[];
    private static LatLng mLastLocation;
    /**
     * Represents a geographical location.
     */
    protected Location latestLocation;
    /**
     * The application Context
     */
    private Context mContext;

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * MapsFragment listener
     */
    private OnLocationSetListener onLocationSetListener;

    /**
     * CloseBuildingsFragment listener
     */
    private OnBuildingsSetListener onBuildingsSetListener;

    /**
     * CloseMonumentsFragment listener
     */
    private OnMonumentsSetListener onMonumentsSetListener;

    private TargetObject[] closeMonuments;
    private TargetObject[] closeBuildings;


    // Define a listener that responds to location updates
    LocationListener locationListener;
    LocationManager locationManager;

    /**
     * Method that updates the last location
     *
     * @param location
     */
    public static void updateLastLocation(LatLng location) {
        mLastLocation = location;
    }

    /**
     * Getter for the last location
     *
     * @return
     */
    public static LatLng getLastLocation() {
        return mLastLocation;
    }


    public LocationHelper(Context context) {
        this.mContext = context;
        buildGoogleApiClient();

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        new AlertDialog.Builder(context).setTitle("Permiso de ubicación").setMessage("Necesitamos tu ubicación para que el app funcione").show();

                    } else {

                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
            }
        }


    }


    /**
     * Method that retrieves the closest buildings
     *
     * @param ammount
     * @return
     */
    public TargetObject[] getClosestBuildings(int ammount) {
        if (latestLocation != null) {
            TargetObject[] closest = new TargetObject[ammount];
            topidx = new int[ammount];
            mindist = new double[ammount];
            for (int i = 0; i < ammount; i++) {
                mindist[i] = Double.MAX_VALUE;
            }
            for (TargetObject ed : Data.targetObjects) {
                if (ed.getType() == TargetType.BUILDING) {
                    double temp = distance(latestLocation.getLatitude(), ed.getLatitude(), latestLocation.getLongitude(), ed.getLongitude(), 0, 0);
                    Data.distances[ed.getId() - 1] = temp;
                    for (int i = 0; i < ammount; i++) {
                        if (temp <= mindist[i]) {
                            for (int j = ammount - 1; j > i; j--) {
                                mindist[j] = mindist[j - 1];
                                topidx[j] = topidx[j - 1];
                            }
                            mindist[i] = temp;
                            topidx[i] = ed.getId() - 1;
                            break;
                        }
                    }
                }
            }
            for (int i = 0; i < ammount; i++) {
                closest[i] = Data.targetObjects.get(topidx[i]);
            }
            return closest;
        }
        return null;
    }


    /**
     * Method that retrieves the closest monuments
     *
     * @param ammount
     * @return
     */
    public TargetObject[] getClosestMonuments(int ammount) {
        if (latestLocation != null) {
            TargetObject[] closest = new TargetObject[ammount];
            topidx = new int[ammount];
            mindist = new double[ammount];
            for (int i = 0; i < ammount; i++) {
                mindist[i] = Double.MAX_VALUE;
            }

            for (TargetObject ed : Data.targetObjects) {
                if (ed.getType() == TargetType.MONUMENT) {
                    double temp = distance(latestLocation.getLatitude(), ed.getLatitude(), latestLocation.getLongitude(), ed.getLongitude(), 0, 0);
                    Data.distances[ed.getId() - 1] = temp;
                    for (int i = 0; i < ammount; i++) {
                        if (temp <= mindist[i]) {
                            for (int j = ammount - 1; j > i; j--) {
                                mindist[j] = mindist[j - 1];
                                topidx[j] = topidx[j - 1];
                            }
                            mindist[i] = temp;
                            topidx[i] = ed.getId() - 1;
                            break;
                        }
                    }
                }
            }
            for (int i = 0; i < ammount; i++) {
                closest[i] = Data.targetObjects.get(topidx[i]);
            }
            return closest;
        }
        return null;
    }

    /**
     * Method that retrieves the distance
     *
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @param el1
     * @param el2
     * @return
     */
    public static double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {
        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * Method that retrieves tha building that the camera is currently pointing to
     *
     * @param xCam
     * @param yCam
     * @param loc
     * @return
     */
    public TargetObject pointingCamera(double xCam, double yCam, LatLng loc) {
        //calculate distance
        float errorAngle = 0;
        double v1 = 0;
        double v2 = 0; // first vector
        double productoPunto = 0;
        double mod1 = 0;
        double mod2 = 0;
        double angulo = 0;
        TargetObject c[] = getClosestBuildings(3);
        for (int i = 0; i < 3; i++) {
            errorAngle = getErrorAngle(loc, c[i].getLatitude(), c[i].getLongitude());
            if (errorAngle != -1) {
                v1 = loc.latitude - c[i].getLatitude();
                v2 = loc.longitude - c[i].getLongitude();
                productoPunto = v1 * xCam + v2 * yCam;
                mod1 = Math.sqrt(Math.pow(v1, 2) + Math.pow(v2, 2));
                mod2 = Math.sqrt(Math.pow(xCam, 2) + Math.pow(yCam, 2));
                angulo = productoPunto / (mod1 * mod2);
                angulo = angulo * 180 / Math.PI;// a grados
                if (angulo < errorAngle) {
                    return c[i];
                }
            }
        }
        return null;
    }


    /**
     * Method that retrieves the error angle
     *
     * @param loc
     * @param xBuild
     * @param yBuild
     * @return
     */
    public float getErrorAngle(LatLng loc, double xBuild, double yBuild) {
        Location newLocation = new Location("newlocation");
        newLocation.setLatitude(xBuild);
        newLocation.setLongitude(yBuild);
        Location d = new Location("");
        d.setLatitude(loc.latitude);
        d.setLongitude(loc.longitude);
        float dist = d.distanceTo(newLocation);
        float a;
        if (dist < 20) {
            a = 80 - dist * 5;
        } else {
            a = -1;
        }
        return a;
    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        latestLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (latestLocation != null) {
//
            Log.d("konri2", "" + latestLocation.getLatitude());
            Log.d("konri2", "" + latestLocation.getLongitude());
            calculateClosest();

        } else {
            Toast.makeText(mContext, "error", Toast.LENGTH_LONG).show();
        }
    }

    
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i("konri", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i("konri", "Connection suspended");
        mGoogleApiClient.connect();
    }


    /**
     * Method that updates the custom markers when the location changes
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        Log.d("konri", "Mae si entra" + location);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        latestLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            latestLocation = location;

            calculateClosest();
            Log.d("konri", "" + latestLocation.getLatitude());
            Log.d("konri", "" + latestLocation.getLongitude());
        }

        Log.d("konri", "No estoy entrado acá");
    }

    void calculateClosest() {
        closeBuildings = getClosestBuildings(TARGET_AMMOUNT);
        if (closeBuildings != null) {
            if (onBuildingsSetListener != null) {
                onBuildingsSetListener.onBuildingsCalculated();
            }
        }
        closeMonuments = getClosestMonuments(TARGET_AMMOUNT);
        if (closeMonuments != null) {
            if (onMonumentsSetListener != null) {
                onMonumentsSetListener.onMonumentsCalculated();
            }
        }
    }

    /**
     * Setter for the MapsFragment listener
     *
     * @param onLocationSetListener
     */
    public void setOnLocationSetListener(OnLocationSetListener onLocationSetListener) {
        this.onLocationSetListener = onLocationSetListener;
    }

    /**
     * Setter for the ClosestBuildingsFragment listener
     *
     * @param onBuildingsSetListener
     */
    public void setOnBuildingsSetListener(OnBuildingsSetListener onBuildingsSetListener) {
        this.onBuildingsSetListener = onBuildingsSetListener;
    }

    /**
     * Setter for the ClosestMonumentFragment listener
     *
     * @param onMonumentsSetListener
     */
    public void setOnMonumentsSetListener(OnMonumentsSetListener onMonumentsSetListener) {
        this.onMonumentsSetListener = onMonumentsSetListener;
    }

    public TargetObject[] getCloseMonuments() {
        return closeMonuments;
    }

    public TargetObject[] getCloseBuildings() {
        return closeBuildings;
    }
}

