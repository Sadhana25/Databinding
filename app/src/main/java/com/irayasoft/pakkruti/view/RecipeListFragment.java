package com.irayasoft.pakkruti.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irayasoft.pakkruti.R;
import com.irayasoft.pakkruti.databinding.FragmentRecipeListBinding;

public class RecipeListFragment extends Fragment {
    private FragmentRecipeListBinding binding;
    public RecipeListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        final NavController navController=Navigation.findNavController(view);
        binding.fabList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fab click event
                // Navigation.findNavController(v).navigate(R.id.action_List_Detail);
                RecipeListFragmentDirections.ActionListDetail action = RecipeListFragmentDirections
                        .actionListDetail();
                 action.setUid(88);
                navController.navigate(action);
            }
        });
    }
}