package com.google.sample.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectFolderActivity extends AppCompatActivity {

    String group,email;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_folder);

        //Intent intent=getIntent();
        pref=getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor=pref.edit();
        email=pref.getString("email","");
        //name=intent.getStringExtra("name");

        Button btnAnimal=(Button)findViewById(R.id.btnAnimal);
        Button btnFood=(Button)findViewById(R.id.btnFood);
        Button btnProfile=(Button)findViewById(R.id.btnProfile);
        Button btnPicture=(Button)findViewById(R.id.btnPicture);
        Button btnWord=(Button)findViewById(R.id.btnWord);
        Button btnLandscape=(Button)findViewById(R.id.btnLandscape);

        //동물 분류 기준
        btnAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group="animal";
                Intent intent=new Intent(getApplicationContext(),PopupActivity.class);

                //SharedPreferences.Editor editor=pref.edit();
                editor.putString("group",group);
                editor.commit();
              //  intent.putExtra("group",group);
               // intent.putExtra("email",email);
                //intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        //음식 분류기준
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group="food";
                Intent intent=new Intent(getApplicationContext(),PopupActivity.class);

                editor.putString("group",group);
                editor.commit();
                //intent.putExtra("group",group);
               // intent.putExtra("email",email);
               // intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        //인물분류 기준
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group="human";
                Intent intent=new Intent(getApplicationContext(),PopupActivity.class);
                intent.putExtra("group",group);
                intent.putExtra("email",email);
                //intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        //풍경 분류 기준
        btnLandscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group="landscape";
                Intent intent=new Intent(getApplicationContext(),PopupActivity.class);

                editor.putString("group",group);
                editor.commit();
              //  intent.putExtra("group",group);
            //    intent.putExtra("email",email);
                //intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        //그림 분류 기준
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group="picture";
                Intent intent=new Intent(getApplicationContext(),PopupActivity.class);
                intent.putExtra("group",group);
                intent.putExtra("email",email);
                //intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        //문자 분류 기준
        btnWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group="text";
                Intent intent=new Intent(getApplicationContext(),PopupActivity.class);
                intent.putExtra("group",group);
                intent.putExtra("email",email);
                //intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }
}
