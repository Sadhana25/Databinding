package com.irayasoft.pakkruti.view;
import static com.irayasoft.pakkruti.R.drawable.dogimg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder;
import androidx.recyclerview.widget.RecyclerView;

import com.irayasoft.pakkruti.R;
import com.irayasoft.pakkruti.model.DogBreed;
import com.irayasoft.pakkruti.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DogsListAdapter extends RecyclerView.Adapter<DogsListAdapter.ViewHolder> {
    private static final String TAG=DogsListAdapter.class.getName();
    private List<DogBreed> dogList;

    public DogsListAdapter( List<DogBreed> dogList) {
        Log.d(TAG, "DogsListAdapter: "+dogList.size());
        this.dogList = dogList;
    }
    // update doglist
    public void UpdateDogsList(List<DogBreed> dogListnew){
        dogList.clear();
        dogList.addAll(dogListnew);
        Log.d(TAG, "UpdateDogsList: "+dogListnew.size());
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public DogsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DogsListAdapter.ViewHolder holder, int position) {

         DogBreed dogBreed=dogList.get(position);
        ConstraintLayout layout=holder.itemView.findViewById(R.id.con_listLayout);
        ImageView imageView =holder.itemView.findViewById(R.id.img_profile);
        TextView dogName=holder.itemView.findViewById(R.id.tv_tittle);
        TextView dogDesc=holder.itemView.findViewById(R.id.tv_desc);
        dogName.setText(dogBreed.getBreed_name());
        dogDesc.setText(dogBreed.getBreedFor());
        Util.LoadImage(imageView,dogBreed.imgUrl,Util.getProgressDrawable(imageView.getContext()));

        layout.setOnClickListener(v -> {
             RecipeListFragmentDirections.ActionListDetail action=RecipeListFragmentDirections.actionListDetail();
             action.setUid(dogBreed.uuid);
             Navigation.findNavController(layout).navigate(action);
            Toast.makeText(v.getContext(), "click function", Toast.LENGTH_SHORT).show();

        });
     // imageView
    }
    @Override
    public int getItemCount() {
        return dogList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // initialite view
        public View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //dobinding
            this.itemView = itemView;
        }
    }
}
