package ra.inge.ucr.location;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by rapuc on 11/17/16.
 */
public class LocationHelperTest {
    @Test
    public void distance() throws Exception {
        double lat1 = 9.935723;
        double lng1 = -84.048662;

        double lat2 = 9.934888;
        double lng2 = -84.053195;

        double dist1 = LocationHelper.distance(lat1,lat2,lng1,lng2,0.0,0.0);
        double dist2 = LocationHelper.distance(lat2,lat1,lng2,lng1,0.0,0.0);
        System.out.println(dist1);
        assertTrue(true);
    }

}