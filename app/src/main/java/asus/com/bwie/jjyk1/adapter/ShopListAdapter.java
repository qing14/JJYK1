package asus.com.bwie.jjyk1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.jjyk1.R;
import asus.com.bwie.jjyk1.activity.ParticularsActivity;
import asus.com.bwie.jjyk1.bean.ShopListBean;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {
    private Context mContext;
    private List<ShopListBean.DataBean> list;

    public ShopListAdapter(Context mContext) {
        this.mContext = mContext;
        this.list = new ArrayList<>();
    }

    public void setList(List<ShopListBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.shoplist_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String replace = list.get(position).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(mContext).load(replace).into(holder.shopimg);
        holder.shopname.setText(list.get(position).getTitle());
        holder.shopprice.setText("￥ "+list.get(position).getPrice());
        holder.shopitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext,ParticularsActivity.class);
                intent.putExtra("detailUrl",list.get(position).getDetailUrl());

                mContext.startActivity(intent);

            }
        });
        holder.addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addShopListener!=null){
                    int pid = list.get(position).getPid();
                    addShopListener.onClick(pid);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView shopimg;
        private final TextView shopname;
        private final TextView shopprice;
        private final LinearLayout shopitem;
        private final Button addCarButton;

        public ViewHolder(View itemView) {
            super(itemView);
            shopimg = itemView.findViewById(R.id.shopimg);
            shopname = itemView.findViewById(R.id.shopname);
            shopprice = itemView.findViewById(R.id.shopprice);
            shopitem = itemView.findViewById(R.id.shopitem);
            addCarButton = itemView.findViewById(R.id.addCarButton);


        }
    }

    private GoParticularsClickListener clickListener;

    public void setShopListListener(GoParticularsClickListener goParticularsClickListener) {
        this.clickListener = goParticularsClickListener;
    }

    public interface GoParticularsClickListener{
        void onClick(int position,String detailUrl);
    }

    private AddShopListener addShopListener;
    public void setAddShopListListener(AddShopListener addShopListener) {
        this.addShopListener = addShopListener;
    }

    public interface AddShopListener{
        void onClick(int position);
    }

}
