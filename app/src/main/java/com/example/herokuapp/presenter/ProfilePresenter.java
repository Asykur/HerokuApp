package com.example.herokuapp.presenter;
import com.example.herokuapp.model.DataProfile;
import com.example.herokuapp.retrofit.ServiceFactory;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter {

    private ReportsViewIface viewIface;

    public ProfilePresenter(ReportsViewIface viewIface) {
        this.viewIface = viewIface;
    }

    public void uploadProfile(RequestBody desc, final MultipartBody.Part file){
        viewIface.showLoading();
        Call<DataProfile> call = ServiceFactory.getService().uploadProfile(desc,file);
        call.enqueue(new Callback<DataProfile>() {
            @Override
            public void onResponse(Call<DataProfile> call, Response<DataProfile> response) {
                viewIface.hideLoading();
                int code = response.code();
                if (response.isSuccessful()){
                    viewIface.doneUpload();
                }else {
                    viewIface.failUpload();
                }
            }

            @Override
            public void onFailure(Call<DataProfile> call, Throwable t) {
                viewIface.hideLoading();
            }
        });

    }
}
