package com.google.sample.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class FolderViewActivity extends AppCompatActivity {

    Button btn_anim,btn_foo,btn_prof,btn_lands,btn_wor,btn_pict;

    String cate;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_view);
        pref=getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor=pref.edit();

        btn_anim=(Button)findViewById(R.id.btn_anim);
        btn_foo=(Button)findViewById(R.id.btn_foo);
        btn_prof=(Button)findViewById(R.id.btn_prof);
        btn_lands=(Button)findViewById(R.id.btn_lands);
        btn_pict=(Button)findViewById(R.id.btn_pict);
        btn_wor=(Button)findViewById(R.id.btn_wor);

        btn_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cate="animal";
                editor.putString("cate",cate);
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),FolderViewAnimalActivity.class);
                startActivity(intent);
            }
        });

        btn_wor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent();
                cate="text";
                editor.putString("cate",cate);
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),FolderViewWordActivity.class);
                startActivity(intent);

            }
        });

        btn_lands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cate="landscape";
                editor.putString("cate",cate);
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),FolderViewLandscapeActivity.class);
                startActivity(intent);
            }
        });

        btn_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cate="human";
                editor.putString("cate",cate);
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),FolderViewProfileActivity.class);
                startActivity(intent);
            }
        });

        btn_foo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cate="food";
                editor.putString("cate",cate);
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),FolderViewFoodActivity.class);
                startActivity(intent);
            }
        });

        btn_pict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cate="art";
                editor.putString("cate",cate);
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),FolderViewPictureActivity.class);
                startActivity(intent);
            }
        });

    }
}
