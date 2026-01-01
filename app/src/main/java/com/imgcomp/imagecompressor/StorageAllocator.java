package com.imgcomp.imagecompressor;

import android.os.Environment;

import java.io.File;

public class StorageAllocator {
    public static final File path=new File(Environment.getExternalStorageDirectory().getPath(),"/Compressed Images");
    public static boolean checkIfPathExists(){
        return(path.exists()?true:false);
    }
    public static void createPath(){
        if(!path.exists()) {
            path.mkdir();
        }
    }
}
