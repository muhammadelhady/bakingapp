package com.example.muham.bakingapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muham.bakingapp.R;
import com.example.muham.bakingapp.RecipeObject.Recipe;
import com.example.muham.bakingapp.RecipeObject.Step;

import java.util.ArrayList;

public class RecipesStepsListGreenAdapter extends RecyclerView.Adapter<RecipesStepsListGreenAdapter.StepsViewHolder> {

    ArrayList<Step> steps=new ArrayList<>();
    final private ListItemClickListener listItemClickListener;

    public RecipesStepsListGreenAdapter(ArrayList<Step> steps,ListItemClickListener listItemClickListener)
    {
        this.steps=steps;
        this.listItemClickListener=listItemClickListener;
    }

    public interface ListItemClickListener
    {
        void onListItemClicked(int clickedItemIndex);
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.stepcard,parent,false);
        RecipesStepsListGreenAdapter.StepsViewHolder stepsViewHolder = new StepsViewHolder(view);
        return stepsViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        String shortDescription = steps.get(position).getShortDescription();
        holder.Bind(shortDescription);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView stepShortDescribtionTextView;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepShortDescribtionTextView=(TextView)itemView.findViewById(R.id.StepShortDescribtionTextView);

            itemView.setOnClickListener(this);
        }

        void Bind(String shortDescriotion)
        {

            stepShortDescribtionTextView.setText(shortDescriotion);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClicked(clickedPosition);
        }
    }
}
