package ra.inge.ucr.location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

import ra.inge.ucr.da.Datos;
import ra.inge.ucr.da.Edificio;

/**
 * Created by Ricardo on 10/6/2016.
 */

public class LocationHelper {
    public Edificio[] closest;
    public int topidx[];
    public double mindist[];
    public int closestAmmount = 0;

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
}
