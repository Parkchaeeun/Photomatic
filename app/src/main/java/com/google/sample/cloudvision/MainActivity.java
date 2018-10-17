
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    private static final String CLOUD_VISION_API_KEY = "AIzaSyAEdUH91wgoQem2sKyh2NDD15Pag650810";
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    String labelName1,labelName2,labelName3,labelName4,labelName5,labelName6;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;

    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;

    private TextView mImageDetails;
    private ImageView mMainImage1,mMainImage2,mMainImage3,mMainImage4,mMainImage5,mMainImage6;
    private int count;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String label1,label2,label3,label4,label5,label6;
    Button fab2;
    Intent intent;
    String imageName,imagePath;
    Uri photoUri;
    ProgressDialog progressDialog;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent=new Intent(getApplicationContext(),SelectActivity.class);

        pref=getSharedPreferences("pref", Context.MODE_PRIVATE);
        //editor=pref.edit();

        Button fab = (Button) findViewById(R.id.fab);
        fab2=(Button)findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder
                        .setMessage("분류할 사진을 고르세요.")
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                            }
                        });
                        /*
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //startCamera();
                            }
                        });*/
                builder.create().show();
            }
        });

        mImageDetails = (TextView) findViewById(R.id.image_details);
        mMainImage1 = (ImageView) findViewById(R.id.main_image);
        mMainImage2 = (ImageView) findViewById(R.id.main_image2);
        mMainImage3 = (ImageView) findViewById(R.id.main_image3);
        mMainImage4 = (ImageView) findViewById(R.id.main_image4);
        mMainImage5 = (ImageView) findViewById(R.id.main_image5);
        mMainImage6 = (ImageView) findViewById(R.id.main_image6);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("분류 기준 선택");
                builder.setMessage("분류 기준 선택 화면으로 넘어가시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Intent intent=new Intent(getApplicationContext(),SelectActivity.class);

                        intent.putExtra("label1",label1);
                        intent.putExtra("label2",label2);
                        intent.putExtra("label3",label3);
                        intent.putExtra("label4",label4);
                        intent.putExtra("label5",label5);
                        intent.putExtra("label6",label6);

                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();

            }
        });

    }

    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {

/*
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            //이미지 다중선택 가능
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST);*/

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            //사진을 여러개 선택할수 있도록 한다
            //intent.setAction("android.intent.action.MULTIPLE_PICK");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "사진을 선택하세요"),  GALLERY_IMAGE_REQUEST);
        }
    }

