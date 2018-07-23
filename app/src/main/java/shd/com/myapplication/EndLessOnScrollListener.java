package shd.com.myapplication;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * 底部自動加載的Listener
 */
public abstract class EndLessOnScrollListener extends  RecyclerView.OnScrollListener{

    //聲明一個LinearLayoutManager
    private LinearLayoutManager layoutManager;

    //recyclerview的分頁,當前頁，從0開始
    private int currentPage = 0;

    //已經載入出來的Item的數量
    private int totalItemCount;

    //主要用來存儲上一個totalItemCount
    private int previousTotal = 0;

    //在螢幕上可見的item數量
    private int visibleItemCount;

    //在螢幕可見的Item中的第一個
    private int firstVisibleItem;

    private int lastVisibleItem;

    //是否正在上拉資料
    private boolean loading = true;

    public EndLessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    /**
     * 改寫RecyclerView.OnScrollListener的方法
     *
     * */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();//獲取可見的item數目
        totalItemCount = layoutManager.getItemCount();//獲取所有的item數目
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();//當前RecycelrView中第一個可見的item的adapter postion
        lastVisibleItem = layoutManager.findLastVisibleItemPosition();//最後一個可見item

        if(loading){

            Log.d("wnwn","firstVisibleItem: " +firstVisibleItem);
            Log.d("wnwn","totalPageCount:" +totalItemCount);
            Log.d("wnwn", "visibleItemCount:" + visibleItemCount);

            if(totalItemCount > previousTotal){
                //说明数据已经加载结束
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        //这里需要好好理解
        /*數據已經加載結束而且底部刷新View已经可見的時候*/
        if (!loading && lastVisibleItem == totalItemCount - 1){
            currentPage ++;
            onLoadMore(currentPage);
            loading = true;
        }

    }

    /**
     * 提供一个抽象方法，在Activity中監聽到這個EndLessOnScrollListener
     * 並且實現這個方法
     * */
    public abstract void onLoadMore(int currentPage);
}
