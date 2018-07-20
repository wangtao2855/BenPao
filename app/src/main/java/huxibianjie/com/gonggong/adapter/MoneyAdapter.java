package huxibianjie.com.gonggong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.runmining.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import huxibianjie.com.gonggong.activity.AccountDetailActivity;
import huxibianjie.com.gonggong.bean.GetMyCoinBean;

/**
 * Created by wangtao on 2018/6/11.
 */

public class MoneyAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<GetMyCoinBean.DatasBean> datas = new ArrayList<>();

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
        return datas.size();
    }

    public void bounData(List<GetMyCoinBean.DatasBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    class MoneyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv3)
        TextView tv3;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.BTC_text)
        TextView BTC_text;

        public MoneyViewHolder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(int position) {
            final GetMyCoinBean.DatasBean datasBean = datas.get(position);
            if (TextUtils.isEmpty(datasBean.getCoinName())) {
                tv3.setVisibility(View.GONE);
                BTC_text.setVisibility(View.GONE);
                tv1.setVisibility(View.VISIBLE);
                rl.setBackgroundResource(R.drawable.recyclerview_hader_item_bg);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);
                rl.setLayoutParams(layoutParams);
            } else {
                rl.setBackgroundResource(0);
                tv1.setVisibility(View.GONE);
                tv3.setVisibility(View.VISIBLE);
                BTC_text.setVisibility(View.VISIBLE);
                BTC_text.setText(datasBean.getCoinName());
                tv3.setText(datasBean.getCoin() + " 个"+datasBean.getCoinName());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);
                rl.setLayoutParams(layoutParams);
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AccountDetailActivity.class);
                        intent.putExtra("coinName", datasBean.getCoinName());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }
}
