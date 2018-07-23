package shd.com.myapplication;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout s_wipe;
    private RecyclerView r_recyclerView;
    private ArrayList data = new ArrayList<String>();
    private MyAdapter adapter;
    private  LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

        layoutManager = new LinearLayoutManager(this);

        r_recyclerView.setLayoutManager(layoutManager);
        r_recyclerView.setAdapter(adapter);

        /*設定頂部更新的監聽器*/
        s_wipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();//頂部更新的時候要增加的資料
                adapter.notifyDataSetChanged();//更新adapter的資料
                s_wipe.setRefreshing(false);//更新完成後,關閉更新的視圖。
            }
        });


        /*有兩種底部加載的方式*/
//        /**
//         * 1.底部有加載的按鈕,點擊後加載更多資料
//         * */
//        adapter.setOnItemClickListener(new MyAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                loadMoreData();
//            }
//        });

        /**
         * 2.拉到底部之後自動加載更多資料
         * 监听addOnScrollListener这个方法，新建我们的EndLessOnScrollListener
         * 在onLoadMore方法中去完成上拉加载的操作
         * */
        r_recyclerView.addOnScrollListener(new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d("wnwn","currentPage: "  + currentPage);
                loadMoreData();
            }
        });
    }

    //初始化一开始加载的数据
    private void initData(){
        for (int i = 0; i < 20; i++){
            data.add("Item"+i);
        }
    }


    //初始化界面
    private void initView(){
        s_wipe = findViewById(R.id.s_wipe);
        r_recyclerView = findViewById(R.id.r_recyclerView);
        adapter = new MyAdapter(this,data);

        //下拉更新轉圈視圖的顏色
        s_wipe.setColorSchemeResources(
                R.color.red,
                R.color.green,
                R.color.yello
        );
    }

    private void updateData(){
        //我在List最前面加入一条数据
        data.add(0, "嘿，我是“下拉刷新”生出来的");
    }

    //每次底部加载的时候，就加载十条数据到RecyclerView中
    private void loadMoreData(){
        for (int i =0; i < 10; i++){
            data.add("嘿，我是“上拉加载”生出来的"+i);
            adapter.notifyDataSetChanged();
        }
    }
}

