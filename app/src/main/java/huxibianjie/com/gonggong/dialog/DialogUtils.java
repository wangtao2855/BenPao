package huxibianjie.com.gonggong.dialog;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.home.runmining.R;


@SuppressLint("WrongConstant")
public class DialogUtils {
    private Dialog mDialog;

    public void displayDialog(Context context, View view, int gravity) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        mDialog = new Dialog(context, R.style.MenuDialog);
        mDialog.setContentView(view);
        Window window = mDialog.getWindow();
        window.setGravity(gravity);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.PopMenuAnimation);
        WindowManager.LayoutParams mParams = mDialog.getWindow().getAttributes();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        mParams.width = display.getWidth();
        mDialog.getWindow().setAttributes(mParams);
    }


    public void displayDialogFull(Context context, View view, int gravity) {
        if (context == null) {
            return;
        }
        mDialog = new Dialog(context, R.style.MenuDialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        Window window = mDialog.getWindow();
        window.setGravity(gravity);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.PopMenuAnimation);
        WindowManager.LayoutParams mParams = mDialog.getWindow().getAttributes();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        mParams.width = display.getWidth();
        mParams.height = display.getHeight();
        mDialog.getWindow().setAttributes(mParams);
    }

    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        } else {
            return false;
        }
    }
}
