package com.bakingapp.android.udacitybakingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepListViewHolder> {

    private List<Step> stepList = new ArrayList<>();

    interface StepClickListener {
        void onClickStep(Step step);
    }

    StepClickListener listener;

    public StepListAdapter(StepClickListener listener) {
        this.listener = listener;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_item, parent, false);
        return new StepListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepListViewHolder holder, int position) {
        holder.bind(stepList.get(position));
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    class StepListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_item_description)
        TextView description;

        @BindView(R.id.step_item_short_description)
        TextView shortDescription;

        @BindView(R.id.step_item_container)
        ConstraintLayout container;


        StepListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Step step) {
            shortDescription.setText(step.getShortDescription());
            description.setText(step.getDescription());

            container.setOnClickListener(view -> listener.onClickStep(step));
        }
    }


}
