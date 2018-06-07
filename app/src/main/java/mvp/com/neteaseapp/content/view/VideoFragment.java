package mvp.com.neteaseapp.content.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mvp.com.neteaseapp.util.LogUtil;

/**
 * Created by wangtao on 2018/5/29.
 */

public class VideoFragment extends BaseFragment implements VideoRecyclerViewAdapter.RecyclerViewItemClickInterface{
    private VideoRecyclerViewAdapter mVideoRecyclerViewAdapter;
    private int mLastVisibleItem;

    public static VideoFragment getInstance(String title) {
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        videoFragment.setArguments(bundle);
        return videoFragment;
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
        mVideoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getActivity(), getUrlData());
        mVideoRecyclerViewAdapter.setItemClickInterface(this); //自定义回调接口

        mRecyclerView.setAdapter(mVideoRecyclerViewAdapter);
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
                        (mLastVisibleItem + 1) == mVideoRecyclerViewAdapter.getItemCount()) { //滑到最后一个item
                    LogUtil.d(TAG, "onScrollStateChanged: 滑到最后一个item 进行异步加载数据");
                    mVideoRecyclerViewAdapter.addDataInFooter(getUrlData());
                    mVideoRecyclerViewAdapter.notifyDataSetChanged();
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
    void refreshAndLoadData() {
        mVideoRecyclerViewAdapter.addDataInHeader(getUrlData());
        mVideoRecyclerViewAdapter.notifyDataSetChanged();

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 模拟网络加载时间，设置不可见
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    private ArrayList<String> getUrlData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            list.add("http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4");
        return list;
    }

    @Override
    public void recyclerViewItemClick() {

    }
}
