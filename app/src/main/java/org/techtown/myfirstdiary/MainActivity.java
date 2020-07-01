package org.techtown.myfirstdiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatePicker datePicker;  //  날짜를 선택하는 달력
    TextView viewDatePick;  //  선택한 날짜를 보여주는 textView
    String data; // intent로 넘겨줄 값
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My Calendar Diary");

        datePicker = findViewById(R.id.datePicker);
        viewDatePick = findViewById(R.id.viewDatePick);

        // 오늘 날짜 받음
        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH) + 1;
        int cDay = c.get(Calendar.DAY_OF_MONTH);
        data = cYear + "/" + cMonth + "/" + cDay;
        Log.d("data", "data1 = "+data);
        viewDatePick.setText(cYear + "/" + cMonth + "/" + cDay+"의 일기 쓰기"); // textView에 오늘 날짜를 띄워준다.

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear =  monthOfYear + 1;
                data = year + "/" + monthOfYear + "/" + dayOfMonth;
                Log.d("data", "data2 = "+data);
                viewDatePick.setText(year + "/" + monthOfYear + "/" + dayOfMonth+"의 일기 쓰기"); // textView에 오늘 날짜를 띄워준다.
            }
        });

        ImageButton button = findViewById(R.id.writeButton); // 연필모양 이미지버튼

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendarDiary.class);
                Log.d("data", "send data = "+data);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
    }
}
