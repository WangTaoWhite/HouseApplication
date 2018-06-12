package mvp.com.neteaseapp.content.view;

import android.app.Activity;
import android.os.Bundle;

import mvp.com.neteaseapp.R;

/**
 * Created by wangtao on 2018/6/12.
 */

public class DetailViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail_layout);
    }
}
