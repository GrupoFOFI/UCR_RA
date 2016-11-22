package ra.inge.ucr.ucraumentedreality.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.Vuforia.VideoPlayback.app.VideoPlayback.VideoPlayback;
import ra.inge.ucr.ucraumentedreality.fragments.HomeFragment;
import ra.inge.ucr.ucraumentedreality.utils.CommandHandler;
import ra.inge.ucr.ucraumentedreality.utils.ShakeHandler;
import ra.inge.ucr.ucraumentedreality.utils.Utils;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ShakeHandler.OnShakeListener {

    /* UI elements */
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Class currentFragmentType;
    private String[] drawerTitles;

    private ShakeHandler shakeHandler;
    private Vibrator vibe;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Utils utils;

    private boolean showingPopUp = false;
    private String text2Speech = "";

    private CommandHandler commandHandler;
    private FloatingActionButton fab;
    private SharedPreferences prefs;

    private HomeFragment homeFragment;
    private LocationHelper locationHelper;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("UCR Realidad Aumentada");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerTitles = getResources().getStringArray(R.array.drawer_titles);
        locationHelper = new LocationHelper();
        homeFragment = new HomeFragment();
        setFragment(new HomeFragment());

        shakeHandler = new ShakeHandler(this);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        shakeHandler.setOnShakeListener(this);

        utils = new Utils(this, getApplicationContext());
        commandHandler = new CommandHandler(getApplicationContext());
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVuforia();
            }
        });
    }

    /**
     * Override method to handle the shake listener gestures
     */
    @Override
    public void onShake() {
        if (prefs.getBoolean("accessibility", false) == true) {

            //vibe.vibrate(100);
            if (showingPopUp == false) {
                promptSpeechInput();
            }
        }
    }


    /**
     * Method that prompts the user to speak
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            showingPopUp = true;
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method that
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text2Speech = result.get(0);
                    Log.i("totoo", "Le entendí: " + text2Speech);
                    commandHandler.translate(text2Speech);

                }
                break;
            }
        }
        showingPopUp = false;
    }

    /**
     * On back pressed method
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Opens the Vuforia Activity to use Videos
     */
    void openVuforia() {
        startActivity(new Intent(getApplicationContext(), VideoPlayback.class));
    }

    /**
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                if (!(currentFragmentType == HomeFragment.class)) {
                    setFragment(new HomeFragment());
                }

                break;

            case R.id.nav_camera:
                openVuforia();
                break;

            case R.id.nav_maps:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                break;

//            case R.id.nav_takeme:
//                fragment = new MapsFragment();
//                break;

            case R.id.nav_accessibility:
                accessibilityDialog();
                break;


            case R.id.nav_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Method that enables the user to change the mode of the app
     */
    void accessibilityDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if (prefs.getBoolean("accessibility", false) == true) {
            alertDialogBuilder.setTitle("Aviso");
            alertDialogBuilder
                    .setMessage("Se cambiará la modalidad a normal")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            prefs.edit().putBoolean("accessibility", false).commit();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

        } else {
            alertDialogBuilder.setTitle("Aviso");
            alertDialogBuilder
                    .setMessage("Se cambiará la modalidad a accesibilidad")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            prefs.edit().putBoolean("accessibility", true).commit();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    /**
     * Method used to setup the fragment memory
     *
     * @param fragment
     */
    private void setFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment_container, fragment);
        if (currentFragmentType == HomeFragment.class) {
            fragmentTransaction.addToBackStack("HomeFragment");

        }
        currentFragmentType = fragment.getClass();
        fragmentTransaction.commit();
    }


}
