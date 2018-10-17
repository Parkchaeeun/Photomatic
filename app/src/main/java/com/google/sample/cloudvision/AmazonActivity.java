package com.google.sample.cloudvision;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AmazonActivity extends AppCompatActivity {

    Button btnbtn,btn_amazon_anim,btn_amazon_lands,btn_amazon_text,btn_amazon_food,btn_amazon_art,btn_amazon_human,btnbtns;
    String email,cate;
    String categroup;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Map<String,String> testMap;
    private int serverResponseCode = 0;
    String strr,str2,str3,str4,str5,str6;
    File sourceFile,SourceFile2,SourceFile3,SourceFile4,SourceFile5,SourceFile6;
    int count=0;
    private static String TAG = "php_AmazonActivity";
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
                /*
                editor=pref.edit();
                editor.clear();
                editor.apply();*/
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
        setContentView(R.layout.activity_amazon);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);

        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());
        StrictMode.setThreadPolicy(old);

        btnbtn=(Button)findViewById(R.id.btnbtn);
        btnbtns=(Button)findViewById(R.id.btnbtns);
        btn_amazon_anim=(Button)findViewById(R.id.btn_amazon_animal);
        btn_amazon_human=(Button)findViewById(R.id.btn_amazon_human);
        btn_amazon_food=(Button)findViewById(R.id.btn_amazon_food);
        btn_amazon_text=(Button)findViewById(R.id.btn_amazon_text);
        btn_amazon_art=(Button)findViewById(R.id.btn_amazon_art);
        btn_amazon_lands=(Button)findViewById(R.id.btn_amazon_lands);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        email = pref.getString("email", "");

        btn_amazon_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categroup="Animal";
                cate="animal";

                SharedPreferences.Editor editor=pref.edit();
                editor.putString("categroup",categroup);
                editor.putString("cate",cate);
                editor.apply();

                testMap=new HashMap<String,String>();
                testMap.put("email",email);
                testMap.put("categroup",categroup);

                Toast.makeText(getApplicationContext(),categroup,Toast.LENGTH_LONG).show();

                deleteTmp task=new deleteTmp();
                task.execute(email);


            }
        });

        btn_amazon_lands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categroup="Nature";
                cate="landscape";
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("categroup",categroup);
                editor.putString("cate",cate);
                editor.apply();

                testMap=new HashMap<String,String>();
                testMap.put("email",email);
                testMap.put("categroup",categroup);
                Toast.makeText(getApplicationContext(),categroup,Toast.LENGTH_LONG).show();
            }
        });

        btn_amazon_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categroup="Food";
                cate="food";
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("categroup",categroup);
                editor.putString("cate",cate);
                editor.apply();
                testMap=new HashMap<String,String>();
                testMap.put("email",email);
                testMap.put("categroup",categroup);
                Toast.makeText(getApplicationContext(),categroup,Toast.LENGTH_LONG).show();
            }
        });

        btn_amazon_human.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categroup="Human";
                cate="human";
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("categroup",categroup);
                editor.putString("cate",cate);
                editor.apply();
                testMap=new HashMap<String,String>();
                testMap.put("email",email);
                testMap.put("categroup",categroup);
                Toast.makeText(getApplicationContext(),categroup,Toast.LENGTH_LONG).show();

            }
        });

        btn_amazon_art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categroup="Art";
                cate="art";
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("categroup",categroup);
                editor.putString("cate",cate);
                editor.apply();
                testMap=new HashMap<String,String>();
                testMap.put("email",email);
                testMap.put("categroup",categroup);
                Toast.makeText(getApplicationContext(),categroup,Toast.LENGTH_LONG).show();
            }
        });

        btn_amazon_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categroup="Text";
                cate="text";
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("categroup",categroup);
                editor.putString("cate",cate);
                editor.apply();
                testMap=new HashMap<String,String>();
                testMap.put("email",email);
                testMap.put("categroup",categroup);
            }
        });

        //사진 불러오기
        btnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 30);
            }
        });

        //분류된 화면으로 넘어가기
        btnbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cate=="animal"){
                    Intent intent=new Intent(getApplicationContext(),ResultClassifyActivity.class);
                    startActivity(intent);
                }else if(cate=="human"){
                    Intent intent=new Intent(getApplicationContext(),ResultClassify_profile.class);
                    startActivity(intent);
                }else if(cate=="landscape") {
                    Intent intent = new Intent(getApplicationContext(), ResultClassify_lands.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==30 && resultCode==RESULT_OK){
            ClipData clipData=data.getClipData();
            for(int i=0;i<clipData.getItemCount();i++){
                count++; //count값은 1
                ClipData.Item item=clipData.getItemAt(i);
                Uri uri=item.getUri();
                if(count==1){
                    strr=getImagePathToUri(uri);
                    sourceFile=new File(strr);
                }else if(count==2){
                    str2=getImagePathToUri(uri);
                    SourceFile2=new File(str2);
                }else if(count==3){
                    str3=getImagePathToUri(uri);
                    SourceFile3=new File(str3);
                }else if(count==4){
                    str4=getImagePathToUri(uri);
                    SourceFile4=new File(str4);
                }else if(count==5){
                    str5=getImagePathToUri(uri);
                    SourceFile5=new File(str5);
                }else if(count==6){
                    str6=getImagePathToUri(uri);
                    SourceFile6=new File(str6);
                }
            }
            arrayImage task=new arrayImage();
            task.execute(email,categroup); //업로드할 작업스레드 실행
            btnbtn.setVisibility(View.GONE);
            btnbtns.setVisibility(View.VISIBLE);
        }
    }

    //절대경로 가져오기
    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public class deleteTmp extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            String email=(String)params[0];

            try{

                String delete = "http://app_deletetemp.php";
                String postParameterdelete = "email=" + URLEncoder.encode(email);
                URL urldelete = new URL(delete);
                HttpURLConnection httpURLConnectiond=(HttpURLConnection)urldelete.openConnection();
                httpURLConnectiond.setReadTimeout(5000);
                httpURLConnectiond.setConnectTimeout(5000);
                httpURLConnectiond.setRequestMethod("POST");
                httpURLConnectiond.setDoInput(true);     //읽기모드
                httpURLConnectiond.setDoOutput(true);    //쓰기모드
                httpURLConnectiond.connect();

                //바이트 데이터를 서버에 전송
                OutputStream outputStream = httpURLConnectiond.getOutputStream();  //서버에 데이터를 전송하기 위해 꼭 필요한 IO객체 선언 및 초기화
                outputStream.write(postParameterdelete.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnectiond.getResponseCode();  //에러 발생
                Log.d(TAG, "POST response code - " + responseStatusCode);

                httpURLConnectiond.disconnect();
            }catch (Exception e){

            }

            return null;
        }
    }

    public class arrayImage extends AsyncTask<String,Void,String>{

        ProgressDialog progressDialog;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        DataOutputStream dos = null;
        DataOutputStream dos2 = null;
        DataOutputStream dos3 = null;
        DataOutputStream dos4 = null;
        DataOutputStream dos5 = null;
        DataOutputStream dos6= null;

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 10240 * 10240;

        @Override
        protected void onPreExecute() {
          super.onPreExecute();
            progressDialog = ProgressDialog.show(AmazonActivity.this,
                    "이미지를 분류 중입니다.", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Log.d(TAG, "POST response  ssssssssssss- " + s);
        }

        @Override
        protected String doInBackground(String... params) {
            String email=(String)params[0];
            String categroup=(String)params[1];

            try {
                //올린 사진 개수가 1개라는 뜻
                if(count==1){
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    String serverURL = "http://app_array.php";
                    URL url = new URL(serverURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setReadTimeout(100*1000);
                    httpURLConnection.setConnectTimeout(100*1000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    dos = new DataOutputStream(httpURLConnection.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos.writeBytes( lineEnd ) ;
                        dos.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos.writeBytes( lineEnd ) ;
                    }
                    //파일 보내기
                    dos.writeBytes(twoHyphens+boundary+lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("newImage");
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens+boundary+lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+strr+"\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                    while (bytesRead>0){
                        dos.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer,0,bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection.getResponseCode();
                    String serverResponseMessage = httpURLConnection.getResponseMessage();

                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                    fileInputStream.close();
                    dos.flush();
                    dos.close();
                    httpURLConnection.disconnect();
                }else if(count==2){

                    //1번째 사진
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    String serverURL = "";
                    URL url = new URL(serverURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection.setDoOutput(true);    //쓰기모드
                    httpURLConnection.setReadTimeout(100*1000);
                    httpURLConnection.setConnectTimeout(100*1000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);     //읽기모드

                    dos = new DataOutputStream(httpURLConnection.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos.writeBytes( lineEnd ) ;
                        dos.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos.writeBytes( lineEnd ) ;
                    }

                    //파일 보내기
                    dos.writeBytes(twoHyphens+boundary+lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("newImage");
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens+boundary+lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+strr+"\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                    while (bytesRead>0){
                        dos.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer,0,bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection.getResponseCode();
                    String serverResponseMessage = httpURLConnection.getResponseMessage();

                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    httpURLConnection.disconnect();

                    //2번째 사진
                    FileInputStream fileInputStream2 = new FileInputStream(SourceFile2);
                    String serverURL2 = "";
                    URL url2 = new URL(serverURL2);
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection(); //특정 사이트의 주소 전달
                    httpURLConnection2.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection2.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection2.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection2.setDoOutput(true);    //쓰기모드
                    httpURLConnection2.setReadTimeout(10*1000);
                    httpURLConnection2.setConnectTimeout(10*1000);
                    httpURLConnection2.setRequestMethod("POST");
                    httpURLConnection2.setDoInput(true);     //읽기모드

                    dos2 = new DataOutputStream(httpURLConnection2.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos2.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos2.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos2.writeBytes( lineEnd ) ;
                        dos2.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos2.writeBytes( lineEnd ) ;
                    }

                    //파일 보내기
                    dos2.writeBytes(twoHyphens+boundary+lineEnd);
                    dos2.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos2.writeBytes(lineEnd);
                    dos2.writeBytes("newImage");
                    dos2.writeBytes(lineEnd);
                    dos2.writeBytes(twoHyphens+boundary+lineEnd);
                    dos2.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+str2+"\""+lineEnd);
                    dos2.writeBytes(lineEnd);

                    bytesAvailable = fileInputStream2.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream2.read(buffer,0,bufferSize);

                    while (bytesRead>0){
                        dos2.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream2.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream2.read(buffer,0,bufferSize);
                    }
                    dos2.writeBytes(lineEnd);
                    dos2.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection2.getResponseCode();
                    String serverResponseMessage2 = httpURLConnection2.getResponseMessage();

                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage2+ ": "+ serverResponseCode);

                    fileInputStream2.close();
                    dos2.flush();
                    dos2.close();

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    httpURLConnection2.disconnect();
                }else if(count==3){
                    //1번째 사진
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    String serverURL = "";
                    URL url = new URL(serverURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection.setDoOutput(true);    //쓰기모드
                    httpURLConnection.setReadTimeout(50*1000);
                    httpURLConnection.setConnectTimeout(50*1000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);     //읽기모드

                    dos = new DataOutputStream(httpURLConnection.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos.writeBytes( lineEnd ) ;
                        dos.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos.writeBytes( lineEnd ) ;
                    }

                    //파일 보내기
                    dos.writeBytes(twoHyphens+boundary+lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("newImage");
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens+boundary+lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+strr+"\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                    while (bytesRead>0){
                        dos.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer,0,bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection.getResponseCode();
                    String serverResponseMessage = httpURLConnection.getResponseMessage();

                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    httpURLConnection.disconnect();

                    //2번째 사진
                    FileInputStream fileInputStream2 = new FileInputStream(SourceFile2);
                    String serverURL2 = "http:///app_array.php";
                    URL url2 = new URL(serverURL2);
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection(); //특정 사이트의 주소 전달
                    httpURLConnection2.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection2.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection2.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection2.setDoOutput(true);    //쓰기모드
                    httpURLConnection2.setReadTimeout(50*1000);
                    httpURLConnection2.setConnectTimeout(50*1000);
                    httpURLConnection2.setRequestMethod("POST");
                    httpURLConnection2.setDoInput(true);     //읽기모드

                    dos2 = new DataOutputStream(httpURLConnection2.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos2.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos2.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos2.writeBytes( lineEnd ) ;
                        dos2.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos2.writeBytes( lineEnd ) ;
                    }

                    //파일 보내기
                    dos2.writeBytes(twoHyphens+boundary+lineEnd);
                    dos2.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos2.writeBytes(lineEnd);
                    dos2.writeBytes("newImage");
                    dos2.writeBytes(lineEnd);
                    dos2.writeBytes(twoHyphens+boundary+lineEnd);
                    dos2.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+str2+"\""+lineEnd);
                    dos2.writeBytes(lineEnd);

                    bytesAvailable = fileInputStream2.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream2.read(buffer,0,bufferSize);

                    while (bytesRead>0){
                        dos2.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream2.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream2.read(buffer,0,bufferSize);
                    }
                    dos2.writeBytes(lineEnd);
                    dos2.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection2.getResponseCode();
                    String serverResponseMessage2 = httpURLConnection2.getResponseMessage();

                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage2+ ": "+ serverResponseCode);

                    fileInputStream2.close();
                    dos2.flush();
                    dos2.close();

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    httpURLConnection2.disconnect();

                    //3번째 사진 보내기
                    FileInputStream fileInputStream3 = new FileInputStream(SourceFile3);
                    String serverURL3 = "http://...///app_array.php";
                    URL url3 = new URL(serverURL3);
                    HttpURLConnection httpURLConnection3 = (HttpURLConnection) url3.openConnection(); //특정 사이트의 주소 전달
                    httpURLConnection3.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection3.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection3.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection3.setDoOutput(true);    //쓰기모드
                    httpURLConnection3.setReadTimeout(100*1000);
                    httpURLConnection3.setConnectTimeout(100*1000);
                    httpURLConnection3.setRequestMethod("POST");
                    httpURLConnection3.setDoInput(true);     //읽기모드

                    dos3 = new DataOutputStream(httpURLConnection3.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos3.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos3.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos3.writeBytes( lineEnd ) ;
                        dos3.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos3.writeBytes( lineEnd ) ;
                    }

                    //파일 보내기
                    dos3.writeBytes(twoHyphens+boundary+lineEnd);
                    dos3.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos3.writeBytes(lineEnd);
                    dos3.writeBytes("newImage");
                    dos3.writeBytes(lineEnd);
                    dos3.writeBytes(twoHyphens+boundary+lineEnd);
                    dos3.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+str3+"\""+lineEnd);
                    dos3.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream3.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream3.read(buffer,0,bufferSize);
                    while (bytesRead>0){
                        dos3.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream3.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream3.read(buffer,0,bufferSize);
                    }
                    dos3.writeBytes(lineEnd);
                    dos3.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection3.getResponseCode();
                    String serverResponseMessage3 = httpURLConnection3.getResponseMessage();

                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage3+ ": "+ serverResponseCode);

                    fileInputStream3.close();
                    dos3.flush();
                    dos3.close();

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    httpURLConnection3.disconnect();
                }else if(count==4){
                    //1번째 사진
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    String serverURL = "hy.php";
                    URL url = new URL(serverURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection.setDoOutput(true);    //쓰기모드
                    httpURLConnection.setReadTimeout(50*1000);
                    httpURLConnection.setConnectTimeout(50*1000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);     //읽기모드

                    dos = new DataOutputStream(httpURLConnection.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos.writeBytes( lineEnd ) ;
                        dos.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos.writeBytes( lineEnd ) ;
                    }

                    //파일 보내기
                    dos.writeBytes(twoHyphens+boundary+lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("newImage");
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens+boundary+lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+strr+"\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                    while (bytesRead>0){
                        dos.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer,0,bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection.getResponseCode();
                    String serverResponseMessage = httpURLConnection.getResponseMessage();

                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    httpURLConnection.disconnect();

                    //2번째 사진
                    FileInputStream fileInputStream2 = new FileInputStream(SourceFile2);
                    String serverURL2 = "httphp";
                    URL url2 = new URL(serverURL2);
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection(); //특정 사이트의 주소 전달
                    httpURLConnection2.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection2.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection2.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection2.setDoOutput(true);    //쓰기모드
                    httpURLConnection2.setReadTimeout(50*1000);
                    httpURLConnection2.setConnectTimeout(50*1000);
                    httpURLConnection2.setRequestMethod("POST");
                    httpURLConnection2.setDoInput(true);     //읽기모드

                    dos2 = new DataOutputStream(httpURLConnection2.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos2.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos2.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos2.writeBytes( lineEnd ) ;
                        dos2.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos2.writeBytes( lineEnd ) ;
                    }

                    //파일 보내기
                    dos2.writeBytes(twoHyphens+boundary+lineEnd);
                    dos2.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos2.writeBytes(lineEnd);
                    dos2.writeBytes("newImage");
                    dos2.writeBytes(lineEnd);
                    dos2.writeBytes(twoHyphens+boundary+lineEnd);
                    dos2.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+str2+"\""+lineEnd);
                    dos2.writeBytes(lineEnd);

                    bytesAvailable = fileInputStream2.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream2.read(buffer,0,bufferSize);

                    while (bytesRead>0){
                        dos2.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream2.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream2.read(buffer,0,bufferSize);
                    }
                    dos2.writeBytes(lineEnd);
                    dos2.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection2.getResponseCode();
                    String serverResponseMessage2 = httpURLConnection2.getResponseMessage();

                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage2+ ": "+ serverResponseCode);

                    fileInputStream2.close();
                    dos2.flush();
                    dos2.close();

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    httpURLConnection2.disconnect();

                    //3번째 사진 보내기
                    FileInputStream fileInputStream3 = new FileInputStream(SourceFile3);
                    String serverURL3 = "http://ay.php";
                    URL url3 = new URL(serverURL3);
                    HttpURLConnection httpURLConnection3 = (HttpURLConnection) url3.openConnection(); //특정 사이트의 주소 전달
                    httpURLConnection3.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection3.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection3.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection3.setDoOutput(true);    //쓰기모드
                    httpURLConnection3.setReadTimeout(100*1000);
                    httpURLConnection3.setConnectTimeout(100*1000);
                    httpURLConnection3.setRequestMethod("POST");
                    httpURLConnection3.setDoInput(true);     //읽기모드

                    dos3 = new DataOutputStream(httpURLConnection3.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos3.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos3.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos3.writeBytes( lineEnd ) ;
                        dos3.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos3.writeBytes( lineEnd ) ;
                    }

                    //파일 보내기
                    dos3.writeBytes(twoHyphens+boundary+lineEnd);
                    dos3.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos3.writeBytes(lineEnd);
                    dos3.writeBytes("newImage");
                    dos3.writeBytes(lineEnd);
                    dos3.writeBytes(twoHyphens+boundary+lineEnd);
                    dos3.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+str3+"\""+lineEnd);
                    dos3.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream3.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream3.read(buffer,0,bufferSize);
                    while (bytesRead>0){
                        dos3.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream3.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream3.read(buffer,0,bufferSize);
                    }
                    dos3.writeBytes(lineEnd);
                    dos3.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection3.getResponseCode();
                    String serverResponseMessage3 = httpURLConnection3.getResponseMessage();

                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage3+ ": "+ serverResponseCode);

                    fileInputStream3.close();
                    dos3.flush();
                    dos3.close();
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    httpURLConnection3.disconnect();

                    //4번째 사진
                    FileInputStream fileInputStream4 = new FileInputStream(SourceFile4);
                    String serverURL4 = "http://app_array.php";
                    URL url4 = new URL(serverURL4);
                    HttpURLConnection httpURLConnection4 = (HttpURLConnection) url4.openConnection(); //특정 사이트의 주소 전달
                    httpURLConnection4.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection4.setRequestProperty("ENCTYPE", "multipart/form-data;");
                    httpURLConnection4.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                    httpURLConnection4.setDoOutput(true);    //쓰기모드
                    httpURLConnection4.setReadTimeout(50*1000);
                    httpURLConnection4.setConnectTimeout(50*1000);
                    httpURLConnection4.setRequestMethod("POST");
                    httpURLConnection4.setDoInput(true);     //읽기모드

                    dos4 = new DataOutputStream(httpURLConnection4.getOutputStream());
                    //변수 보내기
                    for (Iterator<String> i = testMap.keySet( ).iterator( ); i.hasNext( ); ){
                        String key = (String)i.next();
                        dos4.writeBytes( twoHyphens + boundary + lineEnd ) ; //필드 구분자 시작
                        dos4.writeBytes( "Content-Disposition: form-data; name=\""
                                + key
                                + "\""+ lineEnd ) ;
                        dos4.writeBytes( lineEnd ) ;
                        dos4.writeBytes( String.valueOf( testMap.get( key ) ) ) ;
                        dos4.writeBytes( lineEnd ) ;
                    }

                    //파일 보내기
                    dos4.writeBytes(twoHyphens+boundary+lineEnd);
                    dos4.writeBytes("Content-Disposition: form-data; name=\"data1\""+lineEnd);
                    dos4.writeBytes(lineEnd);
                    dos4.writeBytes("newImage");
                    dos4.writeBytes(lineEnd);
                    dos4.writeBytes(twoHyphens+boundary+lineEnd);
                    dos4.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+str4+"\""+lineEnd);
                    dos4.writeBytes(lineEnd);

                    bytesAvailable = fileInputStream4.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream4.read(buffer,0,bufferSize);

                    while (bytesRead>0){
                        dos4.write(buffer,0,bufferSize);
                        bytesAvailable = fileInputStream4.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream4.read(buffer,0,bufferSize);
                    }
                    dos4.writeBytes(lineEnd);
                    dos4.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                    serverResponseCode = httpURLConnection4.getResponseCode();
                    String serverResponseMessage4 = httpURLConnection4.getResponseMessage();
                    Log.d(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage4+ ": "+ serverResponseCode);
                    fileInputStream4.close();
                    dos4.flush();
                    dos4.close();
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    httpURLConnection4.disconnect();
                }
                String s="success";
                return s;
            }catch (Exception e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmazonActivity.this, "Got Exception : see logcat", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG,"[UploadImageToServer] upload file to server Exception Exception : "+ e.getMessage(),e);

                String s="failed";
                return s;
            }

        }
    }
}
