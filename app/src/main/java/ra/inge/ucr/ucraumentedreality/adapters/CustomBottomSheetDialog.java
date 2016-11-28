package ra.inge.ucr.ucraumentedreality.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ra.inge.ucr.ucraumentedreality.R;

/**
 * Custom Bottom Sheet Dialog for this purpose
 *
 * @author konrad
 * @version 1.0
 * @since 23/11/16
 */
public class CustomBottomSheetDialog extends BottomSheetDialogFragment {

//    public static CustomBottomSheetDialog getInstance() {
//        return new CustomBottomSheetDialog();
//    }

    /**
     * Listener for the button interaction
     */
    private OnButtonInteractionListener onButtonInteractionListener;

    /**
     * Interace to create callbacks for the activity
     */
    public interface OnButtonInteractionListener {
        void onRouseChosen(int choice);

//        void onVuforiaChosen();
    }

    public void setOnButtonInteractionListener(OnButtonInteractionListener onButtonInteractionListener) {
        this.onButtonInteractionListener = onButtonInteractionListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.custom_bottom_sheet, container, false);

        LinearLayout route1 = (LinearLayout) root.findViewById(R.id.route_1);
        route1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonInteractionListener != null) {
                    onButtonInteractionListener.onRouseChosen(0);
                }
            }
        });

        LinearLayout route2 = (LinearLayout) root.findViewById(R.id.route_2);
        route2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonInteractionListener != null) {
                    onButtonInteractionListener.onRouseChosen(1);
                }
            }
        });


        LinearLayout route3 = (LinearLayout) root.findViewById(R.id.route_3);
        route3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonInteractionListener != null) {
                    onButtonInteractionListener.onRouseChosen(2);
                }
            }
        });
        return root;
    }
}
