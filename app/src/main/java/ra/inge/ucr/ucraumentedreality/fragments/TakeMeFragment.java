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

import java.util.Locale;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.activities.HomeActivity;
import ra.inge.ucr.ucraumentedreality.adapters.TargetAdapter;

/**
 * Fragment that handles the action to take the user to a point
 *
 * @author Konrad
 * @version 1.0
 * @since 10/11/2016
 */
public class TakeMeFragment extends Fragment implements SearchView.OnQueryTextListener {

    /**
     * Custom adapter for the fragment
     */
    private TargetAdapter mAdapter;

    /**
     * Default constructor
     */
    public TakeMeFragment() {
//        mAdapter = new TargetAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_takeme, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.target_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        SearchView searchView = (SearchView) root.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        mAdapter = new TargetAdapter();
        recyclerView.setAdapter(mAdapter);
        setHasOptionsMenu(true);

        mAdapter.setTargetObjects(Data.targetObjects);
        mAdapter.setHomeActivity((HomeActivity)getActivity());
        return root;
    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        inflater.inflate(R.menu.menu_search, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(this);
//
//    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("konri", "Filered " + newText);
        mAdapter.getFilter().filter(newText.toString());
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


}
