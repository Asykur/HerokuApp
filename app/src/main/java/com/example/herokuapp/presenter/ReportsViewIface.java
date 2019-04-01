package com.example.herokuapp.presenter;

import com.example.herokuapp.model.DataReports;

import java.util.List;

public interface ReportsViewIface {
    void showLoading();
    void hideLoading();
    void showReports(List<DataReports> dataReports);
    void doneUpload();
    void failUpload();
}
