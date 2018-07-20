package huxibianjie.com.gonggong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.runmining.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import huxibianjie.com.gonggong.bean.GetMyCoinDetailBean;

/**
 * Created by wangtao on 2018/6/11.
 */

public class MoneyDetailsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<GetMyCoinDetailBean.DatasBean> datas = new ArrayList<>();

    public MoneyDetailsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_money, null);
        RecyclerView.ViewHolder viewHolder = new MoneyDetailViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MoneyDetailViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void bounData(List<GetMyCoinDetailBean.DatasBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    class MoneyDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv3)
        TextView tv3;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.BTC_text)
        TextView BTC_text;

        public MoneyDetailViewHolder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(int position) {
            GetMyCoinDetailBean.DatasBean datasBean = datas.get(position);
            tv3.setVisibility(View.VISIBLE);
            BTC_text.setVisibility(View.VISIBLE);
            BTC_text.setText(datasBean.getCoinName());
            tv3.setText(datasBean.getCoin() + " 个" + datasBean.getCoinName());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
            rl.setLayoutParams(layoutParams);
        }
    }
}
