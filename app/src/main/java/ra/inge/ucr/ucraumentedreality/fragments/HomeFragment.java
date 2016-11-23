package ra.inge.ucr.ucraumentedreality.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.List;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.location.NavigationHelper;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.activities.MapsActivity;
import ra.inge.ucr.ucraumentedreality.adapters.ViewPagerAdapter;
import ra.inge.ucr.ucraumentedreality.utils.Utils;

/**
 * Class that handles the main commands or actions that can be used by the user
 *
 * @author Konrad
 * @version 1.0
 * @since 10/11/2016
 */
public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    /**
     * Default constructor
     */
    public HomeFragment() {

    }

    private View root;

    int[] sampleImages = {R.drawable.pretil, R.drawable.logo_ucr};
    String[] sampleTitles = {"Pretil", "UCR"};

    private CarouselView customCarouselView;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private LocationRequest mLocationRequest;
    private static final long POLLING_FREQ = 200;
    private static final long FASTEST_UPDATE_FREQ = 1000;
    private NavigationHelper navigationHelper;
    private LocationHelper locationHelper;

    private Utils utils;

    CloseBuildingsFragment closeBuildingsFragment;
    CloseMonumentsFragment closeMonumentsFragment;

    /**
     * Close monuments
     */
    private TargetObject[] closeMonuments;

    /**
     * The closest buildings;
     */
    private TargetObject[] closeBuildings;

    /**
     * The carousel imageview reference
     */
    private ImageView carouselImageView;

    /**
     *
     */
    private TextView carouselTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        closeBuildingsFragment = new CloseBuildingsFragment();
        closeMonumentsFragment = new CloseMonumentsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        buildGoogleApiClient();
        utils = new Utils(getActivity(), getContext());
        root = inflater.inflate(R.layout.fragment_home, container, false);

        locationHelper = new LocationHelper();


        customCarouselView = (CarouselView) root.findViewById(R.id.customCarouselView);
        customCarouselView.setPageCount(sampleImages.length);
        customCarouselView.setSlideInterval(4000);
        customCarouselView.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(int position) {

                View customView = inflater.inflate(R.layout.carousel_custom, customCarouselView, false);
                carouselTextView = (TextView) customView.findViewById(R.id.carouselTargetName);
                carouselImageView = (ImageView) customView.findViewById(R.id.carouselImage);

                carouselImageView.setImageResource(sampleImages[position]);
                carouselImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO agarrar el target object

                        Log.d("konri", "Cant touch this");
//
//                        TargetObject targetFound = null;
//                        Intent intent = new Intent(getContext(), MapsActivity.class);
//                        intent.putExtra("TargetName", targetFound.getName());
//                        startActivity(intent);
                    }
                });
                carouselTextView.setText(sampleTitles[position]);
                customCarouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

                return customView;
            }
        });

        customCarouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);


        setupViewPager();
        return root;
    }


    /**
     * Method that sets up the view pager
     */
    private void setupViewPager() {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        List<Fragment> fragments = getFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment frag : fragments) {
                if (frag instanceof CloseBuildingsFragment || frag instanceof CloseMonumentsFragment || frag instanceof LatestRecognitionFragment) {
                    transaction.remove(frag);
                }
            }
        }
        transaction.commit();
        final ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    /**
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        closeBuildingsFragment.setLocationHelper(locationHelper);
        closeMonumentsFragment.setLocationHelper(locationHelper);

        adapter.addFrag(closeBuildingsFragment, "Edificios Más Cercanos");
        adapter.addFrag(closeMonumentsFragment, "Monumentos Más Cercanos");
        adapter.addFrag(new LatestRecognitionFragment(), "Ultimos Reconocidos");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("konri", "Me vine al on resume");
        if (root != null) {
            Log.d("konri", "Root no es null");
        }

    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("konri", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        Log.i("konri", "Connection suspended");
        mGoogleApiClient.connect();
    }


    /**
     * Method that updates the custom markers when the location changes
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location latestLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            latestLocation = location;

            if (locationHelper != null) {
                locationHelper.calculateClosest(location);

                closeMonuments = locationHelper.getCloseMonuments();
                closeBuildings = locationHelper.getCloseBuildings();
                updateFragments();

                Log.d("konri", "Location helper is not null");
            } else {
                Log.d("konri", "Location helper is null");
            }
            Log.d("konri", "" + latestLocation.getLatitude());
            Log.d("konri", "" + latestLocation.getLongitude());
        }
    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        utils.requestLocationPermission();
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            String error = getResources().getString(R.string.no_location_detected);
            Log.e("ERROR", error);
        } else {
            locationHelper.updateLastLocation(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            Log.d("konri2", "" + mLastLocation.getLatitude());
            Log.d("konri2", "" + mLastLocation.getLongitude());
        }
    }


    /**
     * On Start Class method
     */
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    /**
     * On Stop Class method
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    void updateFragments() {
        if (getCloseBuildings() != null) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("Konri", "Updating buildings 1");
                        if (closeBuildingsFragment.getmAdapter() != null) {
                            Log.d("konri", "Updating buildings 2");
                            closeBuildings = locationHelper.getCloseBuildings();
                            Log.d("konri", "Updating buildings 3");
                            closeBuildingsFragment.getmAdapter().setCloseTargets(closeBuildings);
                            closeBuildingsFragment.getmAdapter().notifyDataSetChanged();
                        }
                    }
                });
            }
        }

        if (getCloseMonuments() != null) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("konri", "Updating monuments?1");
                        if (closeMonumentsFragment.getmAdapter() != null) {

                            Log.d("konri", "Updating monuments?2");
                            closeMonumentsFragment.getmAdapter().setCloseTargets(closeMonuments);
                            closeMonumentsFragment.getmAdapter().notifyDataSetChanged();
                        }
                    }
                });
            }
        }
    }

    public TargetObject[] getCloseMonuments() {
        return closeMonuments;
    }

    public TargetObject[] getCloseBuildings() {
        return closeBuildings;
    }
}