/*
    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            File file=new File(Environment.getExternalStorageDirectory(),SAMPLEIMG);


            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }*/

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    //요청 처리하기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //갤러리를 이용해서 사진을 불러온 경우
        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data.getClipData()!=null) {
            uploadImage2(data.getClipData());

            //카메라 앱을 이용하여 사진을 불러온 경우
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) { //permissiongranted 값이 true이면
                    //startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) { //permissiongranted 값이 true이면
                    startGalleryChooser();
                }
                break;
        }
    }

    //카메라에서 사진 불러오기
    public void uploadImage(Uri uri2) {

        final Uri uri3=uri2;
        if (uri3 != null) {
            try {
                // scale the image to save on bandwidth
                count=1;
                final Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri2),
                                1200);
                callCloudVision(bitmap);
                mMainImage1.setImageBitmap(bitmap);
                mMainImage1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("분류 기준 선택");
                        builder.setMessage("분류 기준 선택 화면으로 넘어가시겠습니까?");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(getApplicationContext(),SelectActivity.class);

                                intent.putExtra("label1",label1);

                                //이미지를 다른 액티비티로 전달
                               ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                                byte[] byteArray = stream.toByteArray();
                                intent.putExtra("image1",byteArray);
                                //intent.putExtra("label",labelName1);

                                String imagePath3=getImagePathToUri(uri3);  //이미지 저장된 절대 경로
                                editor=pref.edit();
                                editor.putString("imagePath1",imagePath3);  //절대 경로를 SharedPreferences
                                editor.apply();

                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.create().show();
                    }
                });

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    //이미지 파일 경로 받아오기
    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    //갤러리에서 사진을 불러온 경우
    public void uploadImage2(ClipData data) {
        //클립데이터가 null이 아닌 경우
        if (data != null) {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "이미지를 업로드 중입니다.", null, true, true);
            try {
                ClipData clipData=data;
                count=1;  //수정한 부분

                for(int i=0;i<6;i++) {

                    if (i < clipData.getItemCount()) {

                        ClipData.Item item= clipData.getItemAt(i);
                        Uri uri=item.getUri();
                        final Bitmap bitmap =
                                scaleBitmapDown(
                                        MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                        300);


                        callCloudVision(bitmap);   //여기서 count++;
                        //1.8초 기다리기

                        try{
                            Thread.sleep(2000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        if(count==1){
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                            byte[] byteArray = stream.toByteArray();
                            //Intent intent=new Intent(getApplicationContext(),SelectActivity.class);

                            intent.putExtra("image1",byteArray);

                            imagePath=getImagePathToUri(uri);  //이미지 저장된 절대 경로
                            editor=pref.edit();
                            editor.putString("imagePath1",imagePath);  //절대 경로를 SharedPreferences
                            editor.commit();

                            //mMainImage1.setImageBitmap(bitmap);
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }else if(count==2){
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                            byte[] byteArray = stream.toByteArray();
                          //  Intent intent=new Intent(getApplicationContext(),SelectActivity.class);
                            intent.putExtra("image2",byteArray);

                            imagePath=getImagePathToUri(uri);  //이미지 저장된 절대 경로
                            editor=pref.edit();
                            editor.putString("imagePath2",imagePath);  //절대 경로를 SharedPreferences
                            editor.commit();

                            //mMainImage2.setImageBitmap(bitmap);
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }else if(count==3){
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                            byte[] byteArray = stream.toByteArray();
                           // Intent intent=new Intent(getApplicationContext(),SelectActivity.class);
                            intent.putExtra("image3",byteArray);

                            imagePath=getImagePathToUri(uri);  //이미지 저장된 절대 경로
                            editor=pref.edit();
                            editor.putString("imagePath3",imagePath);  //절대 경로를 SharedPreferences
                            editor.commit();

                            //mMainImage3.setImageBitmap(bitmap);
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }else if(count==4){
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                            byte[] byteArray = stream.toByteArray();
                            intent.putExtra("image4",byteArray);

                            imagePath=getImagePathToUri(uri);  //이미지 저장된 절대 경로
                            editor=pref.edit();
                            editor.putString("imagePath4",imagePath);  //절대 경로를 SharedPreferences
                            editor.commit();

                            //mMainImage4.setImageBitmap(bitmap);
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }else if(count==5){
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                            byte[] byteArray = stream.toByteArray();
                            //Intent intent=new Intent(getApplicationContext(),SelectActivity.class);
                            intent.putExtra("image5",byteArray);

                            imagePath=getImagePathToUri(uri);  //이미지 저장된 절대 경로
                            editor=pref.edit();
                            editor.putString("imagePath5",imagePath);  //절대 경로를 SharedPreferences
                            editor.commit();

                            //mMainImage5.setImageBitmap(bitmap);
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }else if(count==6){
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                            byte[] byteArray = stream.toByteArray();
                           // Intent intent=new Intent(getApplicationContext(),SelectActivity.class);
                            intent.putExtra("image6",byteArray);

                            imagePath=getImagePathToUri(uri);  //이미지 저장된 절대 경로
                            editor=pref.edit();
                            editor.putString("imagePath6",imagePath);  //절대 경로를 SharedPreferences
                            editor.commit();

                            //mMainImage6.setImageBitmap(bitmap); 수정한 부분
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                        //count++;  수정한 부분

                    }
                    count++;
                }
            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }

        progressDialog.dismiss();
    }

    private void callCloudVision(final Bitmap bitmap) throws IOException {
        // Switch text to loading
        mImageDetails.setText("사진을 업로드하는 중입니다. 잠시만 기다려주세요...");
        // Do the real work in an async task, because we need to use the network anyway

        //여기서 작업스레드 진행중(AsyncTask)
        new AsyncTask<Object, Void, String>() {

            //ProgressDialog progressDialog;
            /*
            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(MainActivity.this,
                        "이미지를 업로드 중입니다.", null, true, true);
            }*/

            @Override
            protected String doInBackground(Object... params) {
                try {
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
                    VisionRequestInitializer requestInitializer =
                            new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                                @Override
                                protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                                        throws IOException {
                                    super.initializeVisionRequest(visionRequest);
                                    String packageName = getPackageName();
                                    visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);
                                    String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);
                                    visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                                }
                            };

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);
                    Vision vision = builder.build();
                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();
                        // Add the image
                        Image base64EncodedImage = new Image();
                        // Convert the bitmap to a JPEG
                        // Just in case it's a format that Android understands but Cloud Vision
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

                        // Base64 encode the JPEG
                        base64EncodedImage.encodeContent(imageBytes);
                        annotateImageRequest.setImage(base64EncodedImage);

                        // add the features we want
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature labelDetection = new Feature();
                            labelDetection.setType("LABEL_DETECTION");
                            labelDetection.setMaxResults(10);
                            add(labelDetection);
                        }});
                        // Add the list of one thing to the request
                        add(annotateImageRequest);
                    }});

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    // Due to a bug: requests to Vision API containing large images fail when GZipped.
                    annotateRequest.setDisableGZipContent(true);
                    Log.d(TAG, "created Cloud Vision request object, sending request");

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response); //분류 결과 -> onPostExecute로 넘겨준다.

                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to make API request because of other IOException " +
                            e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            //doInBackGround에서 넘겨준 result값을 받아서 처리한다.
            protected void onPostExecute(String result) {
               // progressDialog.dismiss();
                mImageDetails.setText("이미지 분석이 완료");  //수정한 부분
                   // count++; 수정한 부분
            }
        }.execute();
    }
    //선택된 이미지 크기 조절
   public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
            //resizedWidth=maxDimension;
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
           // resizedHeight=maxDimension;
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        String message = "사진에서 찾아낸 물체들입니다:\n\n";
        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                message += String.format(Locale.US, "%.3f: %s", label.getScore(), label.getDescription());
                message += "\n";
                if(label.getDescription().equals("cat")||label.getDescription().equals("rabbit")||label.getDescription().equals("dog")||label.getDescription().equals("bird")){
                    message+="동물을 인식했습니다.";
                    message+="\n";

                    if(count==1) labelName1="animal";
                    else if(count==2) labelName2="animal";
                    else if(count==3) labelName3="animal";
                    else if(count==4) labelName4="animal";
                    else if(count==5) labelName5="animal";
                    else if(count==6) labelName6="animal";


                    message+="labelName1: "+labelName1+"labelName2: "+labelName2+"labelName3: "+labelName3+"labelName4: "+labelName4+"labelName5: "+labelName5+"labelName6: "+labelName6;

                    editor=pref.edit();
                    editor.putString("label1",labelName1);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label2",labelName2);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label3",labelName3);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label4",labelName4);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label5",labelName5);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label6",labelName6);
                    editor.commit();
            }
            /*
            for(EntityAnnotation label : labels){
                if(label.getDescription().equals("cat")||label.getDescription().equals("rabbit")||label.getDescription().equals("dog")||label.getDescription().equals("bird")){
                    message+="동물을 인식했습니다.";
                    message+="\n";

                    if(count==1) labelName1="동물";
                    else if(count==2) labelName2="동물";
                    else if(count==3) labelName3="동물";
                    else if(count==4) labelName4="동물";
                    else if(count==5) labelName5="동물";
                    else if(count==6) labelName6="동물";

                    message+="labelName1: "+labelName1+"labelName2: "+labelName2+"labelName3: "+labelName3+"labelName4: "+labelName4+"labelName5: "+labelName5+"labelName6: "+labelName6;
 if(labelName6.equals("동물") ||labelName2.equals("동물")||labelName3.equals("동물")||labelName4.equals("동물")||labelName5.equals("동물")||labelName6.equals("동물")) break;*/

                else if(label.getDescription().equals("male")||label.getDescription().equals("man")||label.getDescription().equals("boy")||label.getDescription().equals("black hair")||label.getDescription().equals("long hair")
                        ||label.getDescription().equals("lady")||label.getDescription().equals("nose")||label.getDescription().equals("neck")||label.getDescription().equals("pilot")||label.getDescription().equals("girl")||label.getDescription().equals("woman")||label.getDescription().equals("gentleman")
                        ||label.getDescription().equals("finger")||label.getDescription().equals("shoulder")||label.getDescription().equals("fashion model")||label.getDescription().equals("person")||label.getDescription().equals("singer")||label.getDescription().equals("social group")||label.getDescription().equals("face")
                        ||label.getDescription().equals("forehead")||label.getDescription().equals("hair")||label.getDescription().equals("human")||label.getDescription().equals("team")||label.getDescription().equals("family")||label.getDescription().equals("child")){
                    message+="인물을 인식했습니다.";
                    message+="\n";

                    if(count==1) labelName1="human";
                    else if(count==2) labelName2="human";
                    else if(count==3) labelName3="human";
                    else if(count==4) labelName4="human";
                    else if(count==5) labelName5="human";
                    else if(count==6) labelName6="human";

                    message+="labelName1: "+labelName1+"labelName2: "+labelName2+"labelName3: "+labelName3+"labelName4: "+labelName4+"labelName5: "+labelName5+"labelName6: "+labelName6;

                    editor=pref.edit();
                    editor.putString("label1",labelName1);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label2",labelName2);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label3",labelName3);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label4",labelName4);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label5",labelName5);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label6",labelName6);
                    editor.commit();


               //     if(labelName6.equals("인물") ||labelName2.equals("인물")||labelName3.equals("인물")||labelName4.equals("인물")||labelName5.equals("인물")||labelName6.equals("인물")) break;

                }else if(label.getDescription().equals("sky")||label.getDescription().equals("moon")||label.getDescription().equals("horizon")||label.getDescription().equals("phenomenon")||label.getDescription().equals("atmosphere")||label.getDescription().equals("forest")||label.getDescription().equals("valley")||label.getDescription().equals("sunlight")){
                    message+="풍경을 인식했습니다.";
                    message+="\n";

                    if(count==1) labelName1="landscape";
                    else if(count==2) labelName2="landscape";
                    else if(count==3) labelName3="landscape";
                    else if(count==4) labelName4="landscape";
                    else if(count==5) labelName5="landscape";
                    else if(count==6) labelName6="landscape";

                    message+="labelName1: "+labelName1+"labelName2: "+labelName2+"labelName3: "+labelName3+"labelName4: "+labelName4+"labelName5: "+labelName5+"labelName6: "+labelName6;

                    editor=pref.edit();
                    editor.putString("label1",labelName1);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label2",labelName2);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label3",labelName3);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label4",labelName4);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label5",labelName5);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label6",labelName6);
                    editor.commit();

