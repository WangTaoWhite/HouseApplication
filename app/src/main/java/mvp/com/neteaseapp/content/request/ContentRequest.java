package mvp.com.neteaseapp.content.request;

import mvp.com.neteaseapp.Constants;
import mvp.com.neteaseapp.RetrofitRequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangtao on 2018/6/12.
 */

public class ContentRequest {
    public static void getNewsRequest(final ContentRequestCallBack contentRequestCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();
        //构建请求接口类
        RetrofitRequestInterface retrofitRequestInterface = retrofit.create(RetrofitRequestInterface.class);
        //获取login对应的Call对象
        Call<ContentResponse> contentRequestCall = retrofitRequestInterface.getContentRequest();

        contentRequestCall.enqueue(new Callback<ContentResponse>() {
            @Override
            public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                contentRequestCallBack.getRequestSuccess();
            }

            @Override
            public void onFailure(Call<ContentResponse> call, Throwable t) {
                contentRequestCallBack.getRequestFail();
            }
        });
    }
}
