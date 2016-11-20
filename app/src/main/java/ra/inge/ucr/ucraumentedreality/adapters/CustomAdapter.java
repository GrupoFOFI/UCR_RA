package ra.inge.ucr.ucraumentedreality.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.ucraumentedreality.R;

/**
 * <h1> Custom Adapter </h1>
 * <p>
 * Adapter personalizado para agarrar los edificios más cercanos
 * </p>
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private TargetObject[] cercanos;

    /**
     * Constructor for the class that receives the current closes buildings as parameter
     * @param cercanos
     */
    public CustomAdapter(TargetObject[] cercanos) {
        this.cercanos = cercanos;
    }

    /**
     * On Create View Method
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    /**
     * On BindView Method
     * @param myViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(cercanos[i].getName());
    }

    /**
     * Methot that retrieves the amount of buildings
     * @return
     */
    @Override
    public int getItemCount() {
        return cercanos == null ? 0 : cercanos.length;
    }


    /**
     * Class used to handle the custom adapter elements
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listitem_name);
        }
    }

}
