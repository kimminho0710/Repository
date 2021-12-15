package com.example.myfirstapp;

import java.io.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.math.*;
import java.util.*;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.lang.String;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.*;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends Activity {

    //프로그램 시작
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        //UI 객체 불러오기
        TextView fileName = (TextView)findViewById(R.id.file_name);
        TextView filePath = (TextView)findViewById(R.id.file_path);
        TextView fileMakeDay = (TextView)findViewById(R.id.file_makeDay);
        TextView fileChangeDay = (TextView)findViewById(R.id.file_changeDay);
        TextView fileVolume = (TextView)findViewById(R.id.file_volume);

        //main에서 전달받은 파일 주소를 가지고, 파일의 정보 불러온 뒤 객체에 기록하여 사용자에게 보여주기
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        File f = new File(path);
        try {
            Path checkPath = Paths.get(path);
            BasicFileAttributes readAttributes = Files.readAttributes(checkPath, BasicFileAttributes.class);

            fileName.setText("파일명: " + f.getName());
            filePath.setText("파일 위치: " + path);
            fileMakeDay.setText("생성일자: " + format.format(readAttributes.creationTime().toMillis()));
            fileChangeDay.setText("수정일자: " + format.format(readAttributes.lastModifiedTime().toMillis()));

            //파일 크기에 따라 한도를 정하여 보여줄 단위를 정하고 단위에 맞게 크기 계산하여 보여주기
            String fileVolumeForm;
            if(Files.size(checkPath) < 128) {
                fileVolumeForm = Long.toString(Files.size(checkPath)) + "byte";
            }
            else if(Files.size(checkPath) < 262144) {
                fileVolumeForm = Double.toString(Math.round(Files.size(checkPath)/1024.00)) + "KB";
            }
            else if(Files.size(checkPath) < 536870912) {
                fileVolumeForm = Double.toString(Math.round(Files.size(checkPath)/1048576.00)) + "MB";
            }
            else {
                fileVolumeForm = Double.toString(Math.round(Files.size(checkPath)/1073741824.00)) + "GB";
            }

            fileVolume.setText("파일 크기: " + fileVolumeForm);


        } catch (IOException ie) {}
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //액티비티(팝업) 닫기
        finish();
    }
}