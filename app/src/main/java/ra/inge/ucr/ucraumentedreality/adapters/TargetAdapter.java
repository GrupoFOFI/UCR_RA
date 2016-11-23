package ra.inge.ucr.ucraumentedreality.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.activities.SingleTargetObjectActivity;
import ra.inge.ucr.ucraumentedreality.activities.HomeActivity;
import ra.inge.ucr.ucraumentedreality.activities.MapsActivity;

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
public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.CustomViewHolder> implements Filterable {

    /**
     * The closest target objects
     */
    public ArrayList<TargetObject> targetObjects;// = new ArrayList<TargetObject>


    private HomeActivity homeActivity;


    /**
     * The closest target objects
     */
    public ArrayList<TargetObject> filteredTargetObjects;// = new ArrayList<TargetObject>

    /**
     * The filter object
     */
    private Filter mFilter;

    /**
     * Empty Constructor
     */
    public TargetAdapter() {
    }


    public void setTargetObjects(ArrayList<TargetObject> targetObjects) {
        this.targetObjects = targetObjects;
        this.filteredTargetObjects = targetObjects;
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
    public void onBindViewHolder(CustomViewHolder myViewHolder, final int i) {
        myViewHolder.title.setText(filteredTargetObjects.get(i).getName());
        myViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TargetObject targetObject = filteredTargetObjects.get(i);
                Context context = v.getContext();

                Intent intent = new Intent(context, SingleTargetObjectActivity.class);
                // La idea de agarrar por id es no tener un loop luego con el nombre.
                intent.putExtra("objectId", targetObject.getId());
                context.startActivity(intent);
            }
        });

    }

    /**
     * Methot that retrieves the amount of buildings
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return filteredTargetObjects.size();
    }

    /**
     * Class used to handle the custom adapter elements
     */
    class CustomViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView title;
        View mView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            this.itemView = itemView;
            title = (TextView) itemView.findViewById(R.id.listitem_name);


        }
    }


    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    filteredTargetObjects = (ArrayList<TargetObject>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<TargetObject> FilteredArrList = new ArrayList<TargetObject>();

                    if (targetObjects == null) {
                        targetObjects = new ArrayList<TargetObject>(filteredTargetObjects); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = targetObjects.size();
                        results.values = targetObjects;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < filteredTargetObjects.size(); i++) {
                            TargetObject targetObject = filteredTargetObjects.get(i);
                            if (targetObject.getName().toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(targetObject);
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return mFilter;
        } else {
            return mFilter;
        }
    }

}

