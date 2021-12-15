package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class array_setActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.array_set);
    }

    public void arraySetOnClick(View v) {finish(); }

    //xml에 리스트 내 위치에 따라 zeroth~seventh의 아이디를 부여함
    //클릭된 정렬방식을 숫자로 파악하여, 그 값을 파일에 sort_num으로 저장하고 메인 xml로 귀환
    public void zeroth(View v) {
        SharedPreferences preferences = getSharedPreferences("array_set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sort_num", 0);
        editor.commit();
        finish();
    }

    public void first(View v) {
        SharedPreferences preferences = getSharedPreferences("array_set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sort_num", 1);
        editor.commit();
        finish();
    }

    public void second(View v) {
        SharedPreferences preferences = getSharedPreferences("array_set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sort_num", 2);
        editor.commit();
        finish();
    }

    public void third(View v) {
        SharedPreferences preferences = getSharedPreferences("array_set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sort_num", 3);
        editor.commit();
        finish();
    }

    public void forth(View v) {
        SharedPreferences preferences = getSharedPreferences("array_set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sort_num", 4);
        editor.commit();
        finish();
    }

    public void fifth(View v) {
        SharedPreferences preferences = getSharedPreferences("array_set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sort_num", 5);
        editor.commit();
        finish();
    }

    public void sixth(View v) {
        SharedPreferences preferences = getSharedPreferences("array_set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sort_num", 6);
        editor.commit();
        finish();
    }

    public void seventh(View v) {
        SharedPreferences preferences = getSharedPreferences("array_set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sort_num", 7);
        editor.commit();
        finish();
    }


}