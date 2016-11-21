package ra.inge.ucr.ucraumentedreality.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.adapters.CustomAdapter;


/**
 * <h1> CloseMonumentsFragment </h1>
 * <p>
 * Fragment to manage the nearest buildings to show the user
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class CloseMonumentsFragment extends Fragment {

    /**
     * Custom adapter made for the fragment
     */
    private CustomAdapter mAdapter;
    /**
     * Maps fragment reference
     */
    private MapsFragment mapsFragment;
    /**
     * Close monuments
     */
    private TargetObject[] closeMonuments;

    /**
     * Empty constructor for the fragment
     */
    public CloseMonumentsFragment() {
    }

    /**
     * Setter for the maps fragment
     *
     * @param mapsFragment
     */
    public void setMapsFragment(MapsFragment mapsFragment) {
        this.mapsFragment = mapsFragment;
    }

    /**
     * Method that prepares all the components for the fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_close_monuments, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(
                R.id.fragment_list_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        if (mapsFragment == null) {
            mapsFragment = new MapsFragment();
        }
        closeMonuments = mapsFragment.getCloseMonuments();
        if (closeMonuments != null) {
            mAdapter = new CustomAdapter(closeMonuments);
            recyclerView.setAdapter(mAdapter);
        }


        return view;
    }
}