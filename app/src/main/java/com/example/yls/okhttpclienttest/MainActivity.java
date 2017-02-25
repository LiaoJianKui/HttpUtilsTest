package com.example.yls.okhttpclienttest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText etPath, etPath2;
    private Button btnSearchImage, btnSearchCode;
    private TextView tvContent;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        etPath = (EditText) findViewById(R.id.etPath);
        etPath2 = (EditText) findViewById(R.id.etPath2);
        btnSearchImage = (Button) findViewById(R.id.btnSearchImg);
        btnSearchCode = (Button) findViewById(R.id.btnSearchCode);
        tvContent = (TextView) findViewById(R.id.tvContent);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        btnSearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadImag();
                useUtilsLoadImag();
            }
        });
        btnSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // loadCode();
                useUtilsLoadCode();
            }
        });

    }

    private void useUtilsLoadImag() {
        String path = etPath2.getText().toString().trim();
        OkHttpUtils.get().url(path).build().execute(new BitmapCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Bitmap response, int id) {
                ivImage.setImageBitmap(response);
            }
        });
    }

    private void useUtilsLoadCode() {
        String path = etPath.getText().toString().trim();
        OkHttpUtils.get().url(path).build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                tvContent.setText(response);
            }
        });
    }

    private void loadCode() {
        String path = etPath.getText().toString().trim();
        OkHttpClient okhttp = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        Call call = okhttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String html = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(html);
                    }
                });
            }
        });

    }

    private void loadImag() {
        String path2 = etPath2.getText().toString().trim();
        OkHttpClient okhttp = new OkHttpClient();
        Request request = new Request.Builder().url(path2).build();
        Call call = okhttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                byte[] imgbyte = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivImage.setImageBitmap(bitmap);
                    }
                });
            }
        });

    }
}
