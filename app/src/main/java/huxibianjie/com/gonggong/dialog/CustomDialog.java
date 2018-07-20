/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package huxibianjie.com.gonggong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.runmining.R;

import huxibianjie.com.gonggong.view.LoadingView;

import static android.view.View.inflate;


public class CustomDialog extends Dialog {
    static TextView tvinfo = null;

    public CustomDialog(Context context) {
        this(context, 0);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static CustomDialog instance(final Context context) {
        View view = inflate(context, R.layout.common_progress_view, null);
        LoadingView v = (LoadingView) view.findViewById(R.id.loading);
        v.setColor(ContextCompat.getColor(context, R.color.common));
        CustomDialog dialog = new CustomDialog(context, R.style.loading_dialog);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        );
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
