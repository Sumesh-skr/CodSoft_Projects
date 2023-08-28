package com.mastercoding.flashlightapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    ImageButton Image_button;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Image_button= findViewById(R.id.torchbtn);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashLight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }
    private void runFlashLight() {
        Image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!state)
                {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try{
                        String cameraId= cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId, true);
                        state= true;
                        Image_button.setImageResource(R.drawable.torch_on);
                        Toast.makeText(MainActivity.this, "FlashLight is On", Toast.LENGTH_SHORT).show();
                    }
                    catch(CameraAccessException e){}
                }
                else {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try{
                        String cameraId= cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId, false);
                        state= false;
                        Image_button.setImageResource(R.drawable.torch_off);
                        Toast.makeText(MainActivity.this, "FlashLight is Off", Toast.LENGTH_SHORT).show();
                    }
                    catch(CameraAccessException e){}
                }
            }
        });
    }
}