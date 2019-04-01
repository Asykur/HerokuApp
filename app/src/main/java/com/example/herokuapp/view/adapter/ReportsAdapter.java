package com.example.herokuapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.herokuapp.R;
import com.example.herokuapp.model.DataReports;
import com.example.herokuapp.view.DetailReportActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReportsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<DataReports> dataReports;

    public ReportsAdapter(Context context, List<DataReports> dataReports) {
        this.context = context;
        this.dataReports = dataReports;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reports, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DataReports datas = dataReports.get(position);
        VH vh = (VH) holder;

        String createdAt = datas.getCreated_at().substring(0,10);
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
        Date parse = null;
        try {
            parse = input.parse(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date = output.format(parse);

        vh.createAt.setText("Created : "+date);
        vh.tvDesc.setText(datas.getDescription());
        final String url = datas.getImage().getUrl();
        Glide.with(context).load(url).into(vh.img);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailReportActivity.class);
                String ss = url;
                intent.putExtra("reports",datas);
                intent.putExtra("url",ss);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataReports.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvDesc, createAt, updatedAt;


        public VH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgItems);
            tvDesc = itemView.findViewById(R.id.tvItemDesc);
            createAt = itemView.findViewById(R.id.tvItemCreated);

        }
    }

}
