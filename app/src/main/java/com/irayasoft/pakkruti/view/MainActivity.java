package com.irayasoft.pakkruti.view;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.irayasoft.pakkruti.R;
import com.irayasoft.pakkruti.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_SMS = 1;
    private static final String TAG = MainActivity.class.getName();
    private NavController navController;
    private ActivityMainBinding binding;
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment=getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // setSupportActionBar();
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupActionBarWithNavController(this, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        //placing null bcz of we are not using drawer layout
        return NavigationUI.navigateUp(navController, (DrawerLayout) null);
    }
    public void checkSMSPermissions(){
        Log.d(TAG, "checkSMSPermissions: ");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS
        )!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){
                  new AlertDialog.Builder(this)
                          .setTitle("SMS Permission")
                          .setMessage("this app allow sms permission ")
                          .setPositiveButton("ask me", (dialog, which) -> {
                            requestSmsPermissions();
                          }).setNegativeButton("no", (dialog, which) -> {
                              notifyDetailFragment(false);
                          }).show();
            }
            else{
                requestSmsPermissions();
            }

        }
        else{
           notifyDetailFragment(true);
        }

    }

    private void requestSmsPermissions() {
        Log.d(TAG, "requestSmsPermissions: ");
        String[] permissions={Manifest.permission.SEND_SMS};
        ActivityCompat.requestPermissions(this,permissions,PERMISSION_SMS);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_SMS:
                if(grantResults.length >0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED ){
                  notifyDetailFragment(true);
                    Log.d(TAG, "onRequestPermissionsResult: ");
                }
                else {
                    notifyDetailFragment(false);
                }
                break;
        }
    }

    private void notifyDetailFragment(boolean permissionGranted) {
        Log.d(TAG, "notifyDetailFragment: call"+permissionGranted);

        Fragment activeFragment=getSupportFragmentManager().getPrimaryNavigationFragment();

        if(activeFragment instanceof RecipeDetailFragment){
            Log.d(TAG, "notifyDetailFragment:  instance ");
              ((RecipeDetailFragment) activeFragment).onPermissionsResult(permissionGranted);

        }

    }
}