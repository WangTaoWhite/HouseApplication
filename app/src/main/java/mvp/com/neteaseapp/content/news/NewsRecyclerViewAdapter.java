package mvp.com.neteaseapp.content.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mvp.com.neteaseapp.R;

/**
 * Created by wangtao on 2018/5/25.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public NewsRecyclerViewAdapter(Context context, List<String> datas) {
        mContext = context;
        mDatas = datas;
        inflater = LayoutInflater.from(mContext);
        mDatas.add("A");
        mDatas.add("A");
        mDatas.add("A");
        mDatas.add("A");
        mDatas.add("A");
        mDatas.add("A");
        mDatas.add("A");
        mDatas.add("A");
        mDatas.add("A");
        mDatas.add("A");
        mDatas.add("A");
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_item_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.news_items_text);
        }
    }
}
