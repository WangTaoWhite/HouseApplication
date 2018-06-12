package mvp.com.neteaseapp.login.presenter;

import mvp.com.neteaseapp.login.request.LoginRequest;
import mvp.com.neteaseapp.login.request.LoginRequestCallBack;
import mvp.com.neteaseapp.login.view.ViewInterface;

/**
 * Created by wangtao on 2018/5/24.
 */

public class LoginPresenter implements LoginPresenterInter, LoginRequestCallBack {

    private ViewInterface mViewInterface;

    public LoginPresenter(ViewInterface viewInterface) {
        mViewInterface = viewInterface;
    }

    @Override
    public void doLogin(String name, String pwd) {
        LoginRequest.login(name, pwd, this);
    }

    @Override
    public void loginFail(String code) {
        mViewInterface.loginFail(code);
    }

    @Override
    public void loginSuccess(String response) {
        mViewInterface.loginSuccess(response);
    }
}
