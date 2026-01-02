package com.imgcomp.imagecompressor;

import static android.view.View.GONE;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    private ImageCompression imageCompression;
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
        // fetching all the UI Views
        convertButton=findViewById(R.id.btnConvert);
        qualitySelected=findViewById(R.id.show_Quality);
        selectedImage=findViewById(R.id.imageSelected);
        seekBar=findViewById(R.id.seekBar);
        // Handling SeekBar Inputs
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
        // Selecting Image Throught Intent
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
            //checking possible conditions
            @Override
            public void onClick(View v) {
                if (imageUri == null) {
                    Toast.makeText(MainActivity.this, "Select a Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(quality<=0){
                    Toast.makeText(MainActivity.this, "Select Quality currently it is zero", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (quality>=90) {
                    Dialog d= new Dialog(MainActivity.this);
                    d.setContentView(R.layout.loading_dialog);
                    ProgressBar p= d.findViewById(R.id.pgbar);
                    p.setVisibility(GONE);
                    TextView loading_tv=d.findViewById(R.id.loading_tv);
                    loading_tv.setText("You have Selected Maximum Quality It will Increase the Image Size");
                    d.setCancelable(true);
                    Button compress=d.findViewById(R.id.compress);
                    Button cancle=d.findViewById(R.id.cancle);
                    d.show();
                    compress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                            compressorMethod();
                        }
                    });
                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                            return;
                        }
                    });
                }
                else{
                    compressorMethod();
                }
            }
        });
    }
    // compress image method
    private void compressorMethod(){
        if(imageCompression==null)
            imageCompression=new ImageCompression(selectedImage,imageUri,MainActivity.this,quality);
        else{
            imageCompression.setImage(selectedImage);
            imageCompression.setImageUri(imageUri);
            imageCompression.setQuality(quality);
        }
        // compress image
        try {
            Dialog d=new Dialog(MainActivity.this);
            d.setContentView(R.layout.loading_dialog);
            Button compress=(Button) d.findViewById(R.id.compress);
            Button cancle=(Button)d.findViewById(R.id.cancle);
            compress.setVisibility(GONE);
            cancle.setVisibility(GONE);
            d.show();
            d.setCancelable(false);
            imageCompression.convertImage();
            d.dismiss();
            Toast.makeText(MainActivity.this, "Image Saved at Pictures/CompressedImages/", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
            ad.setTitle("Error");
            ad.setMessage("There was a Problem Saving the compressed image");
            ad.show();
        }
    }
}