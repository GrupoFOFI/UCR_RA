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
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.adapters.CustomAdapter;

/**
 * Fragment used to present the result of the latest targets that the user has recognized
 *
 * @author konrad
 * @version 1.0
 * @since 18/11/2016
 */
public class LatestRecognitionFragment extends Fragment {


    public static ArrayList<TargetObject> targetObjects;

    public LatestRecognitionFragment() {
        targetObjects = new ArrayList<TargetObject>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_latest_recognitions, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        return view;

    }


}
