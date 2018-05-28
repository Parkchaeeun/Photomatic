package com.google.sample.cloudvision;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Button btnReturn=(Button)findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                finish();

            }
        });

        ImageView resultImage=(ImageView)findViewById(R.id.resultImage);
        ImageView resultImage_2=(ImageView)findViewById(R.id.resultImage_2);
        ImageView resultImage_3=(ImageView)findViewById(R.id.resultImage_3);
        ImageView resultImage_4=(ImageView)findViewById(R.id.resultImage_4);

        ImageView resultImage2=(ImageView)findViewById(R.id.resultImage2);
        ImageView resultImage2_2=(ImageView)findViewById(R.id.resultImage2_2);
        ImageView resultImage2_3=(ImageView)findViewById(R.id.resultImage2_3);
        ImageView resultImage2_4=(ImageView)findViewById(R.id.resultImage2_4);

        ImageView resultImage3=(ImageView)findViewById(R.id.resultImage3);
        ImageView resultImage3_2=(ImageView)findViewById(R.id.resultImage3_2);
        ImageView resultImage3_3=(ImageView)findViewById(R.id.resultImage3_3);
        ImageView resultImage3_4=(ImageView)findViewById(R.id.resultImage3_4);

        ImageView resultImage4=(ImageView)findViewById(R.id.resultImage4);
        ImageView resultImage4_2=(ImageView)findViewById(R.id.resultImage4_2);
        ImageView resultImage4_3=(ImageView)findViewById(R.id.resultImage4_3);
        ImageView resultImage4_4=(ImageView)findViewById(R.id.resultImage4_4);
        ImageView resultImage4_5=(ImageView)findViewById(R.id.resultImage4_5);

        try{
            String imgpath = "data/data/com.google.sample.cloudvision/files/test.png";
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            resultImage.setImageBitmap(bm);

            String imgpath_2 = "data/data/com.google.sample.cloudvision/files/test_2.png";
            Bitmap bm_2 = BitmapFactory.decodeFile(imgpath_2);
            resultImage_2.setImageBitmap(bm_2);

            String imgpath_3 = "data/data/com.google.sample.cloudvision/files/test_3.png";
            Bitmap bm_3 = BitmapFactory.decodeFile(imgpath_3);
            resultImage_3.setImageBitmap(bm_3);

            String imgpath_4 = "data/data/com.google.sample.cloudvision/files/test_4.png";
            Bitmap bm_4 = BitmapFactory.decodeFile(imgpath_4);
            resultImage_4.setImageBitmap(bm_4);



            String imgpath2 = "data/data/com.google.sample.cloudvision/files/test2.png";
            Bitmap bm2 = BitmapFactory.decodeFile(imgpath2);
            resultImage2.setImageBitmap(bm2);

            String imgpath2_2 = "data/data/com.google.sample.cloudvision/files/test2_2.png";
            Bitmap bm2_2 = BitmapFactory.decodeFile(imgpath2_2);
            resultImage2_2.setImageBitmap(bm2_2);

            String imgpath2_3 = "data/data/com.google.sample.cloudvision/files/test2_3.png";
            Bitmap bm2_3 = BitmapFactory.decodeFile(imgpath2_3);
            resultImage2_3.setImageBitmap(bm2_3);

            String imgpath2_4 = "data/data/com.google.sample.cloudvision/files/test2_4.png";
            Bitmap bm2_4 = BitmapFactory.decodeFile(imgpath2_4);
            resultImage2_4.setImageBitmap(bm2_4);


            String imgpath3 = "data/data/com.google.sample.cloudvision/files/test3.png";
            Bitmap bm3 = BitmapFactory.decodeFile(imgpath3);
            resultImage3.setImageBitmap(bm3);

            String imgpath3_2 = "data/data/com.google.sample.cloudvision/files/test3_2.png";
            Bitmap bm3_2 = BitmapFactory.decodeFile(imgpath3_2);
            resultImage3_2.setImageBitmap(bm3_2);

            String imgpath3_3 = "data/data/com.google.sample.cloudvision/files/test3_3.png";
            Bitmap bm3_3 = BitmapFactory.decodeFile(imgpath3_3);
            resultImage3_3.setImageBitmap(bm3_3);

            String imgpath3_4 = "data/data/com.google.sample.cloudvision/files/test3_4.png";
            Bitmap bm3_4 = BitmapFactory.decodeFile(imgpath3_4);
            resultImage3_4.setImageBitmap(bm3_4);


            String imgpath4 = "data/data/com.google.sample.cloudvision/files/test4.png";
            Bitmap bm4 = BitmapFactory.decodeFile(imgpath4);
            resultImage4.setImageBitmap(bm4);

            String imgpath4_2 = "data/data/com.google.sample.cloudvision/files/test4_2.png";
            Bitmap bm4_2 = BitmapFactory.decodeFile(imgpath4_2);
            resultImage4_2.setImageBitmap(bm4_2);

            String imgpath4_3 = "data/data/com.google.sample.cloudvision/files/test4_3.png";
            Bitmap bm4_3 = BitmapFactory.decodeFile(imgpath4_3);
            resultImage4_3.setImageBitmap(bm4_3);

            String imgpath4_4 = "data/data/com.google.sample.cloudvision/files/test4_4.png";
            Bitmap bm4_4 = BitmapFactory.decodeFile(imgpath4_4);
            resultImage4_4.setImageBitmap(bm_4);

            String imgpath4_5= "data/data/com.google.sample.cloudvision/files/test4_5.png";
            Bitmap bm4_5 = BitmapFactory.decodeFile(imgpath4_5);
            resultImage4_5.setImageBitmap(bm4_5);

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "load error", Toast.LENGTH_SHORT).show();
        }
    }
}
