package com.google.sample.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

public class HomeActivity extends AppCompatActivity {

    private static String TAG = "php_HomeActivity";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button btnSelectViewAlbum,btnBringPic,btnAddFolder,btnAmazonMain,btnSearchFace;
    TextView textUser;
    String email,name;
    SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("#태그 검색");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //태그 검색해서
                String tagString = query;
                pref=getSharedPreferences("pref", Context.MODE_PRIVATE);
                editor=pref.edit();
                editor.putString("tagString",tagString);
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),SearchTagActivity.class);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
                //Toast.makeText(this,"홈 화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);

        pref=getSharedPreferences("pref", Context.MODE_PRIVATE);
        email=pref.getString("email","");
        btnSelectViewAlbum=(Button)findViewById(R.id.btnSelectViewAlbum);
        btnBringPic=(Button)findViewById(R.id.btnBringPic);
        btnAddFolder=(Button)findViewById(R.id.btnAddFolder);
        btnAmazonMain=(Button)findViewById(R.id.btnAmazonMain);
        textUser=(TextView)findViewById(R.id.textUser);
        btnSearchFace=(Button)findViewById(R.id.btnSearchFace);

        getName task=new getName();
        task.execute(email);

        //폴더 추가
        btnAddFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SelectFolderActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        //사진 불러오기
        btnBringPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
               // finish();
            }
        });

        //앨범 확인
        btnSelectViewAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),FolderViewActivity.class);
                startActivity(intent);
              //  finish();

            }
        });


        btnAmazonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AmazonActivity.class);
                startActivity(intent);
                //  finish();
            }
        });

        //인물검색
        btnSearchFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SearchFaceActivity.class);
                startActivity(intent);
            }
        });


    }

    public class getName extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            StringTokenizer st = new StringTokenizer(result);
            while(st.hasMoreTokens()){
                String token=st.nextToken();
                textUser.setText(token+"님 환영합니다");
            }
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String email=(String)params[0];
            try{
                String serverURL = "http://5";
                String postParameters = "email=" + URLEncoder.encode(email);
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
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

                //서버로부터 넘어오는 데이터 수신하기(바이트로 받는다)
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line+"\n");
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
                return sb.toString();
            }catch (Exception e){
                Log.d(TAG, "folderViewAnimal: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }


}
