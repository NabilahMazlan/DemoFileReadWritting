package com.example.demofilereadwritting;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {
    Button btnRead, btnWrite;
    TextView tv;
    String folderLocation;
    String internalFolderLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRead = findViewById(R.id.buttonRead);
        btnWrite = findViewById(R.id.buttonWrite);
        tv = findViewById(R.id.textView);

        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";
        internalFolderLocation = getFilesDir().getAbsolutePath() + "/InternalFolder";


        if(checkPremission() == true){
            File folder = new File(folderLocation);
            File internalFolder = new File(internalFolderLocation);
            if(folder.exists() == false){
                boolean result = folder.mkdir();
                boolean internalResult = internalFolder.mkdir();
                if (result == true){
                    Log.d("File Read/Write", "Folder created");
                    Toast.makeText(MainActivity.this, "Folder created", Toast.LENGTH_SHORT).show();
                }else if (internalResult == true){
                    Log.d("File Read/Write", "Internal folder created");
                    Toast.makeText(MainActivity.this, "Folder created", Toast.LENGTH_SHORT).show();

                }else if(result == false){
                    Toast.makeText(MainActivity.this, "Folder creation failed", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "Internal folder creation failed", Toast.LENGTH_SHORT).show();

                }
            }else if(internalFolder.exists() == false){
                boolean result = folder.mkdir();
                if (result == true){
                    Log.d("File Read/Write", "Folder created");
                    Toast.makeText(MainActivity.this, "Folder created", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Failed to folder", Toast.LENGTH_SHORT).show();

                }
            }
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try{
                        File file = new File(folderLocation, "data.txt");
                        FileWriter writer = new FileWriter(file, true);
                        writer.write("Hello World \n");
                        writer.flush();
                        writer.close();

                        File internalFile = new File(internalFolderLocation, "text.txt");
                        FileWriter internalWriter = new FileWriter(internalFile, true);
                        internalWriter.write("test data" + "\n");
                        internalWriter.flush();
                        internalWriter.close();

                        Toast.makeText(MainActivity.this, "Write Successful", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Failed to write", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File target = new File(folderLocation, "data.txt");
                if(target.exists() == true){
                    String data = "";
                    try{
                        FileReader reader = new FileReader(target);
                        BufferedReader br = new BufferedReader(reader);

                        String line = br.readLine();
                        while(line != null){
                            data += line + "\n";
                            line = br.readLine();

                        }

                        br.close();
                        reader.close();
                    }catch(Exception e){
                        Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    Log.d("Content", data);
                    tv.setText(data);
                }
            }
        });

    }

    private boolean checkPremission(){
        int permissionCheck_WRITE = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck_READ = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck_READ == PermissionChecker.PERMISSION_GRANTED && permissionCheck_WRITE == PermissionChecker.PERMISSION_GRANTED){
            return true;
        }else{
            return  false;
        }
    }


}
