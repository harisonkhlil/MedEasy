package www.test.com.medical_system.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import www.test.com.medical_system.R;
import www.test.com.medical_system.bean.CommonResult;
import www.test.com.medical_system.utils.Constants;
import www.test.com.medical_system.utils.Consts;
import www.test.com.medical_system.utils.NetUtils;
import www.test.com.medical_system.utils.SPUtils;

public class FaceActivity extends BaseActivity {

    //拍照
    private static final int TAKE_PHOTO = 1;
    //uri对象
    private Uri imageUri;

    private  String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        //开启相机权限
        ActivityCompat.requestPermissions(
                FaceActivity.this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);


        File outputImage = new File(getExternalCacheDir(), uuid + "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(FaceActivity.this, "www.test.com.medical_system.fileprovider", outputImage);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        System.out.println("获取照片路径：" + imageUri);
                        System.out.println("获取照片路径：" + imageUri.getPath());
                        upload();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 上传照片
     */
    public void upload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                if(TV_choose==TV_TAKE_PHOTO){
                String TV_imagePath = "/storage/emulated/0/Android/data/www.test.com.medical_system/cache/"+ uuid +"output_image.jpg";
//                }
                Log.d("DialogActivity", "upload-run: 上传照片！");
//                File file = new File("/storage/emulated/0/Android/data/tian.project.takepictures/cache/output_image.jpg");
                File file = new File(TV_imagePath);
                System.out.println("测试返回结果imagePath:" + TV_imagePath);
                MediaType mediaType = MediaType.Companion.parse("text/x-markdown; charset=utf-8");
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody fileBody = RequestBody.Companion.create(file, mediaType);
                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), fileBody)
                        .build();

                String url = "http://192.168.43.135:8088/face/upload/image";
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                System.out.println(request);
                okHttpClient.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {

                        System.out.println("上传失败！");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Log.i("json", json);
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String code = jsonObject.optString("code");
                            String msg = jsonObject.optString("msg");
                            String userName = jsonObject.optString("userName");
                            String password = jsonObject.optString("password");
                            Log.i("code", code);
                            Log.i("msg", msg);
                            if (code.equals("200")) {
                                Map<String, String> params = new HashMap<>();
                                params.put("userName", userName);
                                params.put("password", password);
                                NetUtils.request(context, Constants.LOGIN, params, (CommonResult result) -> {
                                    String token = result.getToken();
                                    SPUtils.setPrefString(context, Consts.TOKEN, token);
                                    SPUtils.setPrefBoolean(context, Consts.IS_LOGIN, true);
                                    Log.i("result", result.toString());
                                    Toast.makeText(FaceActivity.this, "人脸登录成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(FaceActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                });
                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
}