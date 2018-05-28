package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HomeActivity extends AppCompatActivity {

    Button btnSelectViewAlbum,btnBringPic,btnAddFolder;
    TextView textUser;
    String email,name;
    String cookie="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent=getIntent();
        //email=intent.getStringExtra("email");
        //name=intent.getStringExtra("name");



        // 모든 연결시마다 항상 새롭게 세팅된 쿠키가 있는지 가져옵니다.
        // 주의 Set-Cookie 는 항상 주는것이 아니니 서버에서 보내줄대만 보관해야합니다.


        btnSelectViewAlbum=(Button)findViewById(R.id.btnSelectViewAlbum);
        btnBringPic=(Button)findViewById(R.id.btnBringPic);
        btnAddFolder=(Button)findViewById(R.id.btnAddFolder);
        textUser=(TextView)findViewById(R.id.textUser);

        //textUser.setText(name+"님");

        getCookie task=new getCookie();
        task.execute();

        //폴더 추가
        btnAddFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SelectFolderActivity.class);
                //intent.putExtra("email",email);
                //intent.putExtra("name",name);
                startActivity(intent);
                finish();
            }
        });
        //사진 불러오기
        btnBringPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //앨범 확인
        btnSelectViewAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public class getCookie extends AsyncTask<Void,Void,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL("http://52.199.119.109/pm_site/");
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                String cookieTemp = con.getHeaderField("Set-Cookie");

                if (cookieTemp != null)
                {
                    cookie = cookieTemp;  //쿠키 불러오기
                }



                //입력 스트림 열기
                InputStream inputStream=con.getInputStream();
                final String result = loginResult(inputStream);

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    String loginResult(InputStream is)
    {
        String data = "";
        Scanner s = new Scanner(is);
        while (s.hasNext()) data += s.nextLine();
        s.close();
        return data;
    }
}
