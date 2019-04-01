package com.example.herokuapp.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.herokuapp.R;
import com.example.herokuapp.model.DataReports;
import com.example.herokuapp.presenter.ProfilePresenter;
import com.example.herokuapp.presenter.ReportsPresenter;
import com.example.herokuapp.presenter.ReportsViewIface;
import com.example.herokuapp.view.adapter.ReportsAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ReportsViewIface {

    private final int REQUEST_CAMERA = 100;
    private final int REQUEST_GALLERY = 101;
    private ImageView imgPreview;
    private ImageButton imgButton;
    private Button btnUpload;
    private AlertDialog alertDialog;
    private RecyclerView rvReports;
    private Uri imgUri = null;
    private File imgFile = null;
    private EditText etDesc;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgPreview = (ImageView) findViewById(R.id.imgPhoto);
        imgButton = (ImageButton) findViewById(R.id.btnPickImage);
        rvReports = (RecyclerView) findViewById(R.id.rvReports);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        etDesc = (EditText) findViewById(R.id.etDesc);
        etDesc.setFocusable(false);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);

        ReportsPresenter reportsPresenter = new ReportsPresenter(this);
        reportsPresenter.getReports();

        ImageButton imageButton = (ImageButton) findViewById(R.id.btnPickImage);
        imageButton.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnPickImage)) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.pick_photo, null);
            dialogBuilder.setView(dialogView);

            ImageButton btnGallery = (ImageButton) dialogView.findViewById(R.id.btnGallery);
            ImageButton btnCamera = (ImageButton) dialogView.findViewById(R.id.btnCamera);

            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImagePicker.Companion.with(MainActivity.this).cameraOnly().start(REQUEST_CAMERA);
                }
            });

            btnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImagePicker.Companion.with(MainActivity.this).galleryOnly().start(REQUEST_GALLERY);
                }
            });

            alertDialog = dialogBuilder.create();
            alertDialog.show();
        } else if (v == findViewById(R.id.btnUpload)) {

            String desc = etDesc.getText().toString();
            if (TextUtils.isEmpty(desc)) {
                Snackbar.make(etDesc, getString(R.string.empty_desc), Snackbar.LENGTH_SHORT).show();
            } else {
                RequestBody requestBodyDesc = RequestBody.create(MediaType.parse("text/plain"), desc);
//                MultipartBody.Part descPart = MultipartBody.Part.createFormData("title", desc);


                MultipartBody.Part file;
                RequestBody reqPhoto = RequestBody.create(MediaType.parse("image/*"), imgFile);

                file = MultipartBody.Part.createFormData("image_uploaded", imgFile.getName(), reqPhoto);

                ProfilePresenter profilePresenter = new ProfilePresenter(this);
                profilePresenter.uploadProfile(requestBodyDesc, file);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            alertDialog.dismiss();
            if (requestCode == REQUEST_CAMERA) {
                imgUri = data.getData();
                imgFile = ImagePicker.Companion.getFile(data);
                loadImage(imgFile);
            } else {
                if (data != null) {
                    imgUri = data.getData();
                    imgFile = ImagePicker.Companion.getFile(data);
                }
                loadImage(imgFile);
            }
        } else {
            alertDialog.dismiss();
        }
    }

    private void loadImage(File file) {
        imgPreview.setVisibility(View.VISIBLE);
        imgButton.setVisibility(View.GONE);
        Glide.with(MainActivity.this).load(file).into(imgPreview);
    }


    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void showReports(List<DataReports> dataReports) {
        if (dataReports.size() != 0){
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvReports.setLayoutManager(layoutManager);
            ReportsAdapter ar = new ReportsAdapter(this,dataReports);
            rvReports.setAdapter(ar);
        }

    }

    @Override
    public void doneUpload() {
        Toast.makeText(this, getString(R.string.success_upload), Toast.LENGTH_SHORT).show();
        Glide.with(this).clear(imgPreview);
        etDesc.setText("");
        imgButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void failUpload() {
        Toast.makeText(this, getString(R.string.err_server), Toast.LENGTH_SHORT).show();
        Glide.with(this).clear(imgPreview);
        etDesc.setText("");
        imgButton.setVisibility(View.VISIBLE);
    }
}
