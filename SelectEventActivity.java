package com.example.dfma_app_656995;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectEventActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_buttons);

        Button cowButton = findViewById(R.id.cow_bt);
        cowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cowOnButtonClick();
            }
        });
        Button milkButton = findViewById(R.id.milk_bt);
        milkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                milkOnButtonClick();
            }
        });
        Button healthButton = findViewById(R.id.health_bt);
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthOnButtonClick();
            }
        });
        Button btnSet=findViewById(R.id.remind_health);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remindOnButtonClick();
            }
        });
      //  Button btnSet=findViewById(R.id.remind_health);
        //btnSet.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
             //   remindOnButtonClick();
            }
        //});
    //}
//
    public void cowOnButtonClick()
    {
        Intent i=new Intent(this,CowRecordsActivity.class);
        startActivity(i);
    }
    public void milkOnButtonClick()
    {
        Intent i=new Intent(this,MilkRecordsActivity.class);
        startActivity(i);
    }
    public void healthOnButtonClick()
    {
        Intent i=new Intent(this,HealthRecordsActivity.class);
        startActivity(i);
    }

    private void remindOnButtonClick() {
        Intent i = new Intent(this, AlarmRecordsActivity.class);
        startActivity(i);
    }

}




