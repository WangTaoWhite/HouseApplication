package mvp.com.neteaseapp.content.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mvp.com.neteaseapp.content.news.NewsFragment;

/**
 * Created by wangtao on 2018/5/25.
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private NewsFragment[] mNewsFragment;

    public ViewPagerFragmentAdapter(FragmentManager fragmentManager, NewsFragment[] mFragments) {
        super(fragmentManager);
        mNewsFragment = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mNewsFragment[position];
    }

    @Override
    public int getCount() {
        return mNewsFragment.length;
    }
}
