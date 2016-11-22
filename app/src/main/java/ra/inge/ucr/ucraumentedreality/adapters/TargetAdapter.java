package ra.inge.ucr.ucraumentedreality.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.ucraumentedreality.R;

/**
 * Created by Konrad on 11/22/16.
 */

/**
 * <h1> Custom Adapter </h1>
 * <p>
 * Adapter personalizado para agarrar los edificios más closeTargets
 * </p>
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.CustomViewHolder> {

    /**
     * The closest target objects
     */
    public ArrayList<TargetObject> targetObjects;// = new ArrayList<TargetObject>

    /**
     * Constructor for the class that receives the current closes buildings as parameter
     *
     * @param targetObjects
     */
    public TargetAdapter(ArrayList<TargetObject> targetObjects) {
        this.targetObjects = targetObjects;
    }

    /**
     * Empty Constructor
     */
    public TargetAdapter() {
    }

    public void setTargetObjects(ArrayList<TargetObject> targetObjects) {
        this.targetObjects = targetObjects;
    }


    /**
     * On Create View Method
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list_item, viewGroup, false);
        return new CustomViewHolder(view);
    }


    /**
     * On BindView Method
     *
     * @param myViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(CustomViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(targetObjects.get(i).getName());
    }

    /**
     * Methot that retrieves the amount of buildings
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return targetObjects == null ? 0 : targetObjects.size();
    }


    /**
     * Class used to handle the custom adapter elements
     */
    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public CustomViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listitem_name);
        }
    }

}

