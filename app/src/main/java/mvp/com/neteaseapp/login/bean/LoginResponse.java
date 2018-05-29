package mvp.com.neteaseapp.login.bean;

/**
 * Created by wangtao on 2018/5/29.
 * 根据返回数据的格式和数据解析方式（Json、XML等）定义
 */

public class LoginResponse {

    private String mCode;
    private String mStates;

    public String getCode() {
        return mCode;
    }

    public void setCode(String mCode) {
        this.mCode = mCode;
    }

    public String getStates() {
        return mStates;
    }

    public void setStates(String mStates) {
        this.mStates = mStates;
    }
}
