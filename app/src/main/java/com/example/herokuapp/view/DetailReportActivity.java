package com.example.herokuapp.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.herokuapp.R;
import com.example.herokuapp.model.DataReports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class DetailReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        TextView tvDate = (TextView) findViewById(R.id.tvDetDate);
        TextView tvDesc = (TextView) findViewById(R.id.tvDetDesc);
        ImageView imgDet = (ImageView) findViewById(R.id.imgDetail);

        DataReports datas = getIntent().getParcelableExtra("reports");
        String urlImg = getIntent().getStringExtra("url");


        String createdAt = datas.getCreated_at().substring(0, 10);
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
        Date parse = null;
        try {
            parse = input.parse(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date = output.format(parse);

        tvDesc.setText(datas.getDescription());
        tvDate.setText(date);

        Glide.with(this).load(urlImg).into(imgDet);
    }


}
