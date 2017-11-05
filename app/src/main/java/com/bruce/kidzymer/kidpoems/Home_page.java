package com.bruce.kidzymer.kidpoems;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home_page extends AppCompatActivity {
    Button english, telugu, hindi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        english = (Button) findViewById(R.id.engbutton);
        telugu = (Button) findViewById(R.id.telbutton);
        hindi = (Button) findViewById(R.id.hinbutton);


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home_page.this, Poem_names.class);

                i.putExtra("language", "english");
                startActivity(i);

            }

        });
        telugu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_page.this, Poem_names.class);

                i.putExtra("language", "telugu");
                startActivity(i);
            }
        });
        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_page.this, Poem_names.class);
                i.putExtra("language", "hindi");
                startActivity(i);
            }
        });

    }

}




