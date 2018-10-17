package com.google.sample.cloudvision;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


//분류 기준에 맞는 이미지만 보이는 액티비티
public class AddFileActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch (id){
            case R.id.menu_logout:

                editor=pref.edit();
                editor.clear();
                editor.commit();

                Toast.makeText(this,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_home:

                editor=pref.edit();
                editor.remove("cate");
                editor.commit();

                editor=pref.edit();
                editor.remove("fname");
                editor.commit();

                editor=pref.edit();
                editor.remove("group");
                editor.commit();

                Toast.makeText(this,"홈 화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static String TAG = "phptest_addFileActivity";
    String email,cate;
    ImageView iv1,iv2,iv3,iv4,iv5,iv6;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6;
    String label1,label2,label3,label4,label5,label6;
    Button  btn_addFile;
    ArrayList<Bitmap> bits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);

        bits=new ArrayList<Bitmap>();

        iv1 = (ImageView) findViewById(R.id.add_file_iv1);
        iv2 = (ImageView) findViewById(R.id.add_file_iv2);
        iv3 = (ImageView) findViewById(R.id.add_file_iv3);
        iv4 = (ImageView) findViewById(R.id.add_file_iv4);
        iv5 = (ImageView) findViewById(R.id.add_file_iv5);
        iv6 = (ImageView) findViewById(R.id.add_file_iv6);

        btn_addFile=(Button)findViewById(R.id.btn_addFile);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        email = pref.getString("email", ""); //이메일
        cate = pref.getString("cate", "");  //카테고리 값

        if (getIntent().getByteArrayExtra("image1") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image1");
            bitmap1 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            bits.add(bitmap1);

            label1 = pref.getString("label1", "");
        }

        if (getIntent().getByteArrayExtra("image2") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image2");
            bitmap2 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            bits.add(bitmap2);
            label2 = pref.getString("label2", "");
        }

        if (getIntent().getByteArrayExtra("image3") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image3");
            bitmap3 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            bits.add(bitmap3);
            label3 = pref.getString("label3", "");
        }

        if (getIntent().getByteArrayExtra("image4") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image4");
            bitmap4 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            bits.add(bitmap4);
            label4 = pref.getString("label4", "");
        }

        if (getIntent().getByteArrayExtra("image5") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image5");
            bitmap5 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            bits.add(bitmap5);
            label5 = pref.getString("label5", "");
        }

        if (getIntent().getByteArrayExtra("image6") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image6");
            bitmap6 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            bits.add(bitmap6);
            label6 = pref.getString("label6", "");
        }

        //이미지뷰에 비트맵 뿌려주기
        for(int i=0;i<bits.size();i++){
            if(i==0){
                Bitmap b=bits.get(i);
                iv1.setVisibility(View.VISIBLE);
                iv1.setImageBitmap(b);
             //   count++;
            }else if(i==1){
                Bitmap b=bits.get(i);
                iv2.setVisibility(View.VISIBLE);
                iv2.setImageBitmap(b);
               // count++;
            }else if(i==2){
                Bitmap b=bits.get(i);
                iv3.setVisibility(View.VISIBLE);
                iv3.setImageBitmap(b);
            }else if(i==3){
                Bitmap b=bits.get(i);
                iv4.setVisibility(View.VISIBLE);
                iv4.setImageBitmap(b);
            }else if(i==4){
                Bitmap b=bits.get(i);
                iv5.setVisibility(View.VISIBLE);
                iv5.setImageBitmap(b);
            }else if(i==5){
                Bitmap b=bits.get(i);
                iv6.setVisibility(View.VISIBLE);
                iv6.setImageBitmap(b);
            }
        }

        btn_addFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cate.equals("animal")){
                    Intent intent=new Intent(getApplicationContext(),SelectFolderAddFileAnimalActivity.class);

                    if (getIntent().getByteArrayExtra("image1") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_1",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image2") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_2",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image3") != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_3",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image4") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_4",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image5") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_5",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image6") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_6",byteArray);
                    }


                    startActivity(intent);
                }else if(cate.equals("human")){
                    Intent intent=new Intent(getApplicationContext(),SelectFolderAddFileHumanActivity.class);

                    if (getIntent().getByteArrayExtra("image1") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_1",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image2") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_2",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image3") != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_3",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image4") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_4",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image5") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_5",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image6") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_6",byteArray);
                    }
                    startActivity(intent);
                }else if(cate.equals("text")){
                    Intent intent=new Intent(getApplicationContext(),SelectFolderAddFileTextActivity.class);

                    if (getIntent().getByteArrayExtra("image1") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_1",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image2") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_2",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image3") != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_3",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image4") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_4",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image5") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_5",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image6") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_6",byteArray);
                    }
                    startActivity(intent);

                }else if(cate.equals("landscape")){
                    Intent intent=new Intent(getApplicationContext(),SelectFolderAddFileLandscapeActivity.class);
                    if (getIntent().getByteArrayExtra("image1") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_1",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image2") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_2",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image3") != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_3",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image4") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_4",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image5") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_5",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image6") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_6",byteArray);
                    }
                    startActivity(intent);
                }else if(cate.equals("food")){
                    Intent intent=new Intent(getApplicationContext(),SelelctFolderAddFileFoodActivity.class);
                    if (getIntent().getByteArrayExtra("image1") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_1",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image2") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_2",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image3") != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_3",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image4") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_4",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image5") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_5",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image6") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_6",byteArray);
                    }
                    startActivity(intent);
                }else if(cate.equals("art")){
                    Intent intent=new Intent(getApplicationContext(),SelectFolderAddFileArtActivity.class);
                    if (getIntent().getByteArrayExtra("image1") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_1",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image2") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_2",byteArray);
                    }

                    if (getIntent().getByteArrayExtra("image3") != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_3",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image4") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_4",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image5") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_5",byteArray);

                    }

                    if (getIntent().getByteArrayExtra("image6") != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image_6",byteArray);
                    }
                    startActivity(intent);
                }

            }
        });
    }
}
