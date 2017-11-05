package com.bruce.kidzymer.kidpoems;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bruce on 18/02/2017.
 */

public class PoemsAdapter extends RecyclerView.Adapter<PoemsAdapter.PoemsViewHolder> {

    //arrylist of peoms
    ArrayList<Poem> poemsArrayList;
    Context context;
    private String language;

    public PoemsAdapter(Context context, ArrayList<Poem> poemsArrayList, String language) {
        //setting context
        this.context = context;
        //setting poems arraylist
        this.poemsArrayList = poemsArrayList;
        //langue is used to know which langauge user selected
        this.language = language;
    }

    @Override
    public PoemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_layout, parent, false);

        //creating view holder object
        PoemsViewHolder poemsViewHolder = new PoemsViewHolder(view);

        return poemsViewHolder;
    }

    @Override
    public void onBindViewHolder(PoemsViewHolder holder, int position) {
        Poem poem = poemsArrayList.get(position);
        holder.poem_name.setText(poem.getPoem());
    }

    @Override
    public int getItemCount() {
        return poemsArrayList.size();
    }

    public class PoemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView poem_name;

        public PoemsViewHolder(View view) {

            super(view);
            view.setOnClickListener(this);
            poem_name = (TextView) view.findViewById(R.id.Tx_Poem_name);

        }

        @Override
        public void onClick(View v) {



            Intent intent = new Intent(context,Player_pageActivity.class);
            intent.putExtra("language",language);
            //position is used to know on which song he clicked
            intent.putExtra("position",getAdapterPosition());

            context.startActivity(intent);

        }
    }
}
