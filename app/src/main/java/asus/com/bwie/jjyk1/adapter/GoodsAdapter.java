package asus.com.bwie.jjyk1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import asus.com.bwie.jjyk1.R;
import asus.com.bwie.jjyk1.activity.ShopListActivity;
import asus.com.bwie.jjyk1.bean.RightBean;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private List<RightBean.DataBean.ListBean> listBeans;
    private Context mContext;

    public GoodsAdapter(List<RightBean.DataBean.ListBean> listBean, Context mContext) {
        this.listBeans = listBean;
        this.mContext = mContext;
    }

    public void setListBeans(List<RightBean.DataBean.ListBean> listBeans) {
        this.listBeans = listBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.goods_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(mContext).load(listBeans.get(position).getIcon()).into(holder.goodsimg);
        holder.goodstitle.setText(listBeans.get(position).getName());
        holder.goodsitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ShopListActivity.class);
                intent.putExtra("pscid",listBeans.get(position).getPscid()+"");
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView goodsimg;
        private final TextView goodstitle;
        private final LinearLayout goodsitem;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsimg = itemView.findViewById(R.id.goodsimg);
            goodstitle = itemView.findViewById(R.id.goodstitle);
            goodsitem = itemView.findViewById(R.id.goodsitem);
        }
    }
    private GoodsClickListener goodClickListener;

    public void setLeftItemClickListener(GoodsClickListener goodsClickListener) {
        this.goodClickListener = goodsClickListener;
    }

    public interface GoodsClickListener{
        void onClick(int position,String cid);
    }

}
