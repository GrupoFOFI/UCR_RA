package ra.inge.ucr.location;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ra.inge.ucr.da.Path;

import static junit.framework.Assert.*;

/**
 * Created by enrico on 11/20/16.
 */
@RunWith(AndroidJUnit4.class)
public class NavigationHelperTest extends AndroidJUnitRunner {
    @Test
    public void getPathsToPoint() throws Exception {
        NavigationHelper navigationHelper = new NavigationHelper(getTestContext());
        //List<Path> paths = navigationHelper.getPathsToPoint(new LatLng(9.939178, -84.052879), 37);
        List<Path> paths = navigationHelper.getPathsToPoint(new LatLng(9.935723, -84.048662), 1);
        assertEquals(3, paths.size());
    }

    public Context getTestContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}