package cl.idatum.overwatchapi;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Jesus Cañizales <jcanizales@siigsa.cl>
 * @version 2
 * @package cl.idatum.mapsgoogleapp Proyecto MapsGoogleApp
 * @link http://www.siigsa.cl
 * @copyright SIIGSA, Propiedad Intelectual y Derechos Patrimoniales de Software y Base de Datos i-datum. Registro Propiedad Intelectual Nº 211.351 y 211.352 respectivamente, con fecha 22 de noviembre del 2011
 * @since 18/1/2018
 */
public class RestAdapter extends RecyclerView.Adapter<RestAdapter.MyViewHolder> {

    private ArrayList<OverwatchHeros> list;
    private String TAG = "RestAdapter.class";
    private OnRecyclerViewItemClickListener listener;

    public RestAdapter(ArrayList<OverwatchHeros> list) {
        this.list = list;
    }

    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClicked(String name, String description, String health, String armour, String shield, String age, String affiliation);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final OverwatchHeros overwatchHero = list.get(position);
        holder.textView.setText(overwatchHero.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerViewItemClicked(overwatchHero.getName(),
                        overwatchHero.getDescription(),
                        overwatchHero.getHealth(),
                        overwatchHero.getArmour(),
                        overwatchHero.getShield(),
                        overwatchHero.getAge(),
                        overwatchHero.getAffiliation());
            }
        });
        Log.d(TAG, "onBindViewHolder: " + overwatchHero.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
