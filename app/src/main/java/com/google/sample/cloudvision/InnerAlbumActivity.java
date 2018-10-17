package com.google.sample.cloudvision;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//앨범 선택 후 이미지 추가하기
public class InnerAlbumActivity extends AppCompatActivity {

    private static String TAG = "php_InnerAlbumActivity";

    String cate, email, fname;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button getFileBtn;
    Map<String,String> testMap;
    private int serverResponseCode = 0;
    String str;
    File sourceFile;


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
        setContentView(R.layout.activity_inner_album);

        getFileBtn = (Button) findViewById(R.id.getFileBtn);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        email = pref.getString("email", "");
        cate = pref.getString("cate", "");
        fname = pref.getString("fname", "");

        getFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 30);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==30 && resultCode==RESULT_OK){
            ClipData clipData=data.getClipData();
            for(int i=0;i<clipData.getItemCount();i++){
                ClipData.Item item=clipData.getItemAt(i);
                Uri uri=item.getUri();
                str=getImagePathToUri(uri);
                try{
                    Thread.sleep(800);
                    uploadFile task=new uploadFile();
                    task.execute(email,fname,cate); //업로드할 작업스레드 실행
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            try{
                Thread.sleep(800);

                AlertDialog.Builder builder=new AlertDialog.Builder(InnerAlbumActivity.this);
                builder.setTitle("파일 추가 완료");
                builder.setMessage("이미지 업로드가 완료되었습니다.");
                builder.setPositiveButton("사진 확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getApplicationContext(),ChooseFolder1Activity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
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
                       progressDialog = ProgressDialog.show(InnerAlbumActivity.this,
                    "이미지를 업로드 중입니다.", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + s);
        }

        @Override
        protected String doInBackground(String... params) {
            String email = (String)params[0];
            String fname=  URLEncoder.encode(params[1]);
            String cate = (String)params[2];
            testMap=new HashMap<String,String>();
            testMap.put("email",email);
            testMap.put("fname",fname);
            testMap.put("cate",cate);

            if(str!=null){
                sourceFile=new File(str);
            }

            try{
                if(!sourceFile.isFile()){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Log.i(TAG, "[UploadImageToServer] Source File not exist :" + str);
                        }
                    });
                    return null;
                }else{
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    String serverURL = "http://52.199.119.109/pm_site/album/app_upload_test.php";
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

                    Log.i(TAG,"fileName: "+str);

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
                    dos.writeBytes("Content-Disposition: form-data; name=\"upload\"; filename=\""+str+"\""+lineEnd);
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
                                Toast.makeText(InnerAlbumActivity.this,"File Upload Completed1", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    fileInputStream.close();
                    //추가된부분
                    dos.flush();
                    dos.close();
                    httpURLConnection.disconnect();
                }
            }catch (MalformedURLException ex){
                ex.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InnerAlbumActivity.this,"MalformedURLExcaption",Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i(TAG,"[UploadImageToServer] error:"+ex.getMessage(),ex);
            }catch (Exception e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InnerAlbumActivity.this, "Got Exception : see logcat", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i(TAG,"[UploadImageToServer] upload file to server Exception Exception : "+ e.getMessage(),e);
            }

            return null;
        }
    }

}
