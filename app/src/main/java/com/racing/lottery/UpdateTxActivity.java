package com.racing.lottery;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lottery.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.racing.app.AppSp;
import com.racing.entity.UserBean;
import com.racing.view.LoadingDialog;
import com.racing.view.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by k41 on 2017/10/13.
 * 修改头像
 */

public class UpdateTxActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    private Button btn_update_tx;
    private ImageView img_update_tx;
    private String ing;
    private String url_img1;
    private String string_msg;
    private LoadingDialog loadingDialog;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private String img_path = null;
    private UserBean userBean;
    private AppSp appSp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.update_tx);
        InitView();
        tv_header_title.setText("修改头像");
        appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(UpdateTxActivity.this, "user");
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        btn_update_tx = (Button) findViewById(R.id.btn_update_tx);
        btn_update_tx.setOnClickListener(this);
        img_update_tx = (ImageView) findViewById(R.id.img_update_tx);
        img_update_tx.setOnClickListener(this);
    }

    // 请求权限兼容低版本
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPer(String... permissions) {
        requestPermissions(permissions, 800);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                UpdateTxActivity.this.finish();
                break;
            case R.id.btn_update_tx:
                //修改头像
                if (img_path != null) {
                    if (loadingDialog == null) {
                        loadingDialog = new LoadingDialog(UpdateTxActivity.this, R.style.LoadingDialog);
                    }
                    loadingDialog.show();
                    UpdateTx(userBean.getId() + "", img_path);
                } else {
                    Message message = new Message();
                    message.what = 100;
                    myHandler.sendMessage(message);
                }
                break;
            case R.id.img_update_tx:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
        }
    }

    //修改头像
    public void UpdateTx(String id, String tx) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/gai_tx")
                .addParams("id", id)
                .addParams("tx", tx)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(UpdateTxActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response.toString());
                    ing = jsonObj.getString("ing");
                    string_msg = jsonObj.getString("msg");
                    Message message = new Message();
                    message.what = 300;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //handler
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 300:
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    if (ing.equals("1")) {
                        Toast.makeText(UpdateTxActivity.this, "修改头像成功", Toast.LENGTH_LONG).show();
                    } else if (ing.equals("0")) {
                        Toast.makeText(UpdateTxActivity.this, string_msg, Toast.LENGTH_LONG).show();
                    }
                    break;
                case 100:
                    Toast.makeText(UpdateTxActivity.this, "请选择一张头像", Toast.LENGTH_LONG).show();
                    break;
                case 400:
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    if (ing.equals("1")) {
                        try {
                            JSONObject jsonObj = new JSONObject(string_msg);
                            url_img1 = jsonObj.getString("path");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else if (ing.equals("0")) {
                        Log.i("aaa","00000000000");
                    }
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
//                      crop(uri);
                    if (!TextUtils.isEmpty(uri.getAuthority())) {
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(uri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        img_path = cursor.getString(column_index);
//                        cursor.close();
                    } else {
                        img_path = uri.getPath();
                    }
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon);
                    UpImg(userBean.getId()+"",bitmaptoString(bitmap));
                    ImageLoader.getInstance().displayImage(uri + "", img_update_tx);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //上传图片
    public void UpImg(String id, String string_url) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/up")
                .addParams("id", id)
                .addParams("", string_url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(UpdateTxActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response.toString());
                    ing = jsonObj.getString("ing");
                    string_msg = response.toString();
                    Message message = new Message();
                    message.what = 400;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public String bitmaptoString(Bitmap bitmap){
        //将Bitmap转换成字符串
        String string=null;
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[]bytes=bStream.toByteArray();
        string= Base64.encodeToString(bytes,Base64.DEFAULT);
        return string;
    }


    private static final Handler handler = new Handler(Looper.getMainLooper());

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    okhttp3.Request request = chain.request().newBuilder()
                            .build();
                    return chain.proceed(request);
                }
            }).readTimeout(15, TimeUnit.SECONDS)// 设置读取超时时间
            .writeTimeout(15, TimeUnit.SECONDS)// 设置写的超时时间
            .connectTimeout(15, TimeUnit.SECONDS)// 设置连接超时时间
            .build();

    // 上传图片公有方法
    private final static void uploadImgAndParameter(Map<String, Object> map,
                                                    String url) {

        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue() instanceof File) {
                        File f = (File) entry.getValue();
                        builder.addFormDataPart(entry.getKey(), f.getName(),
                                RequestBody.create(MEDIA_TYPE_PNG, f));
                    } else {
                        builder.addFormDataPart(entry.getKey(), entry
                                .getValue().toString());
                    }
                }

            }
        }
        // 创建RequestBody
        RequestBody body = builder.build();
        final okhttp3.Request request = new okhttp3.Request.Builder().url(url)// 地址
                .post(body)// 添加请求体
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(Call call, final Response response)
                    throws IOException {
                final String data = response.body().string();
                Log.i("aaa", "上传照片成功-->" + data);
                call.cancel();// 上传成功取消请求释放内存
            }

            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("aaa", "上传失败-->" + e.getMessage());
                call.cancel();// 上传失败取消请求释放内存
            }

        });

    }
}
