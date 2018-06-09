package mvp.com.neteaseapp.content.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mvp.com.neteaseapp.util.LogUtil;

/**
 * Created by wangtao on 2018/5/25.
 */

public class PictureFragment extends BaseFragment implements PictureRecyclerViewAdapter.RecyclerViewItemClickInterface{
    private PictureRecyclerViewAdapter mPicRecyclerViewAdapter;
    private int mLastVisibleItem;

    public static PictureFragment getInstance(String title) {
        PictureFragment newsFragment = new PictureFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPicRecyclerViewAdapter = new PictureRecyclerViewAdapter(getActivity(), getDatas());
        mPicRecyclerViewAdapter.setItemClickInterface(this); //自定义回调接口

        //设置分隔线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        mRecyclerView.setAdapter(mPicRecyclerViewAdapter);
        //设置监听滑动状态和位置
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                /**
                 * 上下滑动都会触发该方法
                 * SCROLL_STATE_IDLE表示未滑动
                 */
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        (mLastVisibleItem + 1) == mPicRecyclerViewAdapter.getItemCount()) { //滑到最后一个item
                    LogUtil.d(TAG, "onScrollStateChanged: 滑到最后一个item 进行异步加载数据");
                    mPicRecyclerViewAdapter.addDataInFooter(getDatas());
                    mPicRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                LogUtil.d(TAG, "onScrolled: mLastVisibleItem = " + mLastVisibleItem);
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    void refreshAndLoadData() {
        mPicRecyclerViewAdapter.addDataInHeader(getDatas());
        mPicRecyclerViewAdapter.notifyDataSetChanged();

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 模拟网络加载时间，设置不可见
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void recyclerViewItemClick() {
        LogUtil.d(TAG, "recyclerViewItemClick: ");
    }

    private ArrayList<String> getDatas() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            list.add("中国新闻网是知名的中文新闻门户网站");
        return list;
    }

}
