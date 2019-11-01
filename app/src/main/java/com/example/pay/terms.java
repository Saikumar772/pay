package com.example.pay;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AutomaticZenRule;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class terms extends AppCompatActivity {
    RadioButton rb1;
    Button a1;
    private Object LayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        a1= findViewById(R.id.a1);
    }


    public void onRadioButtonClicked(View view) {

        a1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent sa = new Intent(terms.this, MainActivity.class);
                startActivity(sa);
            }
        });
    }
}
