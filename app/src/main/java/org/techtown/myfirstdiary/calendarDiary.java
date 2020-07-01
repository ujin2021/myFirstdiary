package org.techtown.myfirstdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.util.Calendar;

public class calendarDiary extends AppCompatActivity {
    EditText edtDiary;   // 일기쓰기, 기존의 일기 띄워주기
    Button btnSave;   // 일기 저장, 수정버튼
    TextView date;
    Button calendar;
    String fileName;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_diary);
        setTitle("My Calendar Diary");

        edtDiary = findViewById(R.id.edtDiary);
        btnSave = findViewById(R.id.btnSave);
        date = findViewById(R.id.date);
        calendar = findViewById(R.id.calendar);

        Intent intent = getIntent();
        processIntent(intent);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary(fileName);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 다시 뒤의 화면으로 돌아감
            }
        });
    }

    private void processIntent(Intent intent){
        if(intent != null){
            data = intent.getStringExtra("data");
            date.setText(data+" diary");
            Log.d("data", "data= "+data);
            if(intent != null){
                checkedDay(data);
            }
        }
    }

    // 일기 파일 읽기
    private void checkedDay(String day) {
        String[] dateArr = day.split("/");
        int year = Integer.parseInt(dateArr[0]);
        int monthOfYear = Integer.parseInt(dateArr[1]);
        int dayOfMonth = Integer.parseInt(dateArr[2]);

        fileName = year + "" + monthOfYear + "" + dayOfMonth + ".txt";

        // 읽어봐서 읽어지면 일기 가져오고
        // 없으면 catch로 처리
        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            String str = new String(fileData, "EUC-KR");
            // 읽어서 토스트 메시지로 보여줌
            Toast.makeText(getApplicationContext(), "일기 써둔 날", Toast.LENGTH_SHORT).show();
            edtDiary.setText(str);
            btnSave.setText("수정하기"); // 일기 써둔게 있다면 수정하기로 버튼이 바뀐다.
        }
        catch (Exception e) {
            // 없어서 오류가 나면 일기가 없는 것. 일기 새로 씀
            Toast.makeText(getApplicationContext(), "일기 없는 날", Toast.LENGTH_SHORT).show();
            edtDiary.setText("");
            btnSave.setText("새 일기 저장");
            e.printStackTrace();
        }

    }

    // 일기 저장
    private void saveDiary(String readDay) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(readDay, Context.MODE_PRIVATE);
            String content = edtDiary.getText().toString();

            fos.write(content.getBytes());
            fos.close();

            Toast.makeText(getApplicationContext(), "일기 저장됨", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류오류", Toast.LENGTH_SHORT).show();
        }
    }

}