package asus.com.bwie.jjyk1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asus.com.bwie.jjyk1.Apis;
import asus.com.bwie.jjyk1.R;
import asus.com.bwie.jjyk1.adapter.ShangJiaAdapter;
import asus.com.bwie.jjyk1.bean.ShopCarBean;
import asus.com.bwie.jjyk1.presenter.PresenterImpl;
import asus.com.bwie.jjyk1.view.IView;

public class ShopCarActivity extends AppCompatActivity implements IView {

    private PresenterImpl presenter;
    private CheckBox allClick;
    private TextView allMoney;
    private TextView allNum;
    private RecyclerView shopCarrecyclerView;
    private ShopCarBean shopCarBean;
    private List<ShopCarBean.DataBean> beanData=new ArrayList<>();
    private ShangJiaAdapter shangJiaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        presenter = new PresenterImpl(this);
        shangJiaAdapter = new ShangJiaAdapter(this);
        initView();
        getData();
        allClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSeller(allClick.isChecked());
                shangJiaAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initView() {
        allClick = findViewById(R.id.AllClick);
        allMoney = findViewById(R.id.AllMoney);
        allNum = findViewById(R.id.AllNum);
        shopCarrecyclerView = findViewById(R.id.shopCarRecycle);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shopCarrecyclerView.setLayoutManager(linearLayoutManager);
        shopCarrecyclerView.setAdapter(shangJiaAdapter);
        shangJiaAdapter.setSelOrDelSPListener(new ShangJiaAdapter.SelOrDelSPListener() {
            @Override
            public void selordelsp(List<ShopCarBean.DataBean> list) {
                double totalPrice=0;
                int num=0;
                int totalNum=0;
                for (int a=0;a<list.size();a++){
                    List<ShopCarBean.DataBean.ListBean> listAll=list.get(a).getList();
                    for (int i=0;i<listAll.size();i++){
                        totalNum=totalNum+listAll.get(i).getNum();
                        if (listAll.get(i).isCheck()){
                            totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                            num = num + listAll.get(i).getNum();
                        }
                    }

                }
                if (num<totalNum){
                    allClick.setChecked(false);
                }else {
                    allClick.setChecked(true);
                }
                allMoney.setText("合计:"+totalPrice);
                allNum.setText("去结算（"+num+")");


            }
        });



    }

    private void getData() {
        Map<String,String> map=new HashMap<>();
        map.put("uid",71+"");
        presenter.onRequestPost(Apis.ShopCarPath,map,ShopCarBean.class);
    }

    @Override
    public void onSuccessData(Object data) {
        if (data instanceof ShopCarBean){
            shopCarBean = (ShopCarBean) data;
            beanData = shopCarBean.getData();
            if (beanData!=null){
                beanData.remove(0);
                shangJiaAdapter.setList(beanData);
            }

        }

    }

    @Override
    public void onFailData(Exception e) {

    }

    private void checkSeller(boolean bool) {
        double totalPrice=0;
        int num=0;
        for (int a=0;a<beanData.size();a++){
            ShopCarBean.DataBean dataBean = beanData.get(a);
            dataBean.setCheck(bool);
            List<ShopCarBean.DataBean.ListBean> listAll=beanData.get(a).getList();
            for (int i=0;i<listAll.size();i++){
                listAll.get(i).setCheck(bool);
                totalPrice=totalPrice+(listAll.get(i).getPrice()*listAll.get(i).getNum());
                num=num+listAll.get(i).getNum();
            }
        }
        if (bool){
            allMoney.setText("合计："+totalPrice);
            allNum.setText("去结算：（"+num+")");

        }else {
            allMoney.setText("合计：00.00");
            allNum.setText("去结算（0）");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
