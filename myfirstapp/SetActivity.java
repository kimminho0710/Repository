package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;


public class SetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

    }

    //취소가 눌리면 이전 화면으로
    public void setOnClick(View v) {finish(); }

    //텍스트 설정 화면으로
    public void textSetOnClick(View v) {
        Intent intent = new Intent(this, text_setActivity.class);
        startActivity(intent);
    }

    //정렬 설정 화면으로
    public void arraySetOnClick(View v) {
        Intent intent = new Intent(this, array_setActivity.class);
        startActivity(intent);
    }
}