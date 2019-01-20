package asus.com.bwie.jjyk1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.jjyk1.Apis;
import asus.com.bwie.jjyk1.R;
import asus.com.bwie.jjyk1.adapter.LeftAdapter;
import asus.com.bwie.jjyk1.adapter.RightAdapter;
import asus.com.bwie.jjyk1.bean.LeftBean;
import asus.com.bwie.jjyk1.bean.RightBean;
import asus.com.bwie.jjyk1.presenter.PresenterImpl;
import asus.com.bwie.jjyk1.view.IView;

public class MainActivity extends AppCompatActivity implements IView {

    private RecyclerView leftRecycle;
    private RecyclerView rightRecycle;
    private PresenterImpl presenter;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private Button go_shopCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leftRecycle = findViewById(R.id.leftRecycleView);
        rightRecycle = findViewById(R.id.rightRecycle);
        presenter = new PresenterImpl(this);
        leftAdapter = new LeftAdapter(this);
        rightAdapter = new RightAdapter(this);
        go_shopCar = findViewById(R.id.go_shopCar);
        initLeft();
        initRight();
        go_shopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShopCarActivity.class);
                startActivity(intent);
            }
        });
    }



    private void initLeft() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        leftRecycle.setLayoutManager(linearLayoutManager);
        leftRecycle.setAdapter(leftAdapter);
        Map<String,String> map=new HashMap<>();
        presenter.onRequestPost(Apis.TypeLeftPath,map,LeftBean.class);
        leftAdapter.setLeftItemClickListener(new LeftAdapter.onLeftItemClickListener() {
            @Override
            public void onClick(int position, String cid) {
                getData(cid);
                Toast.makeText(MainActivity.this,cid+"",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getData(String uid) {
        Map<String,String> map=new HashMap<>();
        map.put("cid",uid);
        presenter.onRequestPost(Apis.TypeShopPath,map,RightBean.class);

    }

    private void initRight() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rightRecycle.setLayoutManager(linearLayoutManager);
        rightRecycle.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rightRecycle.setAdapter(rightAdapter);
    }

    @Override
    public void onSuccessData(Object data) {
        if (data instanceof LeftBean){
            LeftBean leftBean= (LeftBean) data;
            leftAdapter.setList(leftBean.getData());
        }else if (data instanceof RightBean){
            RightBean rightBean= (RightBean) data;
            rightAdapter.setList(rightBean.getData());
        }
    }

    @Override
    public void onFailData(Exception e) {

    }
}
