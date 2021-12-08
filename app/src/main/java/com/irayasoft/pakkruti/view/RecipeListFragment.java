package com.irayasoft.pakkruti.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.irayasoft.pakkruti.R;
import com.irayasoft.pakkruti.databinding.FragmentRecipeListBinding;
import com.irayasoft.pakkruti.model.DogBreed;
import com.irayasoft.pakkruti.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeListFragment extends Fragment {
    private static final String TAG=RecipeListFragment.class.getName();
    private FragmentRecipeListBinding binding;
    private DogsListAdapter dogsListAdapter = new DogsListAdapter(new ArrayList<>());
    ListViewModel listViewModel;
    public RecipeListFragment() {
        // Required empty public constructor
    }
    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentRecipeListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        final NavController navController = Navigation.findNavController(view);
        listViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()
        ).create(ListViewModel.class);
        listViewModel.refresh();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(dogsListAdapter);
        observeViewModel();
        //swiperefreshfunctionality
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.recyclerView.setVisibility(View.GONE);
            binding.tvError.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            listViewModel.refreshBypassCache();
            binding.swipeRefreshLayout.setRefreshing(false);


        });

        //********************safe args Implementation*******************
//        binding.fabList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // fab click event
//                // Navigation.findNavController(v).navigate(R.id.action_List_Detail);
//                RecipeListFragmentDirections.ActionListDetail action = RecipeListFragmentDirections
//                        .actionListDetail();
//                 action.setUid(88);
//                navController.navigate(action);
//            }
//        });
    }

    private void observeViewModel() {


        listViewModel.dogs.observe(getActivity(), new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> dogBreeds) {
                if (dogBreeds != null && dogBreeds instanceof List) {
                    Log.d(TAG, "onChanged: "+dogBreeds.size());
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    dogsListAdapter.UpdateDogsList(dogBreeds);
                }
            }
        });

        listViewModel.loading.observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null && isLoading instanceof Boolean) {
                    binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        binding.tvError.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });
        listViewModel.dogLoadError.observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null && isError instanceof Boolean) {
                    binding.tvError.setVisibility(isError ? View.VISIBLE : View.GONE);

                }

            }
        });
    }
    //************************for setting screen>>> menu setting

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_setting:
                if(isAdded()){
                    Navigation.findNavController(getView()).navigate(RecipeListFragmentDirections.actionListSetting());
                }
                break;
        }



        return super.onOptionsItemSelected(item);
    }
}