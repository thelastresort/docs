package com.flamingo.demo.guopan.searchapp;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;


/**
 * Created by jammy on 15/11/6.
 */
public class AssetsUtil {

    private static final String TAG = "xxlib_AssetsUtil";

    public static void copyFromAssets(Context context, String origFileName, String outpath) {
        try {
            File destFile = new File(outpath);
            File dir = destFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!destFile.exists()) {
                destFile.createNewFile();
            }

            InputStream myInput;
            OutputStream myOutput = new FileOutputStream(outpath);
            myInput = context.getAssets().open(origFileName);
            byte[] buffer = new byte[1024];
            int length = myInput.read(buffer);
            while (length > 0) {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String readFile(File file, String charset) {
        if (file == null || !file.exists()) {
            return "";
        }

        StringWriter sw = new StringWriter();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(file), charset == null ? "utf-8" : charset);
            char[] buf = new char[1024];
            int len = 0;
            while ((len = isr.read(buf)) != -1) {
                sw.write(buf, 0, len);
            }

            return sw.toString();

        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}

