package ra.inge.ucr.ucraumentedreality.fragments;


import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.activities.HomeActivity;
import ra.inge.ucr.ucraumentedreality.adapters.ViewPagerAdapter;
import ra.inge.ucr.ucraumentedreality.fragments.CloseBuildingsFragment;
import ra.inge.ucr.ucraumentedreality.fragments.CloseMonumentsFragment;
import ra.inge.ucr.ucraumentedreality.fragments.LatestRecognitionFragment;
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
    private MapsFragment mapsFragment;

    public void setMapsFragment(MapsFragment mapsFragment) {
        this.mapsFragment = mapsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        setupViewPager();
        return root;
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
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        CloseBuildingsFragment closeBuildingsFragment = new CloseBuildingsFragment();
        CloseMonumentsFragment closeMonumentsFragment = new CloseMonumentsFragment();
        closeBuildingsFragment.setMapsFragment(mapsFragment);
        closeMonumentsFragment.setMapsFragment(mapsFragment);

        adapter.addFrag(closeBuildingsFragment, "Edificios Más Cercanos");
        adapter.addFrag(closeMonumentsFragment, "Monumentos Más Cercanos");
        adapter.addFrag(new LatestRecognitionFragment(), "Ultimos Reconocidos");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
