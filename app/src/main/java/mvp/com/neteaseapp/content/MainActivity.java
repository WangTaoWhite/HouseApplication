package mvp.com.neteaseapp.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mvp.com.neteaseapp.R;
import mvp.com.neteaseapp.content.view.BaseFragment;
import mvp.com.neteaseapp.content.view.NewsFragment;
import mvp.com.neteaseapp.content.view.VideoFragment;
import mvp.com.neteaseapp.content.view.ViewPagerFragmentAdapter;
import mvp.com.neteaseapp.customview.ColorTextView;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private int mPrePosition = 0;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageView mMenuImage;

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
        mMenuImage = findViewById(R.id.menu_image);
        mViewPager = findViewById(R.id.content_viewpager);

        mFragments[0] = NewsFragment.getInstance(mTitles[0]);
        mFragments[1] = VideoFragment.getInstance(mTitles[1]);
        mFragments[2] = NewsFragment.getInstance(mTitles[2]);
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
        mMenuImage.setOnClickListener(this);

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
            case R.id.menu_image:
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


        }
    }
}
