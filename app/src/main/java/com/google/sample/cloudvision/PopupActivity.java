package com.google.sample.cloudvision;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PopupActivity extends AppCompatActivity {

    private static String TAG = "phptest_PopupActivity";
    String email,group;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

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
                editor.remove("group");
                editor.commit();

                /*
                editor=pref.edit();
                editor.remove("fname");
                editor.commit();*/

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
        //타이틀바 없애기
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        final EditText edt_Folder=(EditText)findViewById(R.id.edt_Folder);
        Button btn_folderName=(Button)findViewById(R.id.btn_folderName);

       // final Intent intent=getIntent();
        pref=getSharedPreferences("pref", Context.MODE_PRIVATE);

        email=pref.getString("email","");
        group=pref.getString("group","");
     //   email=intent.getStringExtra("email");
     //   group=intent.getStringExtra("group");
       // name=intent.getStringExtra("name");

        btn_folderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname=edt_Folder.getText().toString();
                //Intent intent2=new Intent(getApplicationContext(),FolderCheckActivity.class);
               // intent2.putExtra("fname",fname);
              //  intent2.putExtra("group",group);

                createFolder task=new createFolder();
                task.execute(email,fname,group);

                showMessage();
            }
        });
    }

    private void showMessage(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("폴더가 추가되었습니다.\n" +
                "홈 화면으로 돌아갑니다.");
        builder.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                     //   intent.putExtra("name",name);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        AlertDialog dialog=builder.create();
        dialog.show();
    }

   public class createFolder extends AsyncTask<String,Void,String>{

    ProgressDialog progressDialog;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(PopupActivity.this,
                "Please Wait", null, true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Log.d(TAG, "POST response  - " + s);

    }

    @Override
    protected String doInBackground(String... params) {

        String email = (String)params[0];
        String fname = (String)params[1];
        String group = (String)params[2];

        try{
            String serverURL = "http://52.199.119.109/pm_site/album/app_add_folder.php";
            String postParameters = "email=" + URLEncoder.encode(email) + "&fname=" + URLEncoder.encode(fname) + "&group=" + URLEncoder.encode(group);

            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //특정 사이트의 주소 전달
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

        StringBuffer sb = new StringBuffer("");
        String line="";

            while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                    }
                    bufferedReader.close();

                    return sb.toString();

                    }catch (Exception e){
                    Log.d(TAG, "createFolder: Error ", e);
                    return new String("Error: " + e.getMessage());
                    }
                    }
                    }
                    }
