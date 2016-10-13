package ra.inge.ucr.ucraumentedreality.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import ra.inge.ucr.da.Edificio;
import ra.inge.ucr.ucraumentedreality.R;

/**
 * Created by Konrad on 10/12/16.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Edificio [] cercanos;

    public CustomAdapter(Edificio[] cercanos) {
        this.cercanos = cercanos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(cercanos[i].getNmbr());
    }

    @Override
    public int getItemCount() {
        return cercanos == null ? 0 : cercanos.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listitem_name);
        }
    }

}
