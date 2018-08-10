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

import java.util.ArrayList;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.RecipesViewHolder> {

    ArrayList<Recipe> recipes=new ArrayList<>();

    final private ListItemClickListener listItemClickListener;

public GreenAdapter(ArrayList<Recipe> recipes,ListItemClickListener listItemClickListener)
{
    this.recipes=recipes;
    this.listItemClickListener=listItemClickListener;
}

 public interface ListItemClickListener
    {
void onListItemClicked(int clickedItemIndex);
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipecard,parent,false);
        RecipesViewHolder recipesViewHolder = new RecipesViewHolder(view);;
        return recipesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
    String name = recipes.get(position).getName();
    String image =recipes.get(position).getImage();

    holder.Bind(name,image);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView recipeNameTextView;
        ImageView recipeImageView;
        public RecipesViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView=(TextView)itemView.findViewById(R.id.recipeNameTextView);
            recipeImageView=(ImageView)itemView.findViewById(R.id.recipeImageView);
            itemView.setOnClickListener(this);
        }

        void Bind(String name, String image)
        {

            recipeNameTextView.setText(name);
            if(image!="")
            recipeImageView.setImageResource(Integer.parseInt(image));
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClicked(clickedPosition);
        }
    }
}
