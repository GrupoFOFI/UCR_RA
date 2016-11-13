package ra.inge.ucr.ucraumentedreality.adapters;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ra.inge.ucr.ucraumentedreality.R;

/**
 * Fragment that handles the action to take the user to a point
 *
 * @author Konrad
 * @version 1.0
 * @since 10/11/2016
 */
public class TakeMeFragment extends Fragment {

    /**
     * Default constructor
     */
    public TakeMeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_takeme, container);
        return root;
    }



}
