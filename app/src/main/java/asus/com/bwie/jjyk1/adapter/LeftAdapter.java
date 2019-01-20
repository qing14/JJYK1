package asus.com.bwie.jjyk1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.jjyk1.R;
import asus.com.bwie.jjyk1.bean.LeftBean;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {

    private Context mContext;
    private List<LeftBean.DataBean> list;


    public LeftAdapter(Context mContext) {
        this.mContext = mContext;
        this.list = new ArrayList<>();
    }

    public void setList(List<LeftBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.left_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.typetitle.setText(list.get(position).getName());
        holder.leftlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftItemClickListener!=null){
                    leftItemClickListener.onClick(position,list.get(position).getCid()+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView typetitle;
        private final LinearLayout leftlinear;

        public ViewHolder(View itemView) {
            super(itemView);
            typetitle = itemView.findViewById(R.id.typetitle);
            leftlinear = itemView.findViewById(R.id.leftlinear);
        }
    }
    private onLeftItemClickListener leftItemClickListener;

    public void setLeftItemClickListener(onLeftItemClickListener ItemClickListener) {
        this.leftItemClickListener = ItemClickListener;
    }

    public interface onLeftItemClickListener{
        void onClick(int position,String cid);
    }
}
