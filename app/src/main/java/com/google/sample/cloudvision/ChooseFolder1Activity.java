package com.google.sample.cloudvision;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

//폴더 속 사진들 보여주기
public class ChooseFolder1Activity extends AppCompatActivity {
    private static String TAG = "php_chooseFolder1Activity";

    String cate,email,fname;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button addImage;
    String result;
    TextView toolbar_title;
    Bitmap bitmap,bitmap1,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6,bitmap7,bitmap8,bitmap9,bitmap10,bitmap11,bitmap12,bitmap13,bitmap14,bitmap15,bitmap16;

    ImageView iv_folder1_4,iv_folder1_1,iv_folder1_2,iv_folder1_3,iv_folder1_5,iv_folder1_6,iv_folder1_7,iv_folder1_8,iv_folder1_9,iv_folder1_10,iv_folder1_11,iv_folder1_12,iv_folder1_13,iv_folder1_14,iv_folder1_15,iv_folder1_16;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                editor.apply();

                Toast.makeText(this,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_home:

                editor=pref.edit();
                editor.remove("cate");
                editor.apply();
                editor=pref.edit();
                editor.remove("fname");
                editor.apply();
                editor=pref.edit();
                editor.remove("group");
                editor.apply();
                Toast.makeText(this,"홈 화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_folder1);
        pref=getSharedPreferences("pref",Context.MODE_PRIVATE);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
        addImage=(Button)findViewById(R.id.addImage);
        toolbar_title=(TextView)findViewById(R.id.toolbar_title);
        email=pref.getString("email","");
        cate=pref.getString("cate","");
        fname=pref.getString("fname","");

        toolbar_title.setText(fname);

        iv_folder1_1=(ImageView)findViewById(R.id.iv_folder1_1);
        iv_folder1_2=(ImageView)findViewById(R.id.iv_folder1_2);
        iv_folder1_3=(ImageView)findViewById(R.id.iv_folder1_3);
        iv_folder1_4=(ImageView)findViewById(R.id.iv_folder1_4);
        iv_folder1_5=(ImageView)findViewById(R.id.iv_folder1_5);
        iv_folder1_6=(ImageView)findViewById(R.id.iv_folder1_6);
        iv_folder1_7=(ImageView)findViewById(R.id.iv_folder1_7);
        iv_folder1_8=(ImageView)findViewById(R.id.iv_folder1_8);
        iv_folder1_9=(ImageView)findViewById(R.id.iv_folder1_9);
        iv_folder1_10=(ImageView)findViewById(R.id.iv_folder1_10);
        iv_folder1_11=(ImageView)findViewById(R.id.iv_folder1_11);
        iv_folder1_12=(ImageView)findViewById(R.id.iv_folder1_12);
        iv_folder1_13=(ImageView)findViewById(R.id.iv_folder1_13);
        iv_folder1_14=(ImageView)findViewById(R.id.iv_folder1_14);
        iv_folder1_15=(ImageView)findViewById(R.id.iv_folder1_15);
        iv_folder1_16=(ImageView)findViewById(R.id.iv_folder1_16);



        iv_folder1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname1","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);

            }
        });

        iv_folder1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname2","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname3","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname4","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname5","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname6","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname6","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname7","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname8","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname9","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });
        iv_folder1_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname10","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname11","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname12","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname13","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname14","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname15","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        iv_folder1_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                String ppname=pref.getString("pname16","");
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("ppname",ppname);
                editor.apply();
                startActivity(intent);
            }
        });

        try{
            Thread.sleep(800);
            showImages task=new showImages();
            task.execute(email,fname,cate);  //app_photo.php실행에 필요
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),InnerAlbumActivity.class);
                startActivity(intent);
            }
        });
    }
    public class showImages extends AsyncTask<String,Bitmap,String>{
        ProgressDialog progressDialog;
        int count=0;

        @Override
        protected void onProgressUpdate(Bitmap... bitmaps) {
            if(count==1){
                iv_folder1_1.setImageBitmap(bitmaps[0]);
            }else if(count==2){
                iv_folder1_2.setImageBitmap(bitmaps[0]);
            }else if(count==3){
                iv_folder1_3.setImageBitmap(bitmaps[0]);
            //    bitmap3=resizeBitmap(bitmaps[0]);
           //     iv_folder1_3.setImageBitmap(bitmap3);
            }else if(count==4){
                iv_folder1_4.setImageBitmap(bitmaps[0]);
            }else if(count==5){
                iv_folder1_5.setImageBitmap(bitmaps[0]);
            }else if(count==6){
                iv_folder1_6.setImageBitmap(bitmaps[0]);
            }else if(count==7){
                iv_folder1_7.setImageBitmap(bitmaps[0]);
            }else if(count==8){
                iv_folder1_8.setImageBitmap(bitmaps[0]);
            }else if(count==9){
                iv_folder1_9.setImageBitmap(bitmaps[0]);
            }else if(count==10){
                iv_folder1_10.setImageBitmap(bitmaps[0]);
            }else if(count==11){
                iv_folder1_11.setImageBitmap(bitmaps[0]);
            }else if(count==12){
                iv_folder1_12.setImageBitmap(bitmaps[0]);
            }else if(count==13){
                iv_folder1_13.setImageBitmap(bitmaps[0]);
            }else if(count==14){
                iv_folder1_14.setImageBitmap(bitmaps[0]);
            }else if(count==15){
                iv_folder1_15.setImageBitmap(bitmaps[0]);
            }else if(count==16){
                iv_folder1_16.setImageBitmap(bitmaps[0]);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ChooseFolder1Activity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + s);
        }

        @Override
        protected String doInBackground(String... params) {
            String email = (String)params[0];
            String fname=  (String)params[1];
            String cate = (String)params[2];
            try{
                String serverURL = "http://52.199.119.109/pm_site/album/app_photo.php";
                String postParameters = "email=" + URLEncoder.encode(email) + "&fname=" + URLEncoder.encode(fname) + "&cate=" + URLEncoder.encode(cate);

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달
                httpURLConnection.setReadTimeout(10*1000);
                httpURLConnection.setConnectTimeout(10*1000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);     //읽기모드
                httpURLConnection.setDoOutput(true);    //쓰기모드
                httpURLConnection.connect();

                //바이트 데이터를 서버에 전송
                OutputStream outputStream = httpURLConnection.getOutputStream();  //서버에 데이터를 전송하기 위해 꼭 필요한 IO객체 선언 및 초기화
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();  //에러 발생
                Log.d(TAG, "POST response code - " + responseStatusCode);

                //서버에서 데이터 받기
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line+'\n');
                }
                bufferedReader.close();
                httpURLConnection.disconnect();

                result=sb.toString();  //a.png b.png가 들어있음
                StringTokenizer st = new StringTokenizer(result);
                while(st.hasMoreTokens()){
                    String pname=st.nextToken();
                    URL url2=new URL("http://52.199.119.109/pm_site/album/user-album/"+email+"/"+cate+"/"+fname+"/"+pname);
                    HttpURLConnection con=(HttpURLConnection)url2.openConnection();

                    con.setReadTimeout(5000);
                    con.setConnectTimeout(5000);
                    con.setRequestMethod("POST");
                    con.setDoInput(true);     //읽기모드
                    con.setDoOutput(true);    //쓰기모드
                    con.connect();

                    //스트림 생성
                    InputStream is=con.getInputStream();
                    bitmap= BitmapFactory.decodeStream(is);

                    count++; //초기 카운트값이 0인데 1이된다.

                    publishProgress(bitmap);

                    if(count==1){
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putString("pname1",pname);
                        editor.apply();
                    }else if(count==2){
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putString("pname2",pname);
                        editor.apply();
                    }else if(count==3){
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putString("pname3",pname);
                        editor.apply();
                    }else if(count==4){
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putString("pname4",pname);
                        editor.apply();
                    }else if(count==5){
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putString("pname5",pname);
                        editor.apply();
                    }else if(count==6){
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putString("pname6",pname);
                        editor.apply();
                    }else if(count==7){
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putString("pname7",pname);
                        editor.apply();
                    }else if(count==8){
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putString("pname8",pname);
                        editor.apply();
                    }
                    is.close();
                    con.disconnect();
                   // count++;
                }
                String s= "success";
                return s;

            }catch (Exception e){
                Log.d(TAG, "chooseFolder: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
