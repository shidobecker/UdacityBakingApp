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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepListViewHolder> {

    private List<Step> stepList = new ArrayList<>();

    interface StepClickListener {
        void onClickStep(Step step);
    }

    private StepClickListener listener;

    private int currentItem = 0;

    StepListAdapter(StepClickListener listener) {
        this.listener = listener;
    }

    void setStepList(List<Step> stepList) {
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
        holder.bind(stepList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    class StepListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_item_short_description)
        TextView shortDescription;

        @BindView(R.id.step_item_container)
        ConstraintLayout container;


        StepListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Step step, int position) {

            if (position == currentItem) {
                container.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorStepListBGSelected));
            } else {
                container.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorStepListBG));
            }

            shortDescription.setText(step.getShortDescription());

            container.setOnClickListener(view -> {
                listener.onClickStep(step);
                currentItem = position;
                notifyDataSetChanged();
            });
        }
    }


}
