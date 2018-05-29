package mvp.com.neteaseapp;

import mvp.com.neteaseapp.login.bean.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by wangtao on 2018/5/29.
 * Retrofit 定义的描述网络请求的接口
 */

public interface RetrofitRequestInterface {

    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse> doLogin(@Field("username") String name, @Field("password") String pwd);
}
