package as.bwei.com.gouwu.presenter;

import java.util.HashMap;

import as.bwei.com.gouwu.bean.CartBean;
import as.bwei.com.gouwu.model.CartModel;
import as.bwei.com.gouwu.view.IcartView;

/**
 * Created by HP on 2018/9/18.
 */

public class CartPresenter {
    private CartModel cartModel;
    private IcartView icartView;

    public CartPresenter(IcartView icartView) {
        cartModel =new CartModel();
        attachView(icartView);
    }

    private void attachView(IcartView icartView) {
        this.icartView = icartView;
    }

    public void getCarts(HashMap<String,String> params,String url){
        cartModel.getCarts(params, url, new CartModel.CartCallback() {
            @Override
            public void success(CartBean cartBean) {
                if (icartView!=null){
                    icartView.success(cartBean);
                }
            }

            @Override
            public void fail(String msg) {
                if (icartView!=null){
                    icartView.failure(msg);
                }
            }
        });
    }

    //解除绑定
    public void detachView(){
        this.icartView = null;
    }
}
