package as.bwei.com.gouwu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import as.bwei.com.gouwu.adapter.CartAdapter;
import as.bwei.com.gouwu.adapter.CartAllCheckboxListener;
import as.bwei.com.gouwu.bean.CartBean;
import as.bwei.com.gouwu.common.Constants;
import as.bwei.com.gouwu.presenter.CartPresenter;
import as.bwei.com.gouwu.view.IcartView;

public class MainActivity extends AppCompatActivity implements IcartView,CartAllCheckboxListener{

    private CartPresenter cartPresenter;
    private XRecyclerView xRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartBean.DataBean> list;
    private CheckBox allCheckbox;
    private TextView totalPriceTv;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        list = new ArrayList<>();
        xRecyclerView = (XRecyclerView) findViewById(R.id.cartGV);
        allCheckbox = (CheckBox) findViewById(R.id.allCheckbox);
        //设置线性布局管理器，listview的列表样式
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        totalPriceTv = (TextView) findViewById(R.id.totalpriceTv);

        xRecyclerView.setLoadingMoreEnabled(true);//支持加载更多
        //下拉刷新上拉加载
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {//刷新
                page =1;
                loadData();
            }

            @Override
            public void onLoadMore() {//加载
                page++;
                loadData();
            }
        });
    }
    //请求数据
    private void initData() {
        loadData();
    }
    //请求数据
    private void loadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", "71");
        params.put("page",page+"");

        cartPresenter = new CartPresenter(this);
        cartPresenter.getCarts(params, Constants.GETCARTS);
    }

    @Override
    public void notifyAllCheckboxStatus() {

    }

    @Override
    public void success(CartBean cartBean) {
        //展示列表数据
        if (cartBean != null && cartBean.getData() != null) {
            if (page==1){
                list = cartBean.getData();
                cartAdapter = new CartAdapter(this, list);

                xRecyclerView.setAdapter(cartAdapter);
                xRecyclerView.refreshComplete();
            }else {
                if (cartAdapter!=null){
                    cartAdapter.addPageData(cartBean.getData());
                }
                xRecyclerView.loadMoreComplete();
            }
            cartAdapter.setCartAllCheckboxListener(this);

        }
    }

    @Override
    public void failure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
