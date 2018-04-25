package com.andrea.bakingapp.features.main.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.util.GlideUtil;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private ListItemClickListener listItemClickListener;

    public interface ListItemClickListener {
        void onListItemClicked(@NonNull Recipe recipe);
    }

    RecipeAdapter(@NonNull ListItemClickListener listItemClickListener, @NonNull List<Recipe> recipeList) {
        this.listItemClickListener = listItemClickListener;
        this.recipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recipeList != null && recipeList.size() > 0 ? recipeList.size() : 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView recipeNameTextView;
        private TextView recipeServingSize;
        private ImageView recipeImage;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipeNameTextView);
            recipeServingSize = itemView.findViewById(R.id.recipeServingSize);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            itemView.setOnClickListener(this);
        }

        void bind(int listItem) {
            recipeNameTextView.setText(recipeList.get(listItem).getName());
            recipeServingSize.setText(String.valueOf(recipeList.get(listItem).getServings()));

            GlideUtil.displayImage(recipeList.get(listItem).getImage(), recipeImage);
        }

        @Override
        public void onClick(View view) {
            listItemClickListener.onListItemClicked(recipeList.get(getAdapterPosition()));
        }
    }
}
