package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class text_setActivity extends AppCompatActivity {

    //프로그램 시작
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_set);

        //각 체크박스 변수 불러오기
        CheckBox box1 = (CheckBox) findViewById(R.id.box1);
        CheckBox box2 = (CheckBox) findViewById(R.id.box2);
        CheckBox box3 = (CheckBox) findViewById(R.id.box3);
        CheckBox box4 = (CheckBox) findViewById(R.id.box4);
        CheckBox box5 = (CheckBox) findViewById(R.id.box5);
        CheckBox box6 = (CheckBox) findViewById(R.id.box6);

        //이전에 클릭되어 있었던 체크박스의 클릭여부를 유지. 만약 없었다면 box1을 체크
        String check_box;
        SharedPreferences preferences = getSharedPreferences("text_set", Context.MODE_PRIVATE);
        check_box = preferences.getString("checked_box", "box1");


        if (check_box.equals("box1")) {
            box1.setChecked(true);
        }
        else if (check_box.equals("box2")) {
            box2.setChecked(true);
        }
        else if (check_box.equals("box3")) {
            box3.setChecked(true);
        }
        else if (check_box.equals("box4")) {
            box4.setChecked(true);
        }
        else if (check_box.equals("box5")) {
            box5.setChecked(true);
        }
        else {
            box6.setChecked(true);
        }

        //이전에 설정되어 있었던 텍스트 크기 값 유지. 만약 없었다면 25로 설정
        EditText text_size = (EditText) findViewById(R.id.editTextSize);
        text_size.setText(preferences.getString("text_size","25"));
    }
    //취소시 이전 화면으로 돌아가기
    public void textSetOnClick(View v) {
        finish();
    }

    //만약 박스 하나가 클릭되면, 다른 박스의 체크 표시를 끄고 클릭된 박스 하나만 체크 표시가 되도록 정의.
    public void boxClick(View v) {
        CheckBox box1 = (CheckBox) findViewById(R.id.box1);
        CheckBox box2 = (CheckBox) findViewById(R.id.box2);
        CheckBox box3 = (CheckBox) findViewById(R.id.box3);
        CheckBox box4 = (CheckBox) findViewById(R.id.box4);
        CheckBox box5 = (CheckBox) findViewById(R.id.box5);
        CheckBox box6 = (CheckBox) findViewById(R.id.box6);

        if (((CheckBox) v).isChecked()) {
            box1.setChecked(false);
            box2.setChecked(false);
            box3.setChecked(false);
            box4.setChecked(false);
            box5.setChecked(false);
            box6.setChecked(false);
        }
        ((CheckBox) v).setChecked(true);


    }

    //확인 버튼을 눌렀을 때
    public void setFinish(View v) {
        CheckBox box1 = (CheckBox) findViewById(R.id.box1);
        CheckBox box2 = (CheckBox) findViewById(R.id.box2);
        CheckBox box3 = (CheckBox) findViewById(R.id.box3);
        CheckBox box4 = (CheckBox) findViewById(R.id.box4);
        CheckBox box5 = (CheckBox) findViewById(R.id.box5);
        CheckBox box6 = (CheckBox) findViewById(R.id.box6);

        //현재 설정된 글자 크기를 저장
        EditText text_size = (EditText) findViewById(R.id.editTextSize);
        String textSize = text_size.getText().toString();

        SharedPreferences preferences = getSharedPreferences("text_set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("text_size", textSize);
        editor.commit();


        //현재 체크된 박스의 이름과, 박스가 가리키는 폰트의 위치를 저장
        if (box1.isChecked()) {
            editor.putString("checked_box", "box1");
            editor.putString("font_path","font/nanumgothic.otf");
        }
        else if (box2.isChecked()) {
            editor.putString("checked_box", "box2");
            editor.putString("font_path","font/maruburi.ttf");
        }
        else if (box3.isChecked()) {
            editor.putString("checked_box", "box3");
            editor.putString("font_path","font/tway_air.ttf");
        }
        else if (box4.isChecked()) {
            editor.putString("checked_box", "box4");
            editor.putString("font_path","font/ydestreetl.otf");
        }
        else if (box5.isChecked()) {
            editor.putString("checked_box", "box5");
            editor.putString("font_path","font/cookierun_regular.ttf");
        }
        else {
            editor.putString("checked_box", "box6");
            editor.putString("font_path","font/gowundodum_regular.ttf");
        }
        editor.commit();

        finish();
    }

}