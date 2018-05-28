package mvp.com.neteaseapp.content.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mvp.com.neteaseapp.R;

/**
 * Created by wangtao on 2018/5/25.
 */

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NewsRecyclerViewAdapter.RecyclerViewItemClickInterface {
    private static String TAG = "WTF";
    private static final String TITLE = "title";
    private String mTitle = "Default";
    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
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
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_main_layout, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = view.findViewById(R.id.news_recycler_view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        //设置下拉监听，当用户下拉的时候会去执行回调onRefresh()
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // 这句话是为了，第一次进入页面的时候显示加载进度条,并指定距离头部的位置显示24dp
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getActivity(), getDatas());
        mNewsRecyclerViewAdapter.setItemClickInterface(this); //自定义回调接口

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置分隔线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        //设置增加或删除条目的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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

    /**
     * 当滑动手势从上向下滑动时触发刷新时调用。
     */
    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: 下拉刷新进行异步加载数据");
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
    public void onDestroy() {
        super.onDestroy();
        mRecyclerView.clearOnScrollListeners();
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
