package ra.inge.ucr.ucraumentedreality.activities;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
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
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.Vuforia.VideoPlayback.app.VideoPlayback.VideoPlayback;
import ra.inge.ucr.ucraumentedreality.fragments.HomeFragment;
import ra.inge.ucr.ucraumentedreality.fragments.TakeMeFragment;
import ra.inge.ucr.ucraumentedreality.utils.CommandHandler;
import ra.inge.ucr.ucraumentedreality.utils.ShakeHandler;
import ra.inge.ucr.ucraumentedreality.utils.Utils;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ShakeHandler.OnShakeListener, SearchView.OnQueryTextListener {


    private OnSearchInteractionListener onSearchInteractionListener;

    public interface OnSearchInteractionListener {
        void onSearchStarted(String searchPattern);

        void onVoiceSearchStarted(String searchPattern);
    }

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
    private TakeMeFragment takeMeFragment;
    private LocationHelper locationHelper;
    NavigationView navigationView;

    private TextView userNameTextView, userMailTextView;

    FragmentManager fragmentManager;
    View headerLayout;

    public void setOnSearchInteractionListener(OnSearchInteractionListener onSearchInteractionListener) {
        this.onSearchInteractionListener = onSearchInteractionListener;
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        drawerTitles = getResources().getStringArray(R.array.drawer_titles);
        locationHelper = new LocationHelper();
        homeFragment = new HomeFragment();
        setFragment(new HomeFragment());

        currentFragmentType = homeFragment.getClass();

        takeMeFragment = new TakeMeFragment();
        takeMeFragment.setHomeActivity(this);

        shakeHandler = new ShakeHandler(this);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        shakeHandler.setOnShakeListener(this);

        utils = new Utils(this, getApplicationContext());
        commandHandler = new CommandHandler(getApplicationContext());
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        headerLayout = navigationView.getHeaderView(0);
        userNameTextView = (TextView) headerLayout.findViewById(R.id.userNameText);
        userNameTextView.setText(prefs.getString("name", "Fofi"));

        userMailTextView = (TextView) headerLayout.findViewById(R.id.userMailText);
        userMailTextView.setText(prefs.getString("mail", "fofijones@gmail.com"));

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

            if (showingPopUp == false) {
                promptSpeechInput();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        userNameTextView.setText(prefs.getString("name", "Fofi"));
        userMailTextView.setText(prefs.getString("mail", "fofijones@gmail.com"));

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
                    if (currentFragmentType != TakeMeFragment.class) {
                        setFragment(takeMeFragment);
                    }
                    onSearchInteractionListener.onVoiceSearchStarted(text2Speech);
                    Toast.makeText(this, "Buscando : " + text2Speech, Toast.LENGTH_SHORT).show();
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

            case R.id.nav_takeme:
                setFragment(takeMeFragment);
                break;

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

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment_container, fragment);
//        if (currentFragmentType == HomeFragment.class) {
//            fragmentTransaction.addToBackStack("HomeFragment");
//
//        }
        currentFragmentType = fragment.getClass();
        fragmentTransaction.commit();
    }
//
    // TODO si da tiempo un search en el homescreen del app que sustituya el searchview del takemefragment


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, HomeActivity.class)));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            onSearchInteractionListener.onVoiceSearchStarted(query);
            Toast.makeText(this, "Buscando : " + query, Toast.LENGTH_SHORT).show();

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String uri = intent.getDataString();
            Toast.makeText(this, "Sugerencia: " + uri, Toast.LENGTH_SHORT).show();
            onSearchInteractionListener.onVoiceSearchStarted(uri);

        }
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("konri", "Search input is " + newText);
        if (currentFragmentType != TakeMeFragment.class) {
            setFragment(takeMeFragment);
        }
        onSearchInteractionListener.onSearchStarted(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
