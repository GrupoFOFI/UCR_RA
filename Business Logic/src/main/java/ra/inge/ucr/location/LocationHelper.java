package ra.inge.ucr.location;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

import ra.inge.ucr.da.Datos;
import ra.inge.ucr.da.Edificio;

import static android.R.attr.id;

/**
 * Created by Ricardo on 10/6/2016.
 */

public class LocationHelper {
    public Edificio[] closest;
    public int topidx[];
    public double mindist[];
    public int closestAmmount = 0;
    private static LatLng mLastLocation;

    public static void updateLastLocation(LatLng location) {
        mLastLocation = location;
    }

    public static LatLng getLastLocation() {
        return mLastLocation;
    }

    /**
     *
     * @param lastLoc
     * @param ammount
     * @return
     */
    public Edificio[] getClosestBuildings(LatLng lastLoc, int ammount){
        Edificio[] closest = new Edificio[ammount];
        topidx = new int[ammount];
        mindist = new double[ammount];
        for(int i=0;i<ammount;i++){
            mindist[i] = Double.MAX_VALUE;
        }
        for(Edificio ed : Datos.edificios){
            double temp = distance(lastLoc.latitude, ed.getLat(), lastLoc.longitude, ed.getLng(), 0,0);
            Datos.distances[ed.getId()-1]= temp;
            for(int i=0;i<ammount;i++){
                if(temp<= mindist[i]){
                    for(int j = ammount-1; j>i;j--){
                        mindist[j]=mindist[j-1];
                        topidx[j]=topidx[j-1];
                    }
                    mindist[i]=temp;
                    topidx[i]=ed.getId()-1;
                    break;
                }
            }
        }
        for(int i=0;i<ammount;i++){
            closest[i] = Datos.edificios.get(topidx[i]);
        }
        return closest;
    }

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


    public Edificio pointingCamera (double xCam, double yCam , LatLng loc ){
        //calculate distance
        float errorAngle =0;
        double v1 = 0; double v2=0 ; // first vector
        double productoPunto = 0;
        double mod1 = 0 ; double mod2=0;
        double angulo = 0;
        Edificio c[] = getClosestBuildings(loc,3);
        for(int i = 0 ; i < 3 ; i++){
            errorAngle= getErrorAngle (loc, c[i].getLat() , c[i].getLng() );
            if(errorAngle != -1) {
                v1 = loc.latitude - c[i].getLat();
                v2 = loc.longitude -c[i].getLng();
                productoPunto = v1* xCam + v2*yCam;
                mod1 = Math.sqrt(Math.pow(v1,2) + Math.pow(v2,2) );
                mod2 = Math.sqrt(Math.pow(xCam,2) + Math.pow(yCam,2) );
                angulo = productoPunto / (mod1 *mod2);
                angulo = angulo * 180 / Math.PI;// a grados
                if (  angulo < errorAngle ){
                   return  c[i];
                }
            }
        }
        return null;
    }

   
    public float getErrorAngle (LatLng loc , double xBuild , double yBuild){
        Location newLocation = new Location("newlocation");
        newLocation.setLatitude(xBuild);
        newLocation.setLongitude(yBuild);
        Location d = new Location("");
        d.setLatitude(loc.latitude);
        d.setLongitude(loc.longitude);
        float dist = d.distanceTo(newLocation);
        float a;
        if(dist < 20 ) {
            a =   80 - dist * 5  ;
        } else {
            a = -1;
        }
        return a;
    }

}

