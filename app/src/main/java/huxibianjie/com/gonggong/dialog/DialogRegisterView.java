package huxibianjie.com.gonggong.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.home.runmining.R;

/**
 * Created by wangtao on 2018/6/15.
 */

public class DialogRegisterView extends View {


    private Context context;
    private DialogUtils dialogUtils;

    public DialogRegisterView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        dialogUtils = new DialogUtils();
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_register, null);
        dialogUtils.displayDialogFull(context, inflate, Gravity.CENTER);
    }

    public void showDialog() {
        if (dialogUtils != null) {
            dialogUtils.showDialog();
        }
    }

    public void DisDialog() {
        if (dialogUtils != null) {
            dialogUtils.dismissDialog();
        }
    }
}