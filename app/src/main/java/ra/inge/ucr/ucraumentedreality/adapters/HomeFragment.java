package ra.inge.ucr.ucraumentedreality.adapters;


import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.Vuforia.VideoPlayback.app.VideoPlayback.VideoPlayback;
import ra.inge.ucr.ucraumentedreality.fragments.CloseBuildingsFragment;
import ra.inge.ucr.ucraumentedreality.fragments.CloseMonumentsFragment;
import ra.inge.ucr.ucraumentedreality.fragments.MapsFragment;
import ra.inge.ucr.ucraumentedreality.utils.ShakeHandler;

/**
 * Class that handles the main commands or actions that can be used by the user
 *
 * @author Konrad
 * @version 1.0
 * @since 10/11/2016
 */
public class HomeFragment extends Fragment {

    /**
     * Default constructor
     */
    public HomeFragment() {
    }

    private ShakeHandler shakeHandler;
    private Vibrator vibe;
    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root  = inflater.inflate(R.layout.fragment_home, container, false);
        setupViewPager();
        return  root;
    }

    /**
     * Method that sets up the view pager
     */
    private void setupViewPager() {
        final ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    /**
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        MapsFragment mapsFragment = new MapsFragment();
        adapter.addFrag(new CloseBuildingsFragment(mapsFragment), "Edificios Más Cercanos");
        adapter.addFrag(new CloseMonumentsFragment(mapsFragment), "Monumentos Más Cercanos");

        viewPager.setAdapter(adapter);
    }




}
