package com.irayasoft.pakkruti.view;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.irayasoft.pakkruti.R;
import com.irayasoft.pakkruti.databinding.FragmentRecipeDetailBinding;
public class RecipeDetailFragment extends Fragment {
    private int recipe_uiid;
    private FragmentRecipeDetailBinding binding;
    public RecipeDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            recipe_uiid=RecipeDetailFragmentArgs.fromBundle(getArguments()).getUid();
            String uid= String.valueOf(recipe_uiid);
            binding.textView2.setText(uid);
        }
        binding.fabDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_Details_List);
            }
        });
        return binding.getRoot();
    }
}