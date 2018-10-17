package com.google.sample.cloudvision;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.testing.http.apache.MockHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class LoginActivity extends Activity {

   EditText l_email,l_pw;
   TextView tx,tx3;
   String email,pw;
   Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        l_email=(EditText)findViewById(R.id.loginEmail);
        l_pw=(EditText)findViewById(R.id.loginPw);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        tx=(TextView)findViewById(R.id.tx);
        tx3=(TextView)findViewById(R.id.tx3);


        TextView tvforJoin=(TextView)findViewById(R.id.textViewforJoin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myThread thread = new myThread();
                if(v.getId() == R.id.btnLogin)
                {
                    if(btnLogin.getText().toString().equals("Login"))
                    {
                        email = l_email.getText().toString().trim();
                        pw = l_pw.getText().toString().trim();
                        thread.start();
                    }
                }
            }
        });
        tvforJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent);
            }
        });
        TextView tvforPW=(TextView)findViewById(R.id.textViewforPW);
        tvforPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),FindActivity.class);
                startActivity(intent);
            }
        });
    }
    Handler handler = new Handler();

    class myThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                URL url = new URL("");
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);


                String postData = "email="+ URLEncoder.encode(email) + "&pw=" + URLEncoder.encode(pw);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                InputStream inputStream;
                if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    inputStream = httpURLConnection.getInputStream();
                }
                else
                {
                    inputStream = httpURLConnection.getErrorStream();
                }
                final String result = loginResult(inputStream);

                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        if(result.equals("SUCCESS"))
                        {
                            SharedPreferences pref=getSharedPreferences("pref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putString("email",email);
                            editor.apply();


                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            if(email.equals("") && pw.equals(""))
                            {
                                tx3.setVisibility(View.VISIBLE);
                            }
                            else {
                                tx.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
