package asus.com.bwie.jjyk1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.jjyk1.R;
import asus.com.bwie.jjyk1.bean.ShopCarBean;

public class ShangJiaAdapter extends RecyclerView.Adapter<ShangJiaAdapter.ViewHolder> {
    private Context mContext;
    private List<ShopCarBean.DataBean> list=new ArrayList<>();

    public ShangJiaAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<ShopCarBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.shangjia_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.shangjiaName.setText(list.get(position).getSellerName());
        final ShangPinAdapter shangPinAdapter=new ShangPinAdapter(mContext,list.get(position).getList());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        holder.shangpinRecycle.setLayoutManager(linearLayoutManager);
        holder.shangpinRecycle.setAdapter(shangPinAdapter);
        holder.shangjiaCheckBox.setChecked(list.get(position).isCheck());
        shangPinAdapter.setListener(new ShangPinAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                if (selOrDelSPListener!=null){
                    selOrDelSPListener.selordelsp(list);
                }
                List<ShopCarBean.DataBean.ListBean> listBeans=new ArrayList<>();
                boolean isAllChecked=true;
                for (ShopCarBean.DataBean.ListBean bean:listBeans){
                    if (!bean.isCheck()){
                        isAllChecked=false;
                        break;
                    }
                }
                holder.shangjiaCheckBox.setChecked(isAllChecked);
                list.get(position).setCheck(isAllChecked);

            }
        });
        holder.shangjiaCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setCheck(holder.shangjiaCheckBox.isChecked());
                shangPinAdapter.selOrdelAll(holder.shangjiaCheckBox.isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox shangjiaCheckBox;
        private final TextView shangjiaName;
        private final RecyclerView shangpinRecycle;

        public ViewHolder(View itemView) {
            super(itemView);
            shangjiaCheckBox = itemView.findViewById(R.id.shangjiaCheckBox);
            shangjiaName = itemView.findViewById(R.id.shangjiaName);
            shangpinRecycle = itemView.findViewById(R.id.shangpinRecycle);

        }
    }
    private SelOrDelSPListener selOrDelSPListener;

    public void setSelOrDelSPListener(SelOrDelSPListener selOrDelSPListener) {
        this.selOrDelSPListener = selOrDelSPListener;
    }

    public interface SelOrDelSPListener{
        void selordelsp(List<ShopCarBean.DataBean> list);
    }
}
