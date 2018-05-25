package mvp.com.neteaseapp.login.bean;

/**
 * Created by wangtao on 2018/5/24.
 */

public class User {
    private String mUsername;
    private String mPassword;

    public User(String username, String password) {
        this.mUsername = username;
        this.mPassword = password;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

}
