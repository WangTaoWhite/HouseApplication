package mvp.com.neteaseapp.content.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by wangtao on 2018/5/25.
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private BaseFragment[] mNewsFragment;

    public ViewPagerFragmentAdapter(FragmentManager fragmentManager, BaseFragment[] mFragments) {
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
