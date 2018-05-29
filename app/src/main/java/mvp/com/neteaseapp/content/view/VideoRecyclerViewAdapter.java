package mvp.com.neteaseapp.content.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import mvp.com.neteaseapp.R;

/**
 * Created by wangtao on 2018/5/25.
 */

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int TYPE_ITEM_ONE = 1;
    private static final int TYPE_FOOTER = 2;

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private RecyclerViewItemClickInterface mItemClickInterface;

    public VideoRecyclerViewAdapter(Context context, List<String> data) {
        mContext = context;
        mDatas = data;
        inflater = LayoutInflater.from(mContext);
    }

    public void addDataInHeader(List<String> data) {
        mDatas.addAll(0, data);
    }

    public void addDataInFooter(List<String> data) {
        mDatas.addAll(data);
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM_ONE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_ONE) {
            View view = inflater.inflate(R.layout.item_video_layout, parent, false);
            return new ItemOneViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.recycleview_footer_layout, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    // RecyclerView的count设置为数据总条数+ 1（footerView）添加了上拉加载布局
    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof ItemOneViewHolder) {
                boolean setUp = ((ItemOneViewHolder) holder).videoView.setUp(mDatas.get(position),
                        JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                if (setUp) {  //设置视频显示的图片
                    Glide.with(mContext).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").
                            into(((ItemOneViewHolder) holder).videoView.thumbImageView);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        mItemClickInterface.recyclerViewItemClick();
    }

    private class ItemOneViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard videoView;

         ItemOneViewHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_item_player);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setItemClickInterface(RecyclerViewItemClickInterface clickInterface) {
        mItemClickInterface = clickInterface;
    }

    interface RecyclerViewItemClickInterface {
        void recyclerViewItemClick();
    }
}
