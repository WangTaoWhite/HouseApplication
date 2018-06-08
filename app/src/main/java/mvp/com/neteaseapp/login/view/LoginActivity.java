package mvp.com.neteaseapp.login.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mvp.com.neteaseapp.R;
import mvp.com.neteaseapp.content.MainActivity;
import mvp.com.neteaseapp.login.presenter.LoginPresenter;


public class LoginActivity extends Activity implements ViewInterface, View.OnClickListener {

    private LoginPresenter mLoginPresenter;
    private EditText mPwdEdit, mNameEdit;
    private TextInputLayout mPwdEditInputLayout, mNameEditInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        initView();
        initOnClick();
    }

    private void initView() {
        mLoginPresenter = new LoginPresenter(this);
        mPwdEdit = findViewById(R.id.pwd_edit);
        mNameEdit = findViewById(R.id.name_edit);
        mPwdEditInputLayout = findViewById(R.id.pwd_edit_input_layout);
        mNameEditInputLayout = findViewById(R.id.name_edit_input_layout);
    }

    private void initOnClick() {
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.forget_pwd_tv).setOnClickListener(this);
        findViewById(R.id.register_tv).setOnClickListener(this);
        findViewById(R.id.weixin_img).setOnClickListener(this);
        findViewById(R.id.qq_img).setOnClickListener(this);
        findViewById(R.id.sina_img).setOnClickListener(this);
        mPwdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if (string.length() > 20) {
                    mPwdEditInputLayout.setErrorEnabled(true);
                    mPwdEditInputLayout.setError("输入长度不能大于20！");
                } else {
                    mPwdEditInputLayout.setErrorEnabled(false);
                }
            }
        });

        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if (string.length() > 10) {
                    mNameEditInputLayout.setErrorEnabled(true);
                    mNameEditInputLayout.setError("输入长度不能大于10！");
                } else {
                    mNameEditInputLayout.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    public void loginFail(String code) {
        Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginSuccess(String response) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startLogin(String name, String pwd) {
        if (name == null || pwd == null) {
            Toast.makeText(this, getString(R.string.account_hint), Toast.LENGTH_SHORT).show();
        }
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
