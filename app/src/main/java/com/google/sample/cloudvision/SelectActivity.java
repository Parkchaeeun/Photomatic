package com.google.sample.cloudvision;

import android.content.DialogInterface;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Intent intent=getIntent();
        byte[] arr = getIntent().getByteArrayExtra("image");
        final String label=getIntent().getStringExtra("label");
        final Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        Button btn_animal=(Button)findViewById(R.id.btn_animal);
        btn_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!label.equals("동물")){
                    Toast.makeText(getApplicationContext(), "기준을 재선택 하시오", Toast.LENGTH_LONG).show();
                    return;
                }else if (label.equals("동물")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(SelectActivity.this);
                    builder.setTitle("동물 기준 선택 ");
                    builder.setMessage("선택한 사진을 '동물'로 분류하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2 = new Intent(getApplicationContext(), CreateActivity.class);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            String label2="동물";
                            byte[] byteArray = stream.toByteArray();
                            intent2.putExtra("image", byteArray);
                            startActivity(intent2);
                        }
                    });
                    builder.create().show();
                }
            }
        });
        Button btn_landscape=(Button)findViewById(R.id.btn_landscape);
        btn_landscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!label.equals("풍경")){
                    Toast.makeText(getApplicationContext(), "기준을 재선택 하시오", Toast.LENGTH_LONG).show();
                    return;
                }else if(label.equals("풍경")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(SelectActivity.this);
                    builder.setTitle("풍경 기준 선택 ");
                    builder.setMessage("선택한 사진을 '풍경'으로 분류하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2=new Intent(getApplicationContext(),CreateActivity.class);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            intent2.putExtra("image",byteArray);
                            startActivity(intent2);
                        }
                    });
                    builder.create().show();
                }
            }
        });
        Button btn_profile=(Button)findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!label.equals("인물")){
                    Toast.makeText(getApplicationContext(), "기준을 재선택 하시오", Toast.LENGTH_LONG).show();
                    return;
                }else if(label.equals("인물")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(SelectActivity.this);
                    builder.setTitle("인물 기준 선택 ");
                    builder.setMessage("선택한 사진을 '인물'로 분류하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2=new Intent(getApplicationContext(),CreateActivity.class);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            intent2.putExtra("image",byteArray);
                            startActivity(intent2);
                        }
                    });
                    builder.create().show();
                }
            }
        });
        Button btn_word=(Button)findViewById(R.id.btn_word);
        btn_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!label.equals("문자")){
                    Toast.makeText(getApplicationContext(), "기준을 재선택 하시오", Toast.LENGTH_LONG).show();
                    return;
                }else if(label.equals("문자")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(SelectActivity.this);
                    builder.setTitle("문자 기준 선택 ");
                    builder.setMessage("선택한 사진을 '문자'로 분류하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2=new Intent(getApplicationContext(),CreateActivity.class);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            intent2.putExtra("image",byteArray);
                            startActivity(intent2);
                        }
                    });
                    builder.create().show();
                }
            }
        });
    }
}
