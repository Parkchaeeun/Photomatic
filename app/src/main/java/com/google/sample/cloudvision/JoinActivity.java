package com.google.sample.cloudvision;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.String;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class JoinActivity extends Activity {

    EditText joinEmail,joinName,joinPw,joinPw2;
    String email,name,pw,pw2;
    private static String TAG = "phptest_JoinActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);













       // setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        joinEmail=(EditText)findViewById(R.id.joinEmail);
        joinName=(EditText)findViewById(R.id.joinName);
        joinPw=(EditText)findViewById(R.id.joinPw);
        joinPw2=(EditText)findViewById(R.id.joinPw2);
        final TextView tx2=(TextView)findViewById(R.id.tx2);
        final TextView tx3=(TextView)findViewById(R.id.tx3);
        Button btnJoin=(Button)findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=joinEmail.getText().toString();
                name=joinName.getText().toString();
                pw=joinPw.getText().toString();
                pw2=joinPw2.getText().toString();

                if(name!=null && !name.equals("") && email!=null && !email.equals("") && pw!=null && !pw.equals("") && pw2!=null && !pw2.equals("")){
                    showMessage();  //메인스레드에서 동작
                }else{
                    tx3.setVisibility(View.VISIBLE);
                }

                if(pw.equals(pw2)){
                    InsertData task = new InsertData();
                    task.execute(name,email,pw);


                }else {
                    tx2.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void showMessage(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("회원가입이 완료되었습니다.\n" +
                "이제 로그인합니다.");
        builder.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                       // intent.putExtra("name",name);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        //메인 스레드에서 동작. 태스크 실행 직후에 호출됨.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(JoinActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //다이얼로그 종료
            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        //작업 스레드에서 동작.
        @Override
        protected String doInBackground(String... params) {
            String name = (String)params[0];
            String email = (String)params[1];
            String pw = (String)params[2];

            String serverURL = "http://52.199.119.109/pm_site/member/app_MemberDao.php ";
            String postParameters = "name=" + name + "&email=" + email + "&pw=" + pw;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}



