package ra.inge.ucr.ucraumentedreality.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.adapters.CustomAdapter;
import ra.inge.ucr.ucraumentedreality.adapters.TargetAdapter;

/**
 * Fragment used to present the result of the latest targets that the user has recognized
 *
 * @author konrad
 * @version 1.0
 * @since 18/11/2016
 */
public class LatestRecognitionFragment extends Fragment {


    /**
     * Custom adapter for the fragment
     */
    private TargetAdapter mAdapter;
    /**
     * Maps fragment listener
     */
    private LocationHelper locationHelper;

    /**
     * The closest buildings;
     */
    public static ArrayList<TargetObject> targetObjects;

    public LatestRecognitionFragment() {
        targetObjects = new ArrayList<TargetObject>();
    }

    /**
     * On create view method for the fragment to inflate its respective layout
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_latest_recognitions, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new TargetAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setTargetObjects(targetObjects);

        return view;

    }


}
