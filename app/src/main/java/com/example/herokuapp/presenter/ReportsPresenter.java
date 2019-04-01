package com.example.herokuapp.presenter;

import com.example.herokuapp.model.DataReports;
import com.example.herokuapp.retrofit.ServiceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsPresenter {

    private ReportsViewIface dataView;

    public ReportsPresenter(ReportsViewIface view) {
        this.dataView = view;
    }

    public void getReports(){
        dataView.showLoading();

        Call<List<DataReports>> call = ServiceFactory.getService().getReports();
        call.enqueue(new Callback<List<DataReports>>() {
            @Override
            public void onResponse(Call<List<DataReports>> call, Response<List<DataReports>> response) {
                dataView.hideLoading();
                if (response.isSuccessful()){
                    List<DataReports> reports = response.body();
                    dataView.showReports(reports);
                }
            }

            @Override
            public void onFailure(Call<List<DataReports>> call, Throwable t) {
                dataView.hideLoading();
            }
        });
    }
}
