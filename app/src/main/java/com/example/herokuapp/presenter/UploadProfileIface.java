package com.example.herokuapp.presenter;

import com.example.herokuapp.model.DataReports;

import java.util.List;

public interface UploadProfileIface {
    void showLoading();
    void hideLoading();
    void successUpload();
    void failedUpload();
}
