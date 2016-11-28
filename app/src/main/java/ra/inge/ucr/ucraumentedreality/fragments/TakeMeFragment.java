package ra.inge.ucr.ucraumentedreality.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Locale;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.activities.HomeActivity;
import ra.inge.ucr.ucraumentedreality.adapters.TargetAdapter;
import ra.inge.ucr.ucraumentedreality.utils.ShakeHandler;

/**
 * Fragment that handles the action to take the user to a point
 *
 * @author Konrad
 * @version 1.0
 * @since 10/11/2016
 */
public class TakeMeFragment extends Fragment implements HomeActivity.OnSearchInteractionListener {


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

    HomeActivity homeActivity;

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        homeActivity.setOnSearchInteractionListener(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Llévame");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_takeme, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.target_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        if (mAdapter == null) {
            mAdapter = new TargetAdapter();
        }
        recyclerView.setAdapter(mAdapter);
        setHasOptionsMenu(true);

        ArrayList<TargetObject> objects = new ArrayList<TargetObject>();
        for(int i=0;i<Data.targetObjects.size();i++){
            if(Data.targetObjects.get(i).getEntrances() != null){
                objects.add(Data.targetObjects.get(i));
            }
        }
        mAdapter.setTargetObjects(objects);
        mAdapter.setHomeActivity((HomeActivity) getActivity());
        return root;
    }


    public TargetAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    public void onSearchStarted(String searchPattern) {
        Log.d("konri", "Normal search");
        mAdapter.getFilter().filter(searchPattern.toString());
    }


    // TODO if accesibility mode entonces entre de una
    @Override
    public void onVoiceSearchStarted(String searchPattern) {
        Log.d("konri", "Voice searching");
        mAdapter.setTargetObjects(Data.targetObjects);
        mAdapter.getFilter().filter(searchPattern.toString());

    }
}
