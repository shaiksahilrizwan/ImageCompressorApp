package com.imgcomp.imagecompressor.compression_saving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.content.ContentResolver;
import com.imgcomp.imagecompressor.StorageAllocator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompression {
    private ImageView image;
    private Drawable tempImage;
    private Bitmap bitmap;
    private Uri imageUri;
    private ContentResolver resolver;
    private FileOutputStream outputStream;
    private int quality;
    public ImageCompression(ImageView image,Uri imageUri,Context context,int quality) {
        this.image = image;
        this.imageUri=imageUri;
        this.resolver= context.getContentResolver();
        this.quality=quality;
    }
    private String getImageExtension(){
        String mimeType = resolver.getType(imageUri);
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
        return(extension.equals("png")?".png":".jpg");
    }
    public void convertImage() throws IOException {
        if(!StorageAllocator.checkIfPathExists())
            StorageAllocator.createPath();
        tempImage=image.getDrawable();

        bitmap = ((BitmapDrawable) tempImage).getBitmap();
        String extension=getImageExtension();
        File savingFile=new File(StorageAllocator.path,"Compressed"+System.currentTimeMillis()+extension);
        outputStream=new FileOutputStream(savingFile);
        bitmap.compress(extension.equals(".jpg")?Bitmap.CompressFormat.JPEG:Bitmap.CompressFormat.PNG, quality, outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
