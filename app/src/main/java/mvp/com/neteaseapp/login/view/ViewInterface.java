package mvp.com.neteaseapp.login.view;

/**
 * Created by wangtao on 2018/5/24.
 */

public interface ViewInterface {
    void loginFail(String code);

    void loginSuccess(String response);
}
