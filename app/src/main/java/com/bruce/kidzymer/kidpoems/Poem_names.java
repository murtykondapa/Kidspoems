package com.bruce.kidzymer.kidpoems;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Poem_names extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    String[] name;
    private String languge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_names);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        Intent intent = getIntent();
        if (intent!=null){
            languge = intent.getStringExtra("language");
        }
            adapter=new PoemsAdapter(this,getPoems(languge),languge);

        //setting the adapter
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Poem> getPoems(String languge){

        ArrayList<Poem> poemArrayList = new ArrayList<Poem>();

        //getting poems from values folder- strings.xml

        String[] poems=null;

        if (languge.equalsIgnoreCase("english")){
            poems = getResources().getStringArray(R.array.english_poem_name);

        }
        else if(languge.equalsIgnoreCase("telugu")){
            poems = getResources().getStringArray(R.array.telugu_poem_name);
        }
        else if(languge.equalsIgnoreCase("hindi")){
            //hindi
            poems = getResources().getStringArray(R.array.hindi_poem_name);
        }

        for (int i=0;i<poems.length;i++){

            poemArrayList.add(new Poem(poems[i]));
        }

        return poemArrayList;
    }

}


