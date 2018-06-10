package mvp.com.neteaseapp.content;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import mvp.com.neteaseapp.R;
import mvp.com.neteaseapp.content.view.BaseFragment;
import mvp.com.neteaseapp.content.view.NewsFragment;
import mvp.com.neteaseapp.content.view.PictureFragment;
import mvp.com.neteaseapp.content.view.VideoFragment;
import mvp.com.neteaseapp.content.view.ViewPagerFragmentAdapter;
import mvp.com.neteaseapp.customview.CircleView;
import mvp.com.neteaseapp.customview.ColorTextView;
import mvp.com.neteaseapp.util.LogUtil;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_GALLERY = 1;
    private int mPrePosition = 0;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView mMenuTitle;
    private CircleView mUserIcon;

    private String[] mTitles = new String[]{"新闻", "视频", "图片", "关注"};
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private BaseFragment[] mFragments = new BaseFragment[mTitles.length];
    private List<ColorTextView> mTabs = new ArrayList<ColorTextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initViewListener();
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationView = findViewById(R.id.navigation_view);
        mMenuTitle = findViewById(R.id.menu_title);
        mViewPager = findViewById(R.id.content_viewpager);
        View view = mNavigationView.getHeaderView(0);
        mUserIcon = view.findViewById(R.id.user_icon);

        mFragments[0] = NewsFragment.getInstance(mTitles[0]);
        mFragments[1] = VideoFragment.getInstance(mTitles[1]);
        mFragments[2] = PictureFragment.getInstance(mTitles[2]);
        mFragments[3] = NewsFragment.getInstance(mTitles[3]);

        mAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);

        mTabs.add((ColorTextView) findViewById(R.id.news_color_text_view));
        mTabs.add((ColorTextView) findViewById(R.id.video_color_text_view));
        mTabs.add((ColorTextView) findViewById(R.id.image_color_text_view));
        mTabs.add((ColorTextView) findViewById(R.id.attention_color_text_view));
        ((ColorTextView) mTabs.get(0)).setProgress(1);  //当前标签为新闻，红色字体
    }

    private void initViewListener() {
        mMenuTitle.setOnClickListener(this);
        mUserIcon.setOnClickListener(this);

        //点击侧滑菜单中的item
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(mNavigationView);
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    ColorTextView left = mTabs.get(position);
                    ColorTextView right = mTabs.get(position + 1);

                    left.setDirection(ColorTextView.DIRECTION_RIGHT);
                    right.setDirection(ColorTextView.DIRECTION_LEFT);

                    left.setProgress(1 - positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                ColorTextView pre = mTabs.get(mPrePosition);
                ColorTextView current = mTabs.get(position);
                pre.setProgress(0);
                current.setProgress(1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < mTabs.size(); i++) {
            mTabs.get(i).setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_title:
                if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                    mDrawerLayout.closeDrawer(mNavigationView);
                } else {
                    mDrawerLayout.openDrawer(mNavigationView);
                }
                break;
            case R.id.news_color_text_view:
                mPrePosition = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(0);
                break;
            case R.id.video_color_text_view:
                mPrePosition = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(1);
                break;
            case R.id.image_color_text_view:
                mPrePosition = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(2);
                break;
            case R.id.attention_color_text_view:
                mPrePosition = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(3);
                break;
            case R.id.user_icon:
                if (requestPermission()) {
                    MultiImageSelector.create()
                            .single()
                            .start(this, REQUEST_CODE_GALLERY);
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY) {
            LogUtil.d("WTF", "onActivityResult: requestCode == REQUEST_CODE_GALLERY");
            if (data != null) {
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path != null) {
                    mUserIcon.setImageBitmap(BitmapFactory.decodeFile(path.get(0)));
                }
            }
        }
    }

    private boolean requestPermission() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> array = new ArrayList<>();
            //判断是否有准许这个权限 GRANTED---授权  DINIED---拒绝
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PERMISSION_GRANTED) {
                array.add(Manifest.permission.CAMERA);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
                array.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
                array.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (array.size() == 0) {  //已经申请
                return true;
            }
            String[] permission = new String[array.size()];
            for (int i = 0; i < array.size(); i++) {
                permission[i] = array.get(i);
            }
            //开始提交请求权限
            ActivityCompat.requestPermissions(this, permission, 1);

            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        MultiImageSelector.create()
                                .single()
                                .start(this, REQUEST_CODE_GALLERY);
                    }

                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