/*
                    if(labelName6.equals("풍경") ||labelName2.equals("풍경")||labelName3.equals("풍경")||labelName4.equals("풍경")||labelName5.equals("풍경")||labelName6.equals("풍경")) break;
*/
                }else if(label.getDescription().equals("word")||label.getDescription().equals("letter")||label.getDescription().equals("alphabet")
                        ||label.getDescription().equals("text") ||label.getDescription().equals("screenshot")){
                    message+="문자를 인식했습니다.";
                    message+="\n";


                    if(count==1) labelName1="text";
                    else if(count==2) labelName2="text";
                    else if(count==3) labelName3="text";
                    else if(count==4) labelName4="text";
                    else if(count==5) labelName5="text";
                    else if(count==6) labelName6="text";

                    message+="labelName1: "+labelName1+"labelName2: "+labelName2+"labelName3: "+labelName3+"labelName4: "+labelName4+"labelName5: "+labelName5+"labelName6: "+labelName6;

                    editor=pref.edit();
                    editor.putString("label1",labelName1);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label2",labelName2);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label3",labelName3);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label4",labelName4);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label5",labelName5);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label6",labelName6);
                    editor.commit();

                    /*
                    if(labelName6.equals("문자") ||labelName2.equals("문자")||labelName3.equals("문자")||labelName4.equals("문자")||labelName5.equals("문자")||labelName6.equals("문자")) break;
*/
                }else if(label.getDescription().equals("food")||label.getDescription().equals("dish")||label.getDescription().equals("hamburger")||label.getDescription().equals("beverage")){
                    message+="음식을 인식했습니다.";
                    message+="\n";


                    if(count==1) labelName1="food";
                    else if(count==2) labelName2="food";
                    else if(count==3) labelName3="food";
                    else if(count==4) labelName4="food";
                    else if(count==5) labelName5="food";
                    else if(count==6) labelName6="food";

                    message+="labelName1: "+labelName1+"labelName2: "+labelName2+"labelName3: "+labelName3+"labelName4: "+labelName4+"labelName5: "+labelName5+"labelName6: "+labelName6;

                    editor=pref.edit();
                    editor.putString("label1",labelName1);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label2",labelName2);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label3",labelName3);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label4",labelName4);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label5",labelName5);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label6",labelName6);
                    editor.commit();

                    /*
                    if(labelName6.equals("음식") ||labelName2.equals("음식")||labelName3.equals("음식")||labelName4.equals("음식")||labelName5.equals("음식")||labelName6.equals("음식")) break;
*/
                }else if(label.getDescription().equals("picture")||label.getDescription().equals("art")){
                    message+="그림을 인식했습니다.";
                    message+="\n";

                    if(count==1) labelName1="art";
                    else if(count==2) labelName2="art";
                    else if(count==3) labelName3="art";
                    else if(count==4) labelName4="art";
                    else if(count==5) labelName5="art";
                    else if(count==6) labelName6="art";

                    message+="labelName1: "+labelName1+"labelName2: "+labelName2+"labelName3: "+labelName3+"labelName4: "+labelName4+"labelName5: "+labelName5+"labelName6: "+labelName6;

                    editor=pref.edit();
                    editor.putString("label1",labelName1);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label2",labelName2);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label3",labelName3);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label4",labelName4);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label5",labelName5);
                    editor.commit();

                    editor=pref.edit();
                    editor.putString("label6",labelName6);
                    editor.commit();

/*
                    if(labelName6.equals("풍경") ||labelName2.equals("풍경")||labelName3.equals("풍경")||labelName4.equals("풍경")||labelName5.equals("풍경")||labelName6.equals("풍경")) break;
*/
                }

                label1=labelName1;
                label2=labelName2;
                label3=labelName3;
                label4=labelName4;
                label5=labelName5;
                label6=labelName6;

            }

            /*
            editor=pref.edit();
            editor.putString("label1",labelName1);
            editor.putString("label2",labelName2);
            editor.putString("label3",labelName3);
            editor.putString("label4",labelName4);
            editor.putString("label5",labelName5);
            editor.putString("label6",labelName6);

            editor.commit();
            */

            //label1=labelName1;
           // label2
//

        } else {
            message += "nothing";

            /*

            if(count==1) labelName1="null";
            else if(count==2) labelName2="null";
            else if(count==3) labelName3="null";
            else if(count==4) labelName4="null";
            else if(count==5) labelName5="null";
            else if(count==6) labelName6="null";

            */

        }
        return message;
    }
}
