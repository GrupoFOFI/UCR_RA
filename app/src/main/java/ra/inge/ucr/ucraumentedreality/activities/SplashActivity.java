package ra.inge.ucr.ucraumentedreality.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * <h1> Splash Activity </h1>
 * <p>
 * Class that initializes the app showing a custom image to the user
 * </p>
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Method that prepares all the components displayed this activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}