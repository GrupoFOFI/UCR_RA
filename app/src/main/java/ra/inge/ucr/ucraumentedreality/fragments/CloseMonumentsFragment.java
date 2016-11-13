package ra.inge.ucr.ucraumentedreality.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.adapters.CustomAdapter;


public class CloseMonumentsFragment extends Fragment {

    private CustomAdapter mAdapter;
    private MapsFragment mapsFragment;

    /**
     * Empty constructor for the fragment
     */
    public CloseMonumentsFragment() {
    }

    /**
     * Constructor that receives the mapsFragmentReference that handles the closest buildings
     *
     * @param mapsFragment
     */
    @SuppressLint("ValidFragment")
    public CloseMonumentsFragment(MapsFragment mapsFragment) {
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

        View view = inflater.inflate(R.layout.fragment_close_buildings, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(
                R.id.fragment_list_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        if (mapsFragment.getCercanos() != null) {
            mAdapter = new CustomAdapter(mapsFragment.getCercanos());
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }
}