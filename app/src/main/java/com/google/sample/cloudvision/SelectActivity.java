package com.google.sample.cloudvision;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class SelectActivity extends AppCompatActivity {

    Button btn_animal,btn_landscape,btn_profile,btn_word,btn_food,btn_art;
    String cate;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6;
    String label1,label2,label3,label4,label5,label6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        btn_food=(Button)findViewById(R.id.btn_food);
        btn_art=(Button)findViewById(R.id.btn_art);
        btn_animal=(Button)findViewById(R.id.btn_animal);
        btn_landscape=(Button)findViewById(R.id.btn_landscape);
        btn_profile=(Button)findViewById(R.id.btn_profile);
        btn_word=(Button)findViewById(R.id.btn_word);

        pref=getSharedPreferences("pref", Context.MODE_PRIVATE);


        if(getIntent().getByteArrayExtra("image1")!=null){
            byte[] arr = getIntent().getByteArrayExtra("image1");
            bitmap1 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            label1=pref.getString("label1","");
        }

        if(getIntent().getByteArrayExtra("image2")!=null){
            byte[] arr = getIntent().getByteArrayExtra("image2");
            bitmap2 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
           label2=pref.getString("label2","");
        }

        if(getIntent().getByteArrayExtra("image3")!=null){
            byte[] arr = getIntent().getByteArrayExtra("image3");
            bitmap3 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            label3=pref.getString("label3","");
        }

        if(getIntent().getByteArrayExtra("image4")!=null){
            byte[] arr = getIntent().getByteArrayExtra("image4");
            bitmap4 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
           label4=pref.getString("label4","");
        }

        if(getIntent().getByteArrayExtra("image5")!=null){
            byte[] arr = getIntent().getByteArrayExtra("image5");
            bitmap5 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            label5=pref.getString("label5","");
        }

        if(getIntent().getByteArrayExtra("image6")!=null){
            byte[] arr = getIntent().getByteArrayExtra("image6");
            bitmap6 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            label6=pref.getString("label6","");
        }

        btn_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                cate="animal";
                editor=pref.edit();
                editor.putString("cate",cate);
                editor.commit();
                if(label1!=null){
                    if(label1.equals("animal")){
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image1",byteArray);
                    }
                }
                if(label2!=null){
                    if(label2.equals("animal")){
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image2",byteArray);
                    }
                }
                if(label3!=null){
                    if(label3.equals("animal")){
                        //  Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image3",byteArray);
                    }
                }

                if(label4!=null){
                    if(label4.equals("animal")){
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image4",byteArray);
                    }
                }
                if(label5!=null){
                    if(label5.equals("animal")){
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image5",byteArray);
                    }
                }
                if(label6!=null){
                    if(label6.equals("animal")){
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image6",byteArray);
                    }
                }
                startActivity(intent);
            }
        });

        //풍경 기준 선택 시
        btn_landscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cate="landscape";
                editor=pref.edit();
                editor.putString("cate",cate);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), AddFileActivity.class);

                if(label1!=null){
                    if (label1.equals("landscape")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image1", byteArray);
                    }
                }
                if(label2!=null){
                    if (label2.equals("landscape")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image2", byteArray);
                    }
                }
                if(label3!=null){
                    if (label3.equals("landscape")) {
                        //  Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image3", byteArray);
                    }
                }
                if(label4!=null){
                    if (label4.equals("landscape")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image4", byteArray);
                    }
                }
                if(label5!=null){
                    if (label5.equals("landscape")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image5", byteArray);
                    }
                }
                if(label6!=null){
                    if (label6.equals("landscape")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image6", byteArray);
                    }
                }
                startActivity(intent);
            }
        });

        btn_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cate="text";
                editor=pref.edit();
                editor.putString("cate",cate);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), AddFileActivity.class);

                if(label1!=null){
                    if (label1.equals("text")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image1", byteArray);
                    }
                }
                if(label2!=null){
                    if (label2.equals("text")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image2", byteArray);
                    }
                }
               if(label3!=null){
                   if (label3.equals("text")) {
                       //  Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                       ByteArrayOutputStream stream = new ByteArrayOutputStream();
                       bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                       byte[] byteArray = stream.toByteArray();
                       intent.putExtra("image3", byteArray);
                   }
               }
                if(label4!=null){
                    if (label4.equals("text")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image4", byteArray);
                    }
                }

                if(label5!=null){
                    if (label5.equals("text")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image5", byteArray);
                    }
                }
                if(label6!=null){
                    if (label6.equals("text")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image6", byteArray);
                    }
                }
                startActivity(intent);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cate="human";
                editor=pref.edit();
                editor.putString("cate",cate);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), AddFileActivity.class);

                if(label1!=null){
                    if (label1.equals("human")) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream); //nullpointerexception 발견
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image1", byteArray);
                    }
                }
                if(label2!=null){
                    if (label2.equals("human")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image2", byteArray);
                    }
                }
                if(label3!=null){
                    if (label3.equals("human")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image3", byteArray);
                    }
                }
               if(label4!=null){
                   if (label4.equals("human")) {
                       // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                       ByteArrayOutputStream stream = new ByteArrayOutputStream();
                       bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                       byte[] byteArray = stream.toByteArray();
                       intent.putExtra("image4", byteArray);
                   }
               }
                if(label5!=null){
                    if (label5.equals("human")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image5", byteArray);
                    }
                }

                if(label6!=null){
                    if(label6.equals("human")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image6", byteArray);
                    }
                }

                startActivity(intent);
            }
        });

        btn_art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cate="art";
                editor=pref.edit();
                editor.putString("cate",cate);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), AddFileActivity.class);

                if(label1!=null){
                    if (label1.equals("art")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image1", byteArray);
                    }
                }

                if(label2!=null){
                    if (label2.equals("art")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image2", byteArray);
                    }
                }

                if(label3!=null){
                    if (label3.equals("art")) {
                        //  Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image3", byteArray);
                    }
                }
                if(label4!=null){
                    if (label4.equals("art")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image4", byteArray);
                    }
                }
                if(label5!=null){
                    if (label5.equals("art")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image5", byteArray);
                    }
                }
                if(label6!=null){
                    if (label6.equals("art")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image6", byteArray);
                    }
                }

                startActivity(intent);

            }
        });

        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cate="food";
                editor=pref.edit();
                editor.putString("cate",cate);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), AddFileActivity.class);

                if(label1!=null){
                    if (label1.equals("food")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image1", byteArray);
                    }
                }

                if(label2!=null){
                    if (label2.equals("food")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image2", byteArray);
                    }
                }
               if(label3!=null){
                   if (label3.equals("food")) {
                       //  Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                       ByteArrayOutputStream stream = new ByteArrayOutputStream();
                       bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                       byte[] byteArray = stream.toByteArray();
                       intent.putExtra("image3", byteArray);
                   }
               }
               if(label4!=null){
                   if (label4.equals("food")) {
                       // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                       ByteArrayOutputStream stream = new ByteArrayOutputStream();
                       bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                       byte[] byteArray = stream.toByteArray();
                       intent.putExtra("image4", byteArray);
                   }
               }
                if(label5!=null){
                    if (label5.equals("food")) {
                        // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image5", byteArray);
                    }
                }
               if(label6!=null){
                   if (label6.equals("food")) {
                       // Intent intent=new Intent(getApplicationContext(),AddFileActivity.class);
                       ByteArrayOutputStream stream = new ByteArrayOutputStream();
                       bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                       byte[] byteArray = stream.toByteArray();
                       intent.putExtra("image6", byteArray);
                   }
               }
                startActivity(intent);

            }
        });
    }
}
