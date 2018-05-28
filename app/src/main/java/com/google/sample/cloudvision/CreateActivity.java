package com.google.sample.cloudvision;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Intent intent=getIntent();

        ImageView imageView_image=(ImageView)findViewById(R.id.image);
        byte[] arr = intent.getByteArrayExtra("image");
        final Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        imageView_image.setImageBitmap(bitmap);

        Button addFolder_animal=(Button)findViewById(R.id.btn_addFolder_animal);
        addFolder_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String dir=getFilesDir()+"/animal";
                FileOutputStream fos;
                try{
                   String fileName="test.png";
                   File file=new File(getFilesDir(),fileName);
                    if(!file.exists()){
                        file.createNewFile();
                        //fileName="test_2.png";
                        fos = openFileOutput(fileName, 0);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                        fos.flush();
                        fos.close();
                        Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_LONG).show();
                    }else{ //test.png가 존재하는 경우
                        fileName="test_2.png";
                        File file2=new File(getFilesDir(),fileName);
                        if(!file2.exists()){
                            file2.createNewFile();
                            fos = openFileOutput(fileName, 0);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                            fos.flush();
                            fos.close();
                            Toast.makeText(getApplicationContext(), "파일 저장 성공2", Toast.LENGTH_LONG).show();
                        }else{//test_2.png가 존재하는 경우
                            fileName="test_3.png";
                            File file3=new File(getFilesDir(),fileName);
                            if(!file3.exists()){
                                file3.createNewFile();
                                fos = openFileOutput(fileName, 0);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                                fos.flush();
                                fos.close();
                                Toast.makeText(getApplicationContext(), "파일 저장 성공3", Toast.LENGTH_LONG).show();
                            }else{//test_3.png가 존재하는 경우
                                fileName="test_4.png";
                                File file4=new File(getFilesDir(),fileName);
                                fos = openFileOutput(fileName, 0);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                                fos.flush();
                                fos.close();
                                Toast.makeText(getApplicationContext(), "파일 저장 성공4", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "파일 인식 실패", Toast.LENGTH_LONG).show();
                }finally {
                    AlertDialog.Builder builder=new AlertDialog.Builder(CreateActivity.this);
                    builder.setTitle("사진 분류 완료");
                    builder.setMessage("분류한 사진을 확인해보시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2=new Intent(getApplicationContext(),CheckActivity.class);
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

        Button addFolder_landscape=(Button)findViewById(R.id.btn_addFolder_landscape);
        addFolder_landscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fos;
                try{
                    String fileName="test2.png";
                    File file=new File(getFilesDir(),fileName);
                    if(!file.exists()){
                        file.createNewFile();
                        //fileName="test_2.png";
                        fos = openFileOutput(fileName, 0);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                        fos.flush();
                        fos.close();
                        Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_LONG).show();
                    }else{ //test.png가 존재하는 경우
                        fileName="test2_2.png";
                        File file2=new File(getFilesDir(),fileName);
                        if(!file2.exists()){
                            file2.createNewFile();
                            fos = openFileOutput(fileName, 0);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                            fos.flush();
                            fos.close();
                            Toast.makeText(getApplicationContext(), "파일 저장 성공2", Toast.LENGTH_LONG).show();
                        }else{//test_2.png가 존재하는 경우
                            fileName="test2_3.png";
                            File file3=new File(getFilesDir(),fileName);
                            if(!file3.exists()){
                                file3.createNewFile();
                                fos = openFileOutput(fileName, 0);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                                fos.flush();
                                fos.close();
                                Toast.makeText(getApplicationContext(), "파일 저장 성공3", Toast.LENGTH_LONG).show();
                            }else{//test_3.png가 존재하는 경우
                                fileName="test2_4.png";
                                File file4=new File(getFilesDir(),fileName);
                                fos = openFileOutput(fileName, 0);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                                fos.flush();
                                fos.close();
                                Toast.makeText(getApplicationContext(), "파일 저장 성공4", Toast.LENGTH_LONG).show();
                            }
                        }
                     }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "파일 인식 실패", Toast.LENGTH_LONG).show();
                }finally {
                    AlertDialog.Builder builder=new AlertDialog.Builder(CreateActivity.this);
                    builder.setTitle("사진 분류 완료");
                    builder.setMessage("분류한 사진을 확인해보시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2=new Intent(getApplicationContext(),CheckActivity.class);
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

        Button addFolder_profile=(Button)findViewById(R.id.btn_addFolder_profile);
        addFolder_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fos;
                try{
                    String fileName="test3.png";
                    File file=new File(getFilesDir(),fileName);
                    if(!file.exists()){
                        file.createNewFile();
                        //fileName="test_2.png";
                        fos = openFileOutput(fileName, 0);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                        fos.flush();
                        fos.close();
                        Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_LONG).show();
                    }else{ //test.png가 존재하는 경우
                        fileName="test3_2.png";
                        File file2=new File(getFilesDir(),fileName);
                        if(!file2.exists()){
                            file2.createNewFile();
                            fos = openFileOutput(fileName, 0);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                            fos.flush();
                            fos.close();
                            Toast.makeText(getApplicationContext(), "파일 저장 성공2", Toast.LENGTH_LONG).show();
                        }else{//test_2.png가 존재하는 경우
                            fileName="test3_3.png";
                            File file3=new File(getFilesDir(),fileName);
                            if(!file3.exists()){
                                file3.createNewFile();
                                fos = openFileOutput(fileName, 0);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                                fos.flush();
                                fos.close();
                                Toast.makeText(getApplicationContext(), "파일 저장 성공3", Toast.LENGTH_LONG).show();
                            }else{//test_3.png가 존재하는 경우
                                fileName="test3_4.png";
                                File file4=new File(getFilesDir(),fileName);
                                fos = openFileOutput(fileName, 0);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                                fos.flush();
                                fos.close();
                                Toast.makeText(getApplicationContext(), "파일 저장 성공4", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "파일 인식 실패", Toast.LENGTH_LONG).show();
                }finally {
                    AlertDialog.Builder builder=new AlertDialog.Builder(CreateActivity.this);
                    builder.setTitle("사진 분류 완료");
                    builder.setMessage("분류한 사진을 확인해보시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2=new Intent(getApplicationContext(),CheckActivity.class);
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

        Button addFolder_word=(Button)findViewById(R.id.btn_addFolder_word);
        addFolder_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fos;
                try{
                    String fileName="test4.png";
                    File file=new File(getFilesDir(),fileName);
                    if(!file.exists()){
                        file.createNewFile();
                        //fileName="test_2.png";
                        fos = openFileOutput(fileName, 0);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                        fos.flush();
                        fos.close();
                        Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_LONG).show();
                    }else{ //test.png가 존재하는 경우
                        fileName="test4_2.png";
                        File file2=new File(getFilesDir(),fileName);
                        if(!file2.exists()){
                            file2.createNewFile();
                            fos = openFileOutput(fileName, 0);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                            fos.flush();
                            fos.close();
                            Toast.makeText(getApplicationContext(), "파일 저장 성공2", Toast.LENGTH_LONG).show();
                        }else{//test_2.png가 존재하는 경우
                            fileName="test4_3.png";
                            File file3=new File(getFilesDir(),fileName);
                            if(!file3.exists()){
                                file3.createNewFile();
                                fos = openFileOutput(fileName, 0);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                                fos.flush();
                                fos.close();
                                Toast.makeText(getApplicationContext(), "파일 저장 성공3", Toast.LENGTH_LONG).show();
                            }else{//test_3.png가 존재하는 경우
                                fileName="test4_4.png";
                                File file4=new File(getFilesDir(),fileName);
                                fos = openFileOutput(fileName, 0);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                                fos.flush();
                                fos.close();
                                Toast.makeText(getApplicationContext(), "파일 저장 성공4", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "파일 인식 실패", Toast.LENGTH_LONG).show();
                }finally {
                    AlertDialog.Builder builder=new AlertDialog.Builder(CreateActivity.this);
                    builder.setTitle("사진 분류 완료");
                    builder.setMessage("분류한 사진을 확인해보시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2=new Intent(getApplicationContext(),CheckActivity.class);
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
