package com.example.myfirstapp;

import java.io.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    //실행 시작 시 명령어
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //get_data 함수를 이용해 현재 접근하려고 하는 파일의 주소를 받아옴
        String path = new MainActivity().get_data();

        //텍스트파일 내의 글을 화면에 보여줄 수 있도록 ReadTextFile 함수를 선언하고 사용
        TextView text_read = (TextView) findViewById(R.id.text_read);

        String read = ReadTextFile(path);
        text_read.setText(read);

        //스크롤 형식으로 글 볼 수 있도록
        text_read.setMovementMethod(new ScrollingMovementMethod());


        //텍스트파일 보기 설정을 파일에서 불러와서 이전 설정대로 볼 수 있게 한다. 디폴트는 나눔고딕, 25사이즈로.
        SharedPreferences preferences = getSharedPreferences("text_set", Context.MODE_PRIVATE);
        String text_sizeRead = preferences.getString("text_size", "25");
        int text_size = Integer.parseInt(text_sizeRead);
        String font_path = preferences.getString("font_path", "font/nanumgothic.otf");


        text_read.setTextSize(text_size);
        Typeface typeface = Typeface.createFromAsset(getAssets(), font_path);
        text_read.setTypeface(typeface);

    }

    //파일로 돌아왔을 때, 설정에서 글꼴, 글씨 크기가 변경되었을 경우를 생각하여 변수들을 다시 불러와 적용시킨다.
    @Override
    public void onResume() {
        super.onResume();

        TextView text_read = (TextView) findViewById(R.id.text_read);

        SharedPreferences preferences = getSharedPreferences("text_set", Context.MODE_PRIVATE);
        String font_path = preferences.getString("font_path", "font/nanumgothic.otf");
        String text_sizeRead = preferences.getString("text_size", "25");
        int text_size = Integer.parseInt(text_sizeRead);

        text_read.setTextSize(text_size);
        Typeface typeface = Typeface.createFromAsset(getAssets(), font_path);
        text_read.setTypeface(typeface);
    }

    //다른 파일로 넘어갔을 경우 퍼즈
    public void onPause() {
        super.onPause();
    }


    //텍스트파일 내의 글을 읽어오는 함수
    public String ReadTextFile (String path){
        //스트링버퍼 이용하여 받은 글 내용을 하나의 스트링으로 만들 수 있도록 한다.
        StringBuffer strBuffer = new StringBuffer();
        try {
            //받고자 하는 글의 주소로 inputstream 값 정의
            InputStream is = new FileInputStream(path);

            //reader에 인풋스트림 값을 넣어 한 줄씩 입력을 받음
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";

            //이를 계속 반복하여 line에 한 줄씩 글이 쌓이는 동시에, 받는 문장별로 줄바꿈을 하여 가독성을 높여서 글 받을 수 있도록 설정
            //라인별로 버퍼에 계속 쌓아서 글 전체 내용 입력
            while ((line = reader.readLine()) != null) {
                strBuffer.append(line + "\n");
            }

            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        //완성된 글을 스트링 형태로 반환
        return strBuffer.toString();
    }

    //설정 화면으로
    public void setOnClick(View v) {
        Intent intent = new Intent(this, SetActivity.class);
        startActivity(intent);
    }

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