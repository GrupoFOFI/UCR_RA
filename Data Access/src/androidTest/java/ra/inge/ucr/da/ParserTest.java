package ra.inge.ucr.da;

import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.runner.AndroidJUnitRunner;

import ra.inge.ucr.da.parser.NodeParser;

import static junit.framework.Assert.*;

/**
 * Created by enrico on 11/20/16.
 */
@RunWith(AndroidJUnit4.class)
public class ParserTest extends AndroidJUnitRunner {
    @Test
    public void testGetNodes() throws Exception {
        Context context = getTestContext();
        NodeParser nodeParser = new NodeParser(context);
        LatLng[] nodes = nodeParser.getNodes();
        assertNotNull(nodes);
        assertEquals(156, nodes.length);
    }

    private Context getTestContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}
