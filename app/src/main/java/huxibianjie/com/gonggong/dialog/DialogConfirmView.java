package huxibianjie.com.gonggong.dialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.home.runmining.R;

/**
 * Created by wangtao on 2018/5/10.
 */

public class DialogConfirmView extends View {
    private Context context;
    private DialogUtils dialogUtils;
    private TextView tv_title;
    private TextView tv_message;
    private TextView i_know;

    public DialogConfirmView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DialogConfirmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialogConfirmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dialogUtils = new DialogUtils();
        View loaingview = View.inflate(context, R.layout.loadinginfo_dialog, null);
        tv_title = (TextView) loaingview.findViewById(R.id.tv_title);
        tv_message = (TextView) loaingview.findViewById(R.id.tv_message);
        i_know = (TextView) loaingview.findViewById(R.id.i_know);
        i_know.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                disDialog();
            }
        });
        dialogUtils.displayDialog(context, loaingview, Gravity.CENTER);
    }

    public void showDialog() {
        if (dialogUtils != null) {
            if (!dialogUtils.isShowing()) {
                dialogUtils.showDialog();
            }
        }
    }

    public void disDialog() {
        if (dialogUtils != null) {
            if (dialogUtils.isShowing()) {
                dialogUtils.dismissDialog();
            }
        }
    }

    public void setDialogMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tv_message.setText(msg);
        }
    }

    public void setDialogTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
    }

    public void setDialogOk(String ok) {
        if (!TextUtils.isEmpty(ok)) {
            i_know.setText(ok);
        }
    }

    private DialogConfirmListener dialogConfirmListener;

    public interface DialogConfirmListener {
        void onBtnClick();
    }

    public void setDialogConfirmListener(DialogConfirmListener dialogConfirmListener) {
        this.dialogConfirmListener = dialogConfirmListener;
    }
}
