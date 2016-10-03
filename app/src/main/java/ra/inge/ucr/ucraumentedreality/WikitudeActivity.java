package ra.inge.ucr.ucraumentedreality;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import java.io.IOException;

public class WikitudeActivity extends AppCompatActivity {
    ArchitectView architectView;

    private static final int PERMISSION_REQUEST_CAMERA = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wikitude);

        // request camera permission
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }

        architectView = (ArchitectView)findViewById(R.id.architectView); // I am the architect
        final StartupConfiguration config = new StartupConfiguration(getResources().getString(R.string.wikitude_key));
        architectView.onCreate(config);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();
        try {
            architectView.load("index.html");
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        architectView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        architectView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();
    }
}
