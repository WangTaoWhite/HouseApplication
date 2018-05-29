package mvp.com.neteaseapp.content.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by wangtao on 2018/5/25.
 */

public class NewsFragment extends BaseFragment implements NewsRecyclerViewAdapter.RecyclerViewItemClickInterface{
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private int mLastVisibleItem;

    public static NewsFragment getInstance(String title) {
        NewsFragment newsFragment = new NewsFragment();
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

        mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getActivity(), getDatas());
        mNewsRecyclerViewAdapter.setItemClickInterface(this); //自定义回调接口

        mRecyclerView.setAdapter(mNewsRecyclerViewAdapter);
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
                        (mLastVisibleItem + 1) == mNewsRecyclerViewAdapter.getItemCount()) { //滑到最后一个item
                    Log.d(TAG, "onScrollStateChanged: 滑到最后一个item 进行异步加载数据");
                    mNewsRecyclerViewAdapter.addDataInFooter(getDatas());
                    mNewsRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                Log.d(TAG, "onScrolled: mLastVisibleItem = " + mLastVisibleItem);
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    void refreshAndLoadData() {
        mNewsRecyclerViewAdapter.addDataInHeader(getDatas());
        mNewsRecyclerViewAdapter.notifyDataSetChanged();

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
        Log.d(TAG, "recyclerViewItemClick: ");
    }

    private ArrayList<String> getDatas() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            list.add("item" + i + Math.random());
        return list;
    }

}
