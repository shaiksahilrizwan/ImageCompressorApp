package com.imgcomp.imagecompressor;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.imgcomp.imagecompressor.compression_saving.ImageCompression;

public class MainActivity extends AppCompatActivity {

    private Uri imageUri=null;
    private ImageView selectedImage;
    ActivityResultLauncher<String> takeImage;
    private SeekBar seekBar;
    private TextView qualitySelected;
    private int quality=0;
    private static final int GOT_ALL_PERMISSIONS=100;
    private AppCompatImageButton convertButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        convertButton=findViewById(R.id.btnConvert);
        qualitySelected=findViewById(R.id.show_Quality);
        selectedImage=findViewById(R.id.imageSelected);
        seekBar=findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(imageUri==null) {
                    Toast.makeText(MainActivity.this, "Select Image First", Toast.LENGTH_SHORT).show();
                    seekBar.setProgress(0);
                }else{
                    quality=seekBar.getProgress();
                    qualitySelected.setText("Quality Selected : "+quality);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            //No work for now
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            //No work for now
            }
        });
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImage.launch("image/*");
            }
        });
        takeImage=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                imageUri=o;
                selectedImage.setImageURI(o);
            }
        });
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri == null) {
                    Toast.makeText(MainActivity.this, "Select a Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(quality<=0){
                    Toast.makeText(MainActivity.this, "Select Quality currently it is zero", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    ImageCompression imgComp=new ImageCompression(selectedImage,imageUri,MainActivity.this,quality);
                    try {
                        Dialog d=new Dialog(MainActivity.this);
                        d.show();
                        d.setCancelable(false);
                        imgComp.convertImage();
                        d.dismiss();
                        Toast.makeText(MainActivity.this, "Image Saved at Storage/Compressed Images/", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
                        ad.setTitle("Error");
                        ad.setMessage("There was a Problem Saving the compressed image");
                        ad.show();
                    }
                }
            }
        });
    }
    protected void onStart() {
        super.onStart();
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},GOT_ALL_PERMISSIONS);
            }
        }
    }
}