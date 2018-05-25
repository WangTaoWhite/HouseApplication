package mvp.com.neteaseapp.login.request;

/**
 * Created by wangtao on 2018/5/24.
 */

public interface LoginRequestCallBack {
    void loginFail(String code);

    void loginSuccess(String response);
}
