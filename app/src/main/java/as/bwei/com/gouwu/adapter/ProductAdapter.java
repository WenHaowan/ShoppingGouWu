package as.bwei.com.gouwu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import as.bwei.com.gouwu.R;
import as.bwei.com.gouwu.bean.CartBean;
import as.bwei.com.gouwu.widget.MyJIaJianView;

/**
 * Created by HP on 2018/9/18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CartViewHolder>{

    private Context mContext;
    private List<CartBean.DataBean.ListBean> listBeanList;
    private CartCheckListener checkListener;
    private CartAllCheckboxListener cartAllCheckboxListener;

    public ProductAdapter(Context context, List<CartBean.DataBean.ListBean> list) {
        mContext = context;
        listBeanList = list;
    }

    public void setCheckListener(CartCheckListener checkListener) {
        this.checkListener = checkListener;
    }

    public void setCartAllCheckboxListener(CartAllCheckboxListener cartAllCheckboxListener) {
        this.cartAllCheckboxListener = cartAllCheckboxListener;
    }

    @Override
    public ProductAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.product_item_layout,parent,false);
        CartViewHolder viewHolder = new CartViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.CartViewHolder holder, int position) {
        final CartBean.DataBean.ListBean bean = listBeanList.get(position);

        holder.priceTv.setText("优惠价：¥" + bean.getBargainPrice());
        holder.titleTv.setText(bean.getTitle());
        String[] imgs = bean.getImages().split("\\|");

        if (imgs != null && imgs.length>0){
            Glide.with(mContext).load(imgs[0]).into(holder.productIv);
        }else {
            holder.productIv.setImageResource(R.mipmap.ic_launcher);
        }
        holder.checkBox.setChecked(bean.isSelected());

        holder.myJIaJianView.setNumEt(bean.getTotalNum());

        holder.myJIaJianView.setJiaJianListener(new MyJIaJianView.JiaJianListener() {
            @Override
            public void getNum(int num) {
                bean.setTotalNum(num);
                if (checkListener!=null){
                    checkListener.notifyParent();
                }
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isSelected()){
                    bean.setSelected(true);
                }else {
                    bean.setSelected(false);
                }
                if (checkListener!=null){
                    checkListener.notifyParent();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeanList.size() == 0 ? 0 : listBeanList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView titleTv, priceTv;
        private ImageView productIv;
        private MyJIaJianView myJIaJianView;

        public CartViewHolder(View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.productCheckbox);
            titleTv = (TextView) itemView.findViewById(R.id.title);
            priceTv = (TextView) itemView.findViewById(R.id.price);
            productIv = (ImageView) itemView.findViewById(R.id.product_icon);
            myJIaJianView = (MyJIaJianView) itemView.findViewById(R.id.jiajianqi);

        }
    }
}
