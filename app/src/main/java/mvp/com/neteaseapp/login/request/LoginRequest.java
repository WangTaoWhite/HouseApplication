package mvp.com.neteaseapp.login.request;

import android.util.Log;

import mvp.com.neteaseapp.Constants;
import mvp.com.neteaseapp.RetrofitRequestInterface;
import mvp.com.neteaseapp.login.bean.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangtao on 2018/5/24.
 * login 网络请求
 */

public class LoginRequest {

    private static final String TAG = "WTF";

    public static void login(String name, String pwd, final LoginRequestCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();

        //构建请求接口类
        RetrofitRequestInterface retrofitRequestInterface = retrofit.create(RetrofitRequestInterface.class);
        //获取login对应的Call对象
        Call<LoginResponse> loginCall = retrofitRequestInterface.doLogin(name, pwd);
        //异步执行网络请求login
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse response1 = response.body();
                Log.d(TAG, "onResponse: code = " + response1.getCode());
                callBack.loginSuccess(response1.getStates());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String msg = t.getMessage();
                Log.d(TAG, "onFailure: msg = " + msg);
                callBack.loginFail(msg);
            }
        });
    }
}
