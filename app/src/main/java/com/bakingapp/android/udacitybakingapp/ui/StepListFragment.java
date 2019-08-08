package com.bakingapp.android.udacitybakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Recipe;
import com.bakingapp.android.udacitybakingapp.model.Step;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

//Fragment responsible for showing the list of Steps with a RecyclerView inside
public class StepListFragment extends Fragment {

    static final String STEPS_ARG = "STEPS_ARG";

    @BindView(R.id.step_recycler_view)
    RecyclerView stepRecyclerView;

    private StepListAdapter adapter;

    OnStepClickListener stepCallback;

    private List<Step> stepList;

    public interface OnStepClickListener {
        void onStepSelected(Step step);
    }

    public StepListFragment() {
    }

    static StepListFragment newInstance(Recipe recipe) {
        StepListFragment fragment = new StepListFragment();
        Bundle b = new Bundle();
        String recipeJson = new Gson().toJson(recipe);
        b.putString(STEPS_ARG, recipeJson);
        fragment.setArguments(b);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            String arg = getArguments().getString(STEPS_ARG);
            Recipe recipe = new Gson().fromJson(arg, Recipe.class);
            stepList = recipe.getSteps();
        }

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupStepList();
    }


    private void setupStepList() {
        adapter = new StepListAdapter(step -> {
            stepCallback.onStepSelected(step);
        });

        adapter.setStepList(stepList);

        stepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        stepRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            stepCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }
    }

}
