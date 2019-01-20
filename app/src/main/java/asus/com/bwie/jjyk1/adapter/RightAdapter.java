package asus.com.bwie.jjyk1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.jjyk1.R;
import asus.com.bwie.jjyk1.bean.RightBean;

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.ViewHolder> {
    private Context mContext;
    private List<RightBean.DataBean> list;

    public RightAdapter(Context mContext) {
        this.mContext = mContext;
        this.list = new ArrayList<>();
    }

    public void setList(List<RightBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.right_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.righttitle.setText(list.get(position).getName());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,3);
        holder.right_goodsRecycle.setLayoutManager(gridLayoutManager);
        GoodsAdapter goodsAdapter=new GoodsAdapter(list.get(position).getList(),mContext);
         holder.right_goodsRecycle.setAdapter(goodsAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView righttitle;
        private final RecyclerView right_goodsRecycle;

        public ViewHolder(View itemView) {
            super(itemView);
            righttitle = itemView.findViewById(R.id.righttitle);
            right_goodsRecycle = itemView.findViewById(R.id.right_goodsRecycle);


        }
    }
}
