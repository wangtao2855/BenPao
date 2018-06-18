package huxibianjie.com.gonggong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.runmining.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangtao on 2018/6/11.
 */

public class MoneyAdapter extends RecyclerView.Adapter {

    private Context context;
    private int NUMBER = 4;

    public MoneyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_money, null);
        RecyclerView.ViewHolder viewHolder = new MoneyViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MoneyViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return NUMBER;
    }

    class MoneyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv2)
        TextView tv2;
        @BindView(R.id.tv3)
        TextView tv3;
        @BindView(R.id.tv4)
        TextView tv4;
        @BindView(R.id.tv5)
        TextView tv5;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.im)
        ImageView im;
        @BindView(R.id.view_button)
        View view_button;
        @BindView(R.id.zongbushu)
        View zongbushu;
        @BindView(R.id.zongshichang)
        View zongshichang;
        @BindView(R.id.tv21)
        View tv21;
        @BindView(R.id.BTC_text)
        View BTC_text;
        @BindView(R.id.rl1)
        LinearLayout rl1;
        @BindView(R.id.r12)
        LinearLayout r12;

        public MoneyViewHolder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(int position) {
            if (position == 0) {
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.VISIBLE);
                im.setVisibility(View.GONE);
                r12.setVisibility(View.GONE);
                BTC_text.setVisibility(View.GONE);
                zongshichang.setVisibility(View.GONE);
                zongbushu.setVisibility(View.GONE);

                rl.setBackgroundResource(R.drawable.recyclerview_hader_item_bg);
            } else if (position == NUMBER - 1) {
                rl.setBackgroundResource(R.drawable.recyclerview_foot_item_bg);
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.GONE);
                rl1.setVisibility(View.GONE);
                r12.setVisibility(View.GONE);

                BTC_text.setVisibility(View.GONE);
                zongshichang.setVisibility(View.GONE);
                zongbushu.setVisibility(View.GONE);
                tv21.setVisibility(View.GONE);
                view_button.setBackgroundColor(Color.parseColor("#00000000"));
                im.setVisibility(View.VISIBLE);

            } else {
                rl.setBackgroundColor(Color.parseColor("#32424d"));
                tv1.setVisibility(View.GONE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.GONE);
                im.setVisibility(View.GONE);
                rl1.setVisibility(View.GONE);
//                r12.setVisibility(View.GONE);
                BTC_text.setVisibility(View.VISIBLE);
                zongshichang.setVisibility(View.VISIBLE);
                zongbushu.setVisibility(View.VISIBLE);
            }
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NUMBER > 4) {
                        NUMBER = 4;
                    } else {
                        NUMBER = 8;
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}
