package asus.com.bwie.jjyk1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.jjyk1.Apis;
import asus.com.bwie.jjyk1.R;
import asus.com.bwie.jjyk1.adapter.ShopListAdapter;
import asus.com.bwie.jjyk1.bean.AddShopCarBean;
import asus.com.bwie.jjyk1.bean.ShopListBean;
import asus.com.bwie.jjyk1.presenter.PresenterImpl;
import asus.com.bwie.jjyk1.view.IView;

public class ShopListActivity extends AppCompatActivity implements IView {

    private ShopListAdapter shopListAdapter;
    private RecyclerView shopListRecycle;
    private PresenterImpl presenter;
    private String pscid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        Intent intent = getIntent();
        pscid = intent.getStringExtra("pscid");
        Toast.makeText(this,""+ pscid,Toast.LENGTH_SHORT).show();
        shopListAdapter = new ShopListAdapter(this);
        shopListRecycle = findViewById(R.id.shopListRecycle);

        presenter = new PresenterImpl(this);
        getData();
        shopListAdapter.setAddShopListListener(new ShopListAdapter.AddShopListener() {
            @Override
            public void onClick(int position) {
                Map<String,String> map=new HashMap<>();
                map.put("uid","71");
                map.put("pid",position+"");
                presenter.onRequestPost(Apis.AddShopCarPath,map,AddShopCarBean.class);
            }
        });
    }

    private void getData() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shopListRecycle.setLayoutManager(linearLayoutManager);
        shopListRecycle.setAdapter(shopListAdapter);
        Map<String,String> map=new HashMap<>();
        map.put("pscid",pscid);
        presenter.onRequestPost(Apis.ShopListPath,map,ShopListBean.class);
    }
    @Override
    public void onSuccessData(Object data) {
        if (data instanceof ShopListBean){
            ShopListBean shopListBean= (ShopListBean) data;
            shopListAdapter.setList(shopListBean.getData());

        }else if (data instanceof AddShopCarBean ){
            AddShopCarBean addShopCarBean= (AddShopCarBean) data;
            Toast.makeText(ShopListActivity.this,addShopCarBean.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailData(Exception e) {

    }
}
