package mvp.com.neteaseapp.login.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mvp.com.neteaseapp.R;
import mvp.com.neteaseapp.login.presenter.LoginPresenter;


public class LoginActivity extends Activity implements ViewInterface, View.OnClickListener {

    private LoginPresenter mLoginPresenter;
    private EditText mPwdEdit, mNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mLoginPresenter = new LoginPresenter(this);
    }

    private void initView() {
        mPwdEdit = findViewById(R.id.pwd_edit);
        mNameEdit = findViewById(R.id.name_edit);
    }

    private void initOnClick() {
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.forget_pwd_tv).setOnClickListener(this);
        findViewById(R.id.register_tv).setOnClickListener(this);
        findViewById(R.id.weixin_img).setOnClickListener(this);
        findViewById(R.id.qq_img).setOnClickListener(this);
        findViewById(R.id.sina_img).setOnClickListener(this);
    }


    @Override
    public void loginFail(String code) {

    }

    @Override
    public void loginSuccess(String response) {

    }

    private void startLogin(String name, String pwd) {
        mLoginPresenter.doLogin(name, pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                startLogin(mNameEdit.getText().toString(), mPwdEdit.getText().toString());
                break;
            case R.id.forget_pwd_tv:
                break;
            case R.id.register_tv:
                break;
            case R.id.weixin_img:
                break;
            case R.id.qq_img:
                break;
            case R.id.sina_img:
                break;
        }
    }
}
