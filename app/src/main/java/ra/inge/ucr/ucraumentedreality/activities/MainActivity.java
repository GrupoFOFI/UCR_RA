package ra.inge.ucr.ucraumentedreality.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.adapters.ViewPagerAdapter;
import ra.inge.ucr.ucraumentedreality.fragments.CloseBuildingsFragment;
import ra.inge.ucr.ucraumentedreality.fragments.MapsFragment;
import ra.inge.ucr.ucraumentedreality.fragments.WikitudeFragment;
import ra.inge.ucr.ucraumentedreality.utils.Utils;


/**
 * <h1> Main Activity </h1>
 * <p>
 * <p>
 * Main Class for the Application
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utils = new Utils(this, getApplicationContext());
        setupToolbar();
        setupViewPager();
        setupCollapsingToolbar();
    //    setupDrawer();
        setupStatusBar();
        utils.requestLocationPermission();

    }

    private void setupDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);
    }


    private void setupViewPager() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        MapsFragment mapsFragment = new MapsFragment();
         adapter.addFrag(mapsFragment, "Mapas");
        adapter.addFrag(new WikitudeFragment(), "Wikitude");
        adapter.addFrag(new CloseBuildingsFragment(mapsFragment), "Edificios Más Cercanos");

        viewPager.setAdapter(adapter);
    }

    private  void setupStatusBar () {

        Window window = this.getWindow();
       window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
    }


    @Override

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     //   getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {

            case R.id.nav_camera:
                intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_gallery:
                intent = new Intent(this, WikitudeActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_slideshow:
                intent = new Intent(this, PoisActivity.class);
                startActivity(intent);

            case R.id.nav_manage:
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_send:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
