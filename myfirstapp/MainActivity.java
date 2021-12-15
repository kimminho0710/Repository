package com.example.myfirstapp;
//package com.example.user.intent;

import java.io.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.nio.file.*;
import java.lang.String;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //다른 스크립트에 전달할 변수를 static으로 선언
    static String data;
    static String path;

    //스크립트 시작 시 실행할 명령문
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //시작 시 명령 실행, 스크립트와 xml 연결
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //현재 파일 위치(주소) 불러오고 주소창에 띄워주기
        File fileFile = getFilesDir();
        path = fileFile.getPath();

        TextView address = (TextView) findViewById(R.id.address);
        address.setText(path);

        //현재 파일내의 파일 목록 이름 불러오기
        File f = new File(path);
        File[] nameList = f.listFiles();

        //파일 이름을 통해 각 파일의 주소을 특정하고, 파일 정보를 함께 불러와 리스트로 저장하기
        List<fileInfo> fileInfoList = new ArrayList<>();

        try {
            for (int i = 0; i < nameList.length; i++) {
                Path checkPath = Paths.get(path, nameList[i].getName());
                BasicFileAttributes readAttributes = Files.readAttributes(checkPath, BasicFileAttributes.class);
                fileInfoList.add(new fileInfo(nameList[i].getName(), readAttributes.creationTime().toMillis(), readAttributes.lastModifiedTime().toMillis(), Files.size(checkPath)));

            }
        } catch (IOException ie) {
            address.setText("Attributes Reading Error!");
        }


        //정해진 정렬 방식에 따라 파일리스트 정렬하기
        Collections.sort(fileInfoList, new fileInfo());

        //정렬된 파일 리스트에서 이름 정보만 불러와 새로 이름 목록에 저장하기
        List<String> filesNameList = new ArrayList<>();
        for (int i = 0; i < nameList.length; i++) {
            filesNameList.add(fileInfoList.get(i).name);
        }

        //이름 목록을 ArrayList와 연결하여 사용자에게 보여주기
        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filesNameList);

        ListView list = (ListView) findViewById(R.id.list);

        list.setAdapter(Adapter);

        //리스트파일 클릭 시, 클릭된 개체를 통해 파일 주소를 불러오고 텍스트파일 로드 화면으로 넘기기
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                data = (String) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
            }
        });

        //리스트파일 롱클릭시, 롱클릭된 개체를 통해 파일 주소를 불러오고, 주소가 가리키는 객체의 정보를 팝업으로 띄워주기
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                data = (String) adapterView.getItemAtPosition(position);
                String check_path = path + '/' + data;
                Intent intent = new Intent(getBaseContext(), PopupActivity.class);
                intent.putExtra("path", check_path);
                startActivity(intent);
                return true;
            }
        });
    }

    //다른 페이지에 갔다가 돌아왔을 때 실행할 명령어
    //파일 목록을 정렬 기준에 맞춰 재정렬하고, 변경된 ArrayList 순서를 출력
    @Override
    public void onResume() {
        super.onResume();

        File fileFile = getFilesDir();
        path = fileFile.getPath();

        TextView address = (TextView) findViewById(R.id.address);
        address.setText(path);

        File f = new File(path);
        File[] nameList = f.listFiles();

        List<fileInfo> fileInfoList = new ArrayList<>();

        try {
            for (int i = 0; i < nameList.length; i++) {
                Path checkPath = Paths.get(path, nameList[i].getName());
                BasicFileAttributes readAttributes = Files.readAttributes(checkPath, BasicFileAttributes.class);
                fileInfoList.add(new fileInfo(nameList[i].getName(), readAttributes.creationTime().toMillis(), readAttributes.lastModifiedTime().toMillis(), Files.size(checkPath)));
            }
        } catch (IOException ie) {
            address.setText("Attributes Reading Error!");
        }

        Collections.sort(fileInfoList, new fileInfo());

        List<String> filesNameList = new ArrayList<>();
        for (int i = 0; i < nameList.length; i++) {
            filesNameList.add(fileInfoList.get(i).name);
        }

        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filesNameList);

        ListView list = (ListView) findViewById(R.id.list);

        list.setAdapter(Adapter);
    }

    //다른 화면으로 넘어갔을 때 퍼즈시키기
    public void onPause() {
        super.onPause();
    }


    //ArrayList에 넣을, 파일에서 불러올 정보들을 하나로 모아둔 객체 fileInfo 정의, 객체 비교 가능하도록 Comparator 선언
    public class fileInfo implements Comparator<fileInfo> {
        //파일 이름, 파일 생성일자, 파일 수정일자, 파일 크기를 각각 변수로 정의
        String name;
        long makeDay, changeDay, fileVolume;

        //초기화
        public fileInfo() {

        }

        //객체 생성시, 변수 설정은 객체 초기화 시에 받은 값으로 저장
        public fileInfo(String name, long makeDay, long changeDay, long fileVolume) {
            this.name = name;
            this.makeDay = makeDay;
            this.changeDay = changeDay;
            this.fileVolume = fileVolume;
        }

        //객체끼리 비교하여 정렬할 수 있도록 compare 함수 정의
        @Override
        public int compare(fileInfo f1, fileInfo f2) {
            //sort_num은 정렬 기준을 나타내는 변수. SharedPreferences 객체를 이용해 파일에 저장된 값을 불러오고, 값이 없으면 가나다 정순으로 정렬
            int sort_num;
            SharedPreferences preferences = getSharedPreferences("array_set", Context.MODE_PRIVATE);
            sort_num = preferences.getInt("sort_num", 0);

            //가나다 정순, 가나다 역순일 경우는 comPareTo의 값을 이용하여 결과 반환
            if (sort_num == 0) {
                return f1.name.compareTo(f2.name);
            } else if (sort_num == 1) {
                return f2.name.compareTo(f1.name);
            }

            //그 외의 생성날짜, 수정날짜, 크기를 비교하는 경우는 if문을 이용하여 원하는 정렬기준에 맞게 결과 반환
            //생성날짜 비교
            else if (sort_num == 2) {
                if (f1.makeDay < f2.makeDay) {
                    return 1;
                } else if (f1.makeDay == f2.makeDay) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (sort_num == 3) {
                if (f1.makeDay < f2.makeDay) {
                    return -1;
                } else if (f1.makeDay == f2.makeDay) {
                    return 0;
                } else {
                    return 1;
                }
            }
            //수정날짜 비교
            else if (sort_num == 4) {
                if (f1.changeDay < f2.changeDay) {
                    return 1;
                } else if (f1.changeDay == f2.changeDay) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (sort_num == 5) {
                if (f1.changeDay < f2.changeDay) {
                    return -1;
                } else if (f1.changeDay == f2.changeDay) {
                    return 0;
                } else {
                    return 1;
                }
            }
            //파일크기 비교
            else if (sort_num == 6) {
                if (f1.fileVolume < f2.fileVolume) {
                    return 1;
                } else if (f1.fileVolume == f2.fileVolume) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (sort_num == 7) {
                if (f1.fileVolume < f2.fileVolume) {
                    return -1;
                } else if (f1.fileVolume == f2.fileVolume) {
                    return 0;
                } else {
                    return 1;
                }
            }
            return 0;
        }
    }

    //get_data 함수를 정의하여 다른 스크립트(SubActivity)에서도 data값을 가져올 수 있도록 선언
    public String get_data() {
        data = path + '/' + data;
        return data;
    }

    //설정을 클릭하면 설정화면으로 넘어갈 수 있도록
    public void setOnClick(View v) {
        Intent intent = new Intent(this, SetActivity.class);
        startActivity(intent);
    }

    //텍스트설정을 클릭하면 텍스트 설정으로 넘어갈 수 있도록
    public void textSetOnClick(View v) {
        Intent intent = new Intent(this, text_setActivity.class);
        startActivity(intent);
    }

    //정렬설정을 클릭하면 정렬설정으로 넘어갈 수 있도록
    public void arraySetOnClick(View v) {
        Intent intent = new Intent(this, array_setActivity.class);
        startActivity(intent);
    }

    //새로고침을 클릭하면 파일주소에서 다시 파일을 받아와 리스트 재정렬 후 유저에게 보여줄 수 있도록
    //주로 새로운 파일을 다운받은 후, 앱 재실행 없이 새로운 파일도 리스트에 적용할 수 있도록 만든 버튼
    public void refresh(View v) {
        File fileFile = getFilesDir();
        path = fileFile.getPath();

        TextView address = (TextView) findViewById(R.id.address);
        address.setText(path);

        File f = new File(path);
        File[] nameList = f.listFiles();

        List<fileInfo> fileInfoList = new ArrayList<>();

        try {
            for (int i = 0; i < nameList.length; i++) {
                Path checkPath = Paths.get(path, nameList[i].getName());
                BasicFileAttributes readAttributes = Files.readAttributes(checkPath, BasicFileAttributes.class);
                fileInfoList.add(new fileInfo(nameList[i].getName(), readAttributes.creationTime().toMillis(), readAttributes.lastModifiedTime().toMillis(), Files.size(checkPath)));
            }
        } catch (IOException ie) {
            address.setText("Attributes Reading Error!");
        }

        Collections.sort(fileInfoList, new fileInfo());

        List<String> filesNameList = new ArrayList<>();
        for (int i = 0; i < nameList.length; i++) {
            filesNameList.add(fileInfoList.get(i).name);
        }

        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filesNameList);

        ListView list = (ListView) findViewById(R.id.list);

        list.setAdapter(Adapter);

    }


}