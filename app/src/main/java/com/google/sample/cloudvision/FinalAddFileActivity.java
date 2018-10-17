package com.google.sample.cloudvision;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class FinalAddFileActivity extends AppCompatActivity {

    private static String TAG = "php_finalAddFileActivity";
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

    final int PERMISSION = 1;

    String cate,email,fname;
    //String pname;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String imgPath1,imgPath2,imgPath3,imgPath4,imgPath5,imgPath6;
    String fileName1,fileName2,fileName3,fileName4,fileName5,fileName6;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6;
    File sourceFile1,sourceFile2,sourceFile3,sourceFile4,sourceFile5,sourceFile6;
    Map<String,String> testMap;
    private int serverResponseCode = 0;
    Button backMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_add_file);

        if(Build.VERSION.SDK_INT>=23 && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION);
        }

        pref=getSharedPreferences("pref", Context.MODE_PRIVATE);

        backMain=(Button)findViewById(R.id.backMain);
        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        email=pref.getString("email","");
        cate=pref.getString("cate","");
        fname=pref.getString("fname","");

        if (getIntent().getByteArrayExtra("image1") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image1");
            bitmap1 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            imgPath1=pref.getString("imagePath1","");
            fileName1=imgPath1;
        }

        if (getIntent().getByteArrayExtra("image2") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image2");
            bitmap2 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            imgPath2=pref.getString("imagePath2","");
            fileName2=imgPath2;

        }

        if (getIntent().getByteArrayExtra("image3") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image3");
            bitmap3 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            imgPath3=pref.getString("imagePath3","");
            fileName3=imgPath3;

        }

        if (getIntent().getByteArrayExtra("image4") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image4");
            bitmap4 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            imgPath4=pref.getString("imagePath4","");
            fileName4=imgPath4;

        }

        if (getIntent().getByteArrayExtra("image5") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image5");
            bitmap5 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            imgPath5=pref.getString("imagePath5","");
            fileName5=imgPath5;
            //  bits.add(bitmap5);
            //  label5 = pref.getString("label5", "");
        }

        if (getIntent().getByteArrayExtra("image6") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image6");
            bitmap6 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            imgPath6=pref.getString("imagePath6","");
            fileName6=imgPath6;
            //  bits.add(bitmap6);
            //  label6 = pref.getString("label6", "");
        }

        try{
            Thread.sleep(800);

            uploadFile task=new uploadFile();
            task.execute(email,fname,cate); //업로드할 작업스레드 실행

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public class uploadFile extends AsyncTask<String,Void,String>{

        ProgressDialog progressDialog;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        DataOutputStream dos = null;

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 10240 * 10240;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(FinalAddFileActivity.this,
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
            String fname=  URLEncoder.encode((String)params[1]);
            String cate = (String)params[2];


            testMap=new HashMap<String,String>();
            testMap.put("email",email);
            testMap.put("fname",fname);
            testMap.put("cate",cate);

            if(imgPath1!=null){
                sourceFile1 = new File(imgPath1);
            }

            if(imgPath2!=null){
                sourceFile2 = new File(imgPath2);
            }

            if(imgPath3!=null){
                sourceFile3 = new File(imgPath3);
            }
            if(imgPath4!=null){
                sourceFile4 = new File(imgPath4);
            }

            if(imgPath5!=null){
                sourceFile5 = new File(imgPath5);
            }

            if(imgPath6!=null){
                sourceFile6 = new File(imgPath6);
            }

            try{

                if(sourceFile1!=null){
                    if(!sourceFile1.isFile()){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Log.i(TAG, "[UploadImageToServer1] Source File not exist :" + imgPath1);
                            }
                        });
                        return null;
                    }else{
                        FileInputStream fileInputStream = new FileInputStream(sourceFile1);
                        //String sendline="\r\n--"+binary+"\r\n";

                        String serverURL = "http://52.199.119.109/pm_site/album/app_upload_test.php";
                        //  String postParameters = "email=" + URLEncoder.encode(email) + "&fname=" + URLEncoder.encode(fname) + "&cate=" + URLEncoder.encode(cate);

                        URL url = new URL(serverURL);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달

                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                        httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                        httpURLConnection.setDoOutput(true);    //쓰기모드
                        httpURLConnection.setReadTimeout(5000);
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);     //읽기모드\

                        Log.i(TAG,"fileName: "+fileName1);

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
                        dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+fileName1+"\""+lineEnd);
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

                        Log.i(TAG, "[UploadImageToServer] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                        if(serverResponseCode == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FinalAddFileActivity.this,"이미지 업로드 완료", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        fileInputStream.close();
                        //추가된부분
                        dos.flush();
                        dos.close();
                        //    httpURLConnection.connect();
                        httpURLConnection.disconnect();

                    }
                }
                if(sourceFile2!=null){
                    if(!sourceFile2.isFile()){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Log.i(TAG, "[UploadImageToServer] Source File not exist :" + imgPath2);
                            }
                        });
                        return null;
                    }else{
                        FileInputStream fileInputStream = new FileInputStream(sourceFile2);
                        //String sendline="\r\n--"+binary+"\r\n";

                        String serverURL = "http://52.199.119.109/pm_site/album/app_upload_test.php";
                        //  String postParameters = "email=" + URLEncoder.encode(email) + "&fname=" + URLEncoder.encode(fname) + "&cate=" + URLEncoder.encode(cate);

                        URL url = new URL(serverURL);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달

                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                        httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                        httpURLConnection.setDoOutput(true);    //쓰기모드
                        httpURLConnection.setReadTimeout(5000);
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);     //읽기모드\

                        Log.i(TAG,"fileName: "+fileName2);

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
                        dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+fileName2+"\""+lineEnd);
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

                        Log.i(TAG, "[UploadImageToServer2] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                        if(serverResponseCode == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FinalAddFileActivity.this,"이미지 업로드 완료", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        fileInputStream.close();
                        //추가된부분
                        dos.flush();
                        dos.close();
                        httpURLConnection.disconnect();

                    }
                }

                if(sourceFile3!=null){
                    if(!sourceFile3.isFile()){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Log.i(TAG, "[UploadImageToServer] Source File not exist :" + imgPath3);
                            }
                        });
                        return null;
                    }else{
                        FileInputStream fileInputStream = new FileInputStream(sourceFile3);
                        //String sendline="\r\n--"+binary+"\r\n";

                        String serverURL = "http://52.199.119.109/pm_site/album/app_upload_test.php";
                        //  String postParameters = "email=" + URLEncoder.encode(email) + "&fname=" + URLEncoder.encode(fname) + "&cate=" + URLEncoder.encode(cate);

                        URL url = new URL(serverURL);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달

                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                        httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                        httpURLConnection.setDoOutput(true);    //쓰기모드
                        httpURLConnection.setReadTimeout(5000);
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);     //읽기모드\

                        Log.i(TAG,"fileName: "+fileName3);

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
                        dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+fileName3+"\""+lineEnd);
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

                        Log.i(TAG, "[UploadImageToServer3] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                        if(serverResponseCode == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FinalAddFileActivity.this,"이미지 업로드 완료", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        fileInputStream.close();
                        //추가된부분
                        dos.flush();
                        dos.close();
                        httpURLConnection.disconnect();

                    }
                }

                if(sourceFile4!=null){
                    if(!sourceFile4.isFile()){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Log.i(TAG, "[UploadImageToServer] Source File not exist :" + imgPath4);
                            }
                        });
                        return null;
                    }else{
                        FileInputStream fileInputStream = new FileInputStream(sourceFile4);
                        //String sendline="\r\n--"+binary+"\r\n";

                        String serverURL = "http://52.199.119.109/pm_site/album/app_upload_test.php";
                        //  String postParameters = "email=" + URLEncoder.encode(email) + "&fname=" + URLEncoder.encode(fname) + "&cate=" + URLEncoder.encode(cate);

                        URL url = new URL(serverURL);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달

                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                        httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                        httpURLConnection.setDoOutput(true);    //쓰기모드
                        httpURLConnection.setReadTimeout(5000);
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);     //읽기모드\

                        Log.i(TAG,"fileName: "+fileName4);

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
                        dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+fileName4+"\""+lineEnd);
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

                        Log.i(TAG, "[UploadImageToServer4] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                        if(serverResponseCode == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FinalAddFileActivity.this,"이미지 업로드 완료", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        fileInputStream.close();
                        //추가된부분
                        dos.flush();
                        dos.close();
                        httpURLConnection.disconnect();

                    }
                }

                if(sourceFile5!=null){
                    if(!sourceFile5.isFile()){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Log.i(TAG, "[UploadImageToServer] Source File not exist :" + imgPath5);
                            }
                        });
                        return null;
                    }else{
                        FileInputStream fileInputStream = new FileInputStream(sourceFile5);
                        //String sendline="\r\n--"+binary+"\r\n";

                        String serverURL = "http://52.199.119.109/pm_site/album/app_upload_test.php";
                        //  String postParameters = "email=" + URLEncoder.encode(email) + "&fname=" + URLEncoder.encode(fname) + "&cate=" + URLEncoder.encode(cate);

                        URL url = new URL(serverURL);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달

                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                        httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                        httpURLConnection.setDoOutput(true);    //쓰기모드
                        httpURLConnection.setReadTimeout(5000);
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);     //읽기모드\

                        Log.i(TAG,"fileName: "+fileName5);

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
                        dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+fileName5+"\""+lineEnd);
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

                        Log.i(TAG, "[UploadImageToServer3] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                        if(serverResponseCode == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FinalAddFileActivity.this,"이미지 업로드 완료", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        fileInputStream.close();
                        //추가된부분
                        dos.flush();
                        dos.close();
                        httpURLConnection.disconnect();

                    }
                }

                if(sourceFile6!=null){
                    if(!sourceFile6.isFile()){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Log.i(TAG, "[UploadImageToServer] Source File not exist :" + imgPath6);
                            }
                        });
                        return null;
                    }else{
                        FileInputStream fileInputStream = new FileInputStream(sourceFile6);
                        //String sendline="\r\n--"+binary+"\r\n";

                        String serverURL = "http://52.199.119.109/pm_site/album/app_upload_test.php";
                        //  String postParameters = "email=" + URLEncoder.encode(email) + "&fname=" + URLEncoder.encode(fname) + "&cate=" + URLEncoder.encode(cate);

                        URL url = new URL(serverURL);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달

                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data;");
                        httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
                        httpURLConnection.setDoOutput(true);    //쓰기모드
                        httpURLConnection.setReadTimeout(5000);
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);     //읽기모드\

                        Log.i(TAG,"fileName: "+fileName6);

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
                        dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+fileName6+"\""+lineEnd);
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

                        Log.i(TAG, "[UploadImageToServer6] HTTP Response is: "+ serverResponseMessage+ ": "+ serverResponseCode);

                        if(serverResponseCode == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FinalAddFileActivity.this,"이미지 업로드 완료", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        fileInputStream.close();
                        //추가된부분
                        dos.flush();
                        dos.close();
                        httpURLConnection.disconnect();

                    }
                }
            }catch (MalformedURLException ex){
                ex.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FinalAddFileActivity.this,"MalformedURLExcaption",Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i(TAG,"[UploadImageToServer] error:"+ex.getMessage(),ex);
            }catch (Exception e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(FinalAddFileActivity.this, "Got Exception : see logcat", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i(TAG,"[UploadImageToServer] upload file to server Exception Exception : "+ e.getMessage(),e);
            }
            Log.i(TAG, "[UploadImageToServer] Finish");
            return null;
        }
    }
}
