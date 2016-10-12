package ra.inge.ucr.ucraumentedreality.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.adapters.CustomAdapter;


/**
 * Class to manage the closest buildings
 */
public class CloseBuildingsFragment extends Fragment {

        private CustomAdapter mAdapter;

        private String mItemData = "Odontología Educación Letras Informática";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_close_buildings, container, false);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(
                    R.id.fragment_list_rv);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);

            String[] listItems = mItemData.split(" ");

            List<String> list = new ArrayList<String>();
            Collections.addAll(list, listItems);

            mAdapter = new CustomAdapter(list);
            recyclerView.setAdapter(mAdapter);

            return view;
        }
    }