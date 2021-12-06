package com.irayasoft.pakkruti.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.irayasoft.pakkruti.R;
import com.irayasoft.pakkruti.databinding.FragmentRecipeDetailBinding;
import com.irayasoft.pakkruti.model.DogBreed;
import com.irayasoft.pakkruti.util.Util;
import com.irayasoft.pakkruti.viewmodel.DetailViewModel;

public class RecipeDetailFragment extends Fragment {

    private int recipe_uiid;
    private FragmentRecipeDetailBinding binding;
    DetailViewModel detailViewModel;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailViewModel = ViewModelProvider.AndroidViewModelFactory.
                getInstance(getActivity().getApplication()).create(DetailViewModel.class);
        // detailViewModel.fetch();
        observeViewModel();

        //*****************receive the navigation argument *******************
        if (getArguments() != null) {
            recipe_uiid = RecipeDetailFragmentArgs.fromBundle(getArguments()).getUid();
        }
        String uid = String.valueOf(recipe_uiid);
        //  binding.textView2.setText(uid);
        detailViewModel.fetch(recipe_uiid);


//        binding.fabDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_Details_List);
//            }
//        });

    }

    private void observeViewModel() {
        detailViewModel.dogLivedata.observe(this, new Observer<DogBreed>() {
            @Override
            public void onChanged(DogBreed dogBreed) {
                // binding.imgDog
                if (dogBreed != null && dogBreed instanceof DogBreed && getContext()!=null)
                {
                    binding.tvDogname.setText(dogBreed.breed_name);
                    binding.tvDoglifespan.setText(dogBreed.lifespan);
                    binding.tvDogPurpose.setText(dogBreed.breedGroup);
                    binding.tvTemperment.setText(dogBreed.temparament);
                    if (dogBreed.imgUrl != null) {
                        Util.LoadImage(binding.imgDog, dogBreed.imgUrl,
                                new CircularProgressDrawable(getContext()));

                    }

                }

            }
        });

    }
}