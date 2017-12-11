package com.example.deh3215.okhttp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    int flag=0;
    String downLoadStr="http://od.moi.gov.tw/api/v1/rest/datastore/A01010000C-000674-011";

    //=========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        寫進資料在專案目錄底下
//        PackageManager m = getPackageManager();
//        String s = getPackageName();
//        String path="test.csv";
//        File uploadedFile = new File("/data/data/"+s+"/files/"+path);
//        Log.d("FilePath", "/data/data/"+s+"/files/"+path);

//        File sdCard = Environment.getExternalStorageDirectory();
//        File dir = new File (sdCard.getAbsolutePath() + "/dir1/dir2");
//        dir.mkdirs();
//        File file = new File(dir, "filename");

        File sdCard = Environment.getExternalStorageDirectory();
        String path="test.csv";
        File uploadedFile = new File(sdCard+"/"+path);
        Log.d("FilePath", "");


        String str;
        if(uploadedFile.exists()) {
            str = "File Exist";
            flag=1;
        }   else
            str = "File Not Exist !";
        Log.d("FileInfo", str);

        if(flag == 0) {
            try {
                downloadFileAsync(downLoadStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadFileAsync(final String downloadUrl) throws Exception {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(downloadUrl).build();

            client.newCall(request).enqueue(new Callback() {
                String path = "test.csv";

                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.d("Test", "Failure");
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Failed to download file: " + response);
                    }
                    Log.d("Test", "Success");
                    //FileOutputStream fos = new FileOutputStream(path);
                    FileOutputStream fos = openFileOutput(path, Context.MODE_PRIVATE);
                    fos.write(response.body().bytes());
                    fos.close();
                }
            });
    }
}