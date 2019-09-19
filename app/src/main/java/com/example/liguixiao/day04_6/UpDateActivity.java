package com.example.liguixiao.day04_6;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class UpDateActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImg;
    private EditText mEdit;
    /**
     * 修改
     */
    private Button mUpdate;
    private String pic;
    private String title;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date);
        initView();


    }

    private void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        mEdit = (EditText) findViewById(R.id.edit);
        mUpdate = (Button) findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        intent = getIntent();
        title = intent.getStringExtra("title");
        pic = intent.getStringExtra("pic");
        //Glide.with(this).load(pic).into(mImg);
        mImg.setImageURI(Uri.parse(pic));
        mEdit.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.update:
                String s = mEdit.getText().toString();
                intent.putExtra("t",s);
                setResult(200,intent);
                finish();
                break;
        }
    }
}
