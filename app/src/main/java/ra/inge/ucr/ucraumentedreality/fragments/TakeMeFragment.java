package ra.inge.ucr.ucraumentedreality.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.location.LocationHelper;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.adapters.CustomAdapter;
import ra.inge.ucr.ucraumentedreality.adapters.TargetAdapter;

/**
 * Fragment that handles the action to take the user to a point
 *
 * @author Konrad
 * @version 1.0
 * @since 10/11/2016
 */
public class TakeMeFragment extends Fragment {

    /**
     * Custom adapter for the fragment
     */
    private TargetAdapter mAdapter;

    /**
     * Default constructor
     */
    public TakeMeFragment() {
        mAdapter = new TargetAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_takeme, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.target_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(mAdapter);
        mAdapter.setTargetObjects(Data.targetObjects);
        return root;
    }


}
