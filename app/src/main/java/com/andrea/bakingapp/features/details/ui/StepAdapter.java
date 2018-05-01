package com.andrea.bakingapp.features.details.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.domain.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepsViewHolder> {

    private List<Step> steps;
    private Recipe recipe;
    private ListItemClickedListener listItemClickedListener;

    public interface ListItemClickedListener {
        void onListItemClicked(@NonNull Step step, @NonNull Recipe recipe);
    }

    StepAdapter(@NonNull ListItemClickedListener listItemClickedListener, @NonNull List<Step> steps, @NonNull Recipe recipe) {
        this.listItemClickedListener = listItemClickedListener;
        this.steps = steps;
        this.recipe = recipe;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return steps != null && steps.size() > 0 ? steps.size() : 0;
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.stepsTextView) TextView stepTextView;

        StepsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(int listItem) {
            stepTextView.setText(steps.get(listItem).getShortDescription());
        }

        @Override
        public void onClick(View view) {
            listItemClickedListener.onListItemClicked(steps.get(getAdapterPosition()), recipe);
        }
    }
}
