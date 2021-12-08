package com.irayasoft.pakkruti.view;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.palette.graphics.Palette;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.irayasoft.pakkruti.R;
import com.irayasoft.pakkruti.databinding.FragmentRecipeDetailBinding;
import com.irayasoft.pakkruti.databinding.SendSmsBinding;
import com.irayasoft.pakkruti.model.DogBreed;
import com.irayasoft.pakkruti.model.DogPallete;
import com.irayasoft.pakkruti.model.SmsInfo;
import com.irayasoft.pakkruti.util.Util;
import com.irayasoft.pakkruti.viewmodel.DetailViewModel;

public class RecipeDetailFragment extends Fragment {
    private static final String TAG = RecipeDetailFragment.class.getName();
    private int recipe_uiid;
    private FragmentRecipeDetailBinding binding;
    DetailViewModel detailViewModel;
    private Boolean sendSmsStart = false;
    private DogBreed currDog;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        //  binding = FragmentRecipeDetailBinding.inflate(inflater, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);
        this.binding = binding;
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
                currDog = dogBreed;
                // binding.imgDog
                if (dogBreed != null && dogBreed instanceof DogBreed && getContext() != null) {
                    binding.setDog(dogBreed);
//                    binding.tvDogname.setText(dogBreed.breed_name);
//                    binding.tvDoglifespan.setText(dogBreed.lifespan);
//                    binding.tvDogPurpose.setText(dogBreed.breedGroup);
//                    binding.tvTemperment.setText(dogBreed.temparament);

                    if (dogBreed.imgUrl != null) {
//                        Util.loadImage(binding.imgDog, dogBreed.imgUrl,
//                                new CircularProgressDrawable(getContext()));
                        setbackgroundColor(dogBreed.imgUrl);

                    }

                }

            }
        });

    }

    //implement pallete library
    public void setbackgroundColor(String Url) {
        Glide.with(this)
                .asBitmap()
                .load(Url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(palette -> {
                            int color = palette.getLightMutedSwatch().getRgb();
                            DogPallete dogPallete = new DogPallete(color);
                            binding.setPallete(dogPallete);

                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }
    //menu option

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //switch case
        switch (item.getItemId()) {
            case R.id.menu_send:

                if (!sendSmsStart) {
                    sendSmsStart = true;
                    sendDialogue();
                    //((MainActivity) getActivity()).checkSMSPermissions();
                    Toast.makeText(getContext(), "send function", Toast.LENGTH_LONG).show();
                }
                //send function

                break;
            case R.id.menu_share:
                //share function
                Toast.makeText(getContext(), "share function", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"check out dog dreed");
                intent.putExtra(Intent.EXTRA_TEXT,currDog.breed_name+"breed for"+ currDog.breedFor);
                intent.putExtra(Intent.EXTRA_STREAM,currDog.imgUrl);
                startActivity(Intent.createChooser(intent,"share with :"));
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void sendDialogue() {
        String msg_body = "this is templte to send " + currDog.breed_name + "for" + currDog.breedFor;
        SmsInfo smsInfo = new SmsInfo("7350741603", msg_body, currDog.imgUrl);
        //dialoge
        SendSmsBinding smsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.send_sms,
                null,
                false
        );
        smsBinding.setInfo(smsInfo);
        new AlertDialog.Builder(getContext())
                .setView(smsBinding.getRoot())
                .setPositiveButton("send SMS", (dialog, which) -> {
                    if (!smsBinding.etTo.getText().toString().isEmpty()) {
                        smsInfo.to = smsBinding.etTo.getText().toString();
                        sendSMS(smsInfo);
                    }

                })
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .show();

    }

    public void onPermissionsResult(boolean permissionGranted) {
        Log.d(TAG, "onPermissionsResult: ");
        if (isAdded() && sendSmsStart && permissionGranted) {
            SmsInfo smsInfo = new SmsInfo("", currDog.breed_name +
                    "for" + currDog.breedFor, currDog.imgUrl);

            //dialoge
            SendSmsBinding smsBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.send_sms,
                    null,
                    false
            );
            //set the value to binding layout
            smsBinding.setInfo(smsInfo);
            new AlertDialog.Builder(getContext())
                    .setView(smsBinding.getRoot())
                    .setPositiveButton("send SMS", (dialog, which) -> {
//                        if (!smsBinding.etTo.getText().toString().isEmpty()) {
//                            smsInfo.to = smsBinding.etTo.getText().toString();
//                            smsInfo.text=
//                            sendSMS(smsInfo);
//                        }

                    })
                    .setNegativeButton("cancel", (dialog, which) -> {

                    })
                    .show();

            sendSmsStart = false;

        }
    }

    private void sendSMS(SmsInfo smsInfo) {
        Log.d(TAG, "sendSMS: ");
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(smsInfo.to, null, smsInfo.text, pendingIntent, null);
        Toast.makeText(getActivity(), "SMS sent", Toast.LENGTH_LONG).show();
    }
}