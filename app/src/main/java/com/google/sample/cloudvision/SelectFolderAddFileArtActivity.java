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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class SelectFolderAddFileArtActivity extends AppCompatActivity {

    private static String TAG = "php_selectFolderAddFileArtActivity";

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

                /*
                editor=pref.edit();
                editor.remove("cate");
                editor.commit();*/

                Toast.makeText(this,"홈 화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent1);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    String cate,email,fname;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button btn1,btn2,btn3,btn4,btn5,btn6;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_folder_add_file_art);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        email = pref.getString("email", ""); //이메일
        cate = pref.getString("cate", "");  //카테고리 값

        btn1=(Button)findViewById(R.id.selectFolderArt_1);
        btn2=(Button)findViewById(R.id.selectFolderArt_2);
        btn3=(Button)findViewById(R.id.selectFolderArt_3);
        btn4=(Button)findViewById(R.id.selectFolderArt_4);
        btn5=(Button)findViewById(R.id.selectFolderArt_5);
        btn6=(Button)findViewById(R.id.selectFolderArt_6);

        if (getIntent().getByteArrayExtra("image_1") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image_1");
            bitmap1 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        }

        if (getIntent().getByteArrayExtra("image_2") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image_2");
            bitmap2 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        }

        if (getIntent().getByteArrayExtra("image_3") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image_3");
            bitmap3 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        }

        if (getIntent().getByteArrayExtra("image_4") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image_4");
            bitmap4 = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        }

        if (getIntent().getByteArrayExtra("image_5") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image_5");
            bitmap5 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            //  bits.add(bitmap5);
            //  label5 = pref.getString("label5", "");
        }

        if (getIntent().getByteArrayExtra("image_6") != null) {
            byte[] arr = getIntent().getByteArrayExtra("image_6");
            bitmap6 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            //  bits.add(bitmap6);
            //  label6 = pref.getString("label6", "");
        }

        try{
            Thread.sleep(500);

            folderView task=new folderView();
            task.execute(email,cate);

        }catch (InterruptedException e){
            e.printStackTrace();
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  pref=getSharedPreferences("pref", Context.MODE_PRIVATE);

                String tol=btn1.getText().toString();
                fname=tol;

                editor=pref.edit();
                editor.putString("fname",fname);
                editor.commit();

                Intent intent=new Intent(getApplicationContext(),FinalAddFileActivity.class);
                if (getIntent().getByteArrayExtra("image_1") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image1",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_2") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image2",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_3") != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image3",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_4") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image4",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_5") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image5",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_6") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image6",byteArray);
                }
                startActivity(intent);
                //finish();

            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tol=btn2.getText().toString();
                fname=tol;

                editor=pref.edit();
                editor.putString("fname",fname);
                editor.commit();
                Intent intent=new Intent(getApplicationContext(),FinalAddFileActivity.class);
                if (getIntent().getByteArrayExtra("image_1") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image1",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_2") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image2",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_3") != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image3",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_4") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image4",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_5") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image5",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_6") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image6",byteArray);
                }
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tol=btn3.getText().toString();
                fname=tol;

                editor=pref.edit();
                editor.putString("fname",fname);
                editor.commit();
                Intent intent=new Intent(getApplicationContext(),FinalAddFileActivity.class);

                if (getIntent().getByteArrayExtra("image_1") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image1",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_2") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image2",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_3") != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image3",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_4") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image4",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_5") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image5",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_6") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image6",byteArray);
                }
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tol=btn4.getText().toString();
                fname=tol;

                editor=pref.edit();
                editor.putString("fname",fname);
                editor.commit();
                Intent intent=new Intent(getApplicationContext(),FinalAddFileActivity.class);
                if (getIntent().getByteArrayExtra("image_1") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image1",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_2") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image2",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_3") != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image3",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_4") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image4",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_5") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image5",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_6") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image6",byteArray);
                }
                startActivity(intent);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tol=btn5.getText().toString();
                fname=tol;

                editor=pref.edit();
                editor.putString("fname",fname);
                editor.commit();
                Intent intent=new Intent(getApplicationContext(),FinalAddFileActivity.class);
                if (getIntent().getByteArrayExtra("image_1") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image1",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_2") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image2",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_3") != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image3",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_4") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image4",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_5") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image5",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_6") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image6",byteArray);
                }
                startActivity(intent);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tol=btn6.getText().toString();
                fname=tol;

                editor=pref.edit();
                editor.putString("fname",fname);
                editor.commit();
                Intent intent=new Intent(getApplicationContext(),FinalAddFileActivity.class);

                if (getIntent().getByteArrayExtra("image_1") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image1",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_2") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image2",byteArray);
                }

                if (getIntent().getByteArrayExtra("image_3") != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image3",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_4") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap4.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image4",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_5") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap5.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image5",byteArray);

                }

                if (getIntent().getByteArrayExtra("image_6") != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap6.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image6",byteArray);
                }
                startActivity(intent);
            }
        });
    }

    public class folderView extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SelectFolderAddFileArtActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            int count = 1;

            StringTokenizer st = new StringTokenizer(result);
            while (st.hasMoreTokens()) {
                // System.out.println(st.nextToken());
                if (count == 1) {
                    String token = st.nextToken();
                    btn1.setText(token);
                    btn1.setVisibility(View.VISIBLE);

                    count++;
                    // break;
                } else if (count == 2) {
                    String token = st.nextToken();
                    fname = token;
                    btn2.setText(token);
                    btn2.setVisibility(View.VISIBLE);

                    count++;
                    // break;
                } else if (count == 3) {
                    String token = st.nextToken();
                    btn3.setText(token);
                    btn3.setVisibility(View.VISIBLE);

                    count++;
                    //  break;
                } else if (count == 4) {
                    String token = st.nextToken();
                    btn4.setText(token);
                    btn4.setVisibility(View.VISIBLE);

                    count++;
                    //   break;
                } else if (count == 5) {
                    String token = st.nextToken();
                    btn5.setText(token);
                    btn5.setVisibility(View.VISIBLE);

                    count++;
                    //     break;
                } else if (count == 6) {
                    String token = st.nextToken();
                    btn6.setText(token);
                    btn6.setVisibility(View.VISIBLE);

                    count++;
                    //   break;
                }
            }
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {

            String email = (String)params[0];
            String cate = (String)params[1];

            try{
                String serverURL = "http://52.199.119.109/pm_site/album/app_folder_main.php";
                String postParameters = "email=" + URLEncoder.encode(email) + "&cate=" + URLEncoder.encode(cate);

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

                int responseStatusCode = httpURLConnection.getResponseCode();  //에러 발생
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line+"\n");
                }
                bufferedReader.close();
                //     httpURLConnection.disconnect();
                return sb.toString();

            }catch (Exception e){
                Log.d(TAG, "selectFolderaddFileArt: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
