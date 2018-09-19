package as.bwei.com.gouwu.view;

import as.bwei.com.gouwu.bean.CartBean;

/**
 * Created by HP on 2018/9/18.
 */

public interface IcartView {
    void success(CartBean cartBean);
    void failure(String msg);
}
