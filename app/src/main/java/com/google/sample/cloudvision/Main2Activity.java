package com.google.sample.cloudvision;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {

    Button addFolder;
    String serverURL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addFolder=(Button)findViewById(R.id.addFolder);
        addFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myThread thread = new myThread();
                thread.start();

                /*
                URL url=new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();*/
            }
        });
    }

    class myThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");




                /*
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
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            if(email.equals(""))
                            {
                                txEmail.setVisibility(View.VISIBLE);
                            }
                            else{
                                txPw.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });*/

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



}
