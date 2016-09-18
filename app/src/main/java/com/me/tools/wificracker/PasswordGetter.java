package com.me.tools.wificracker;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PasswordGetter {
    private String password;
    private File file;
    private FileReader reader;
    private BufferedReader br;
    private InputStreamReader inputReader;
    private Context context;
    private String assetsName;

    public PasswordGetter(String passwordFile) throws FileNotFoundException {
        password = null;

        //File file = new File("/sdcard/password.txt");
        file = new File(passwordFile);
        if (!file.exists())
            throw new FileNotFoundException();
        reader = new FileReader(file);
        br = new BufferedReader(reader);
    }

    public PasswordGetter(Context context, String assetsName) throws IOException {
        password = null;
        this.context = context;
        this.assetsName = assetsName;
        inputReader = new InputStreamReader(context.getResources().getAssets().open(assetsName));
        br = new BufferedReader(inputReader);
    }

    public void reSet() {
        try {
            if (br != null)
                br.close();
            if (reader != null)
                reader.close();
            if (file != null) {
                reader = new FileReader(file);
                br = new BufferedReader(reader);
            } else {
                inputReader = new InputStreamReader(context.getResources().getAssets().open(assetsName));
                br = new BufferedReader(inputReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
            password = null;
        }
    }

    public String getPassword() {
        try {
            password = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            password = null;
        }
        return password;
    }

    public void Clean() {
        try {
            br.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}