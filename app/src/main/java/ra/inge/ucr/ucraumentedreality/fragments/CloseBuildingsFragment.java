package ra.inge.ucr.ucraumentedreality.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.location.listener.OnBuildingsSetListener;
import ra.inge.ucr.location.listener.OnLocationSetListener;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.adapters.CustomAdapter;


/**
 * <h1> CloseBuildingsFragment </h1>
 * <p>
 * Fragment to manage the nearest buildings to show the user
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class CloseBuildingsFragment extends Fragment implements OnBuildingsSetListener {

    /**
     * Custom adapter for the fragment
     */
    private CustomAdapter mAdapter;
    /**
     * Maps fragment listener
     */
    private LocationHelper locationHelper;

    /**
     * The closest buildings;
     */
    private TargetObject[] closeBuildings;

    /**
     * The location listener
     */
    private OnBuildingsSetListener onBuildingsSetListener;


    /**
     * Empty constructor for the fragment
     */
    public CloseBuildingsFragment() {
    }


    /**
     * Setter for the location helper
     *
     * @param locationHelper
     */
    public void setLocationHelper(LocationHelper locationHelper) {
        this.locationHelper = locationHelper;
        locationHelper.setOnBuildingsSetListener(this);
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

        View view = inflater.inflate(R.layout.fragment_close_buildings, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(
                R.id.fragment_list_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new CustomAdapter();
        recyclerView.setAdapter(mAdapter);

        if (locationHelper != null) {
            closeBuildings = locationHelper.getClosestBuildings(MapsFragment.CLOSEST_AMOUNT);
            if (closeBuildings != null) {
                mAdapter.setCercanos(closeBuildings);
            }
        }
        return view;
    }

    @Override
    public void onBuildingsCalculated() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mAdapter != null) {
                        closeBuildings = locationHelper.getCloseBuildings();
                        mAdapter.setCercanos(closeBuildings);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
