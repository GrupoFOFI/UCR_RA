package ra.inge.ucr.ucraumentedreality.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.Vuforia.VideoPlayback.app.VideoPlayback.VideoPlayback;
import ra.inge.ucr.ucraumentedreality.adapters.HomeFragment;
import ra.inge.ucr.ucraumentedreality.fragments.MapsFragment;
import ra.inge.ucr.ucraumentedreality.utils.ShakeHandler;
import ra.inge.ucr.ucraumentedreality.utils.Utils;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ShakeHandler.OnShakeListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar();
        setupDrawer();
        drawerTitles = getResources().getStringArray(R.array.drawer_titles);
        setFragment(getResources().getString(R.string.app_name), new HomeFragment());

        shakeHandler = new ShakeHandler(this);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        shakeHandler.setOnShakeListener(this);

        utils = new Utils(this, getApplicationContext());

    }

    @Override
    public void onShake() {
        vibe.vibrate(100);
//        new AlertDialog.Builder(this)
//                .setPositiveButton(android.R.string.ok, null)
//                .setMessage("Shooken!")
//                .show();
        if (showingPopUp == false) {
            promptSpeechInput();
        }

    }


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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text2Speech = result.get(0);
                    utils.toastLog(text2Speech);
                }
                break;
            }
        }
        showingPopUp = false;
    }


    /**
     * Method that sets up the toolbar
     */
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("UCR Realidad Aumentada");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }


    /**
     * Method that sets up the navigation drawer feature
     */
    private void setupDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     *
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
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    /**
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        boolean isFragment = true;
        switch (item.getItemId()) {

            case R.id.nav_home:
                fragment = new HomeFragment();

                break;

            case R.id.nav_camera:
//                fragment = new VuforiaFragment();
                isFragment = false;
                startActivity(new Intent(getApplicationContext(), VideoPlayback.class));
                break;

            case R.id.nav_maps:
                fragment = new MapsFragment();
                break;

//            case R.id.nav_takeme:
//                fragment = new MapsFragment();
//                break;

            case R.id.nav_accessibility:
                isFragment = false;
                accessibilityDialog();
                break;

            case R.id.nav_share:
                isFragment = false;
                shareApp();
                break;

            case R.id.nav_settings:
                isFragment = false;
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;

            default:
                isFragment = false;
                break;
        }

        if (isFragment) {
            Log.i("Tag", "is fragment");
            setFragment("Reste", fragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Method that creates the android's native share function
     */
    void shareApp() {

    }

    void accessibilityDialog() {
        showDialog("Aviso", "Se cambiará la modalidad a accesibilidad");

    }

    /**
     * Generic method to show errors in case the user enters invalid parameters
     *
     * @param title
     * @param message
     */
    private void showDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    /**
     * Method used to setup the fragment memory
     *
     * @param title
     * @param fragment
     */
    private void setFragment(String title, Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.home_fragment_container, fragment);
        if (currentFragmentType == HomeFragment.class)
            fragmentTransaction.addToBackStack("HomeFragment");
        currentFragmentType = fragment.getClass();
        fragmentTransaction.commit();
    }


}
