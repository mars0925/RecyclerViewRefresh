package shd.com.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wnw on 16-5-26.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickLitener onItemClickLitener = null;
    private static final int TYPE_NORMAL_ITEM = 0;//一般item
    private static final int TYPE_BOTTOM_REFRESH_ITEM = 1;//底部更新的按鈕
    //定义一个集合，接收从Activity中传递过来的数据和上下文
    private List<String> mList;
    private Context mContext;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickLitener listener) {
        this.onItemClickLitener = listener;
    }

    MyAdapter(Context context, List<String> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }//增加底部加1

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根據不同的位置建立不同的view,如果是底部的話,回傳底部的view
        if (viewType == TYPE_BOTTOM_REFRESH_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerrview_bottom, parent, false);
            return new BottomViewHolder(view);
        }
        // 如果是其他類型的View，則按照正常流程創建普通的ViewHolder
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
            return new MyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        /*根據不同的view綁定不同的元件*/
        if (holder instanceof MyHolder){
            final String itemText = mList.get(position);
            ((MyHolder)holder).tv.setText(itemText);
        }
        else if (holder instanceof BottomViewHolder){
            Button b_loadmore = ((BottomViewHolder)holder).b_loadmore;

            /*底部加載按鈕事件*/
            b_loadmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mList.size()) {
            return TYPE_NORMAL_ITEM;
        } else {
            return TYPE_BOTTOM_REFRESH_ITEM;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView tv;
        MyHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.list_item);
        }
    }

    // 定義底部刷新View對應的ViewHolder
    class BottomViewHolder extends RecyclerView.ViewHolder {
        Button b_loadmore;

        BottomViewHolder(View itemView) {
            super(itemView);
            b_loadmore = itemView.findViewById(R.id.b_loadmore);
        }
    }




}
