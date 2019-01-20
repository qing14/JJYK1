package asus.com.bwie.jjyk1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.jjyk1.R;
import asus.com.bwie.jjyk1.bean.ShopCarBean;
import asus.com.bwie.jjyk1.utils.JiaJianView;

public class ShangPinAdapter extends RecyclerView.Adapter<ShangPinAdapter.ViewHolder> {
    private Context context;
    private List<ShopCarBean.DataBean.ListBean> list=new ArrayList<>();

    public ShangPinAdapter(Context context, List<ShopCarBean.DataBean.ListBean> listBeans) {
        this.context = context;
        this.list = listBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.shangpin_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String replace = list.get(position).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(context).load(replace).into(holder.shangpinImg);
        holder.shangpinName.setText(list.get(position).getTitle());
        holder.shangpinPrice.setText("￥ " + list.get(position).getPrice());
        holder.sp_check.setChecked(list.get(position).isCheck());
        holder.sp_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setCheck(isChecked);
                if (mshopCallBackListener != null) {
                    mshopCallBackListener.callBack();
                }
            }
        });
        holder.jiajianView.setData(this,list,position);
        holder.jiajianView.setOnJiaJian(new JiaJianView.JiajianListener(){
            @Override
            public void jiajian() {
                if (mshopCallBackListener != null) {
                    mshopCallBackListener.callBack();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView shangpinImg;
        private final TextView shangpinName;
        private final TextView shangpinPrice;
        private final CheckBox sp_check;
       private final JiaJianView jiajianView;

        public ViewHolder(View itemView) {
            super(itemView);
            sp_check = itemView.findViewById(R.id.sp_check);
            shangpinImg = itemView.findViewById(R.id.shangpinImg);
            shangpinName = itemView.findViewById(R.id.shangpinName);
            shangpinPrice = itemView.findViewById(R.id.shangpinPrice);
          jiajianView = itemView.findViewById(R.id.jiajianView);
        }
    }
    public void selOrdelAll(boolean isSelectAll){
        for (ShopCarBean.DataBean.ListBean listBean:list){
            listBean.setCheck(isSelectAll);
        }
        notifyDataSetChanged();
    }
    private ShopCallBackListener mshopCallBackListener;

    public void setListener(ShopCallBackListener listeners) {
        this.mshopCallBackListener = listeners;
    }

    public interface ShopCallBackListener {
        void callBack();
    }


}
