package ra.inge.ucr.ucraumentedreality.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1> ViewPager Adapter </h1>
 * <p>
 * Adapter for the fragments on the Application
 * </p>
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    /**
     * Base constructor for fragments
     *
     * @param manager
     */
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    /**
     * Method used to retrieve the current fragment at the position sent as parameter
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Amount of fragments at the ViewPager
     *
     * @return
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * Method used to add a fragment to the ViewPager
     *
     * @param fragment
     * @param title
     */
    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    /**
     * Method used to retrieve a Fragment's title
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}