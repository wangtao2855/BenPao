package huxibianjie.com.gonggong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.home.runmining.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import huxibianjie.com.gonggong.bean.NearbyBean;
import huxibianjie.com.gonggong.util.CircleImageView;
import huxibianjie.com.gonggong.util.Constant;

/**
 * Created by wangtao on 2018/6/11.
 */

public class NearbyRankingsAdapter extends RecyclerView.Adapter {

    private List<NearbyBean.DatasBean> datas;


    public void setDatas(List<NearbyBean.DatasBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_rankings, parent, false);
        RecyclerView.ViewHolder viewHolder = new MoneyViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MoneyViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MoneyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_head)
        CircleImageView mUserHead;
        @BindView(R.id.ranking)
        TextView mRanking;
        @BindView(R.id.Apart)
        TextView mApart;
        @BindView(R.id.Today_step)
        TextView mTodayStep;
        @BindView(R.id.Apart_walking)
        TextView mApartWalking;
        @BindView(R.id.Today_money)
        TextView mTodayMoney;
        @BindView(R.id.rl)
        RelativeLayout mRl;
        @BindView(R.id.view)
        View view;

        public MoneyViewHolder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(int position) {
            NearbyBean.DatasBean datasBean = datas.get(position);
            Glide.with(itemView.getContext()).load(datasBean.getHead()).asBitmap().placeholder(R.mipmap.none).error(R.mipmap.none).into(mUserHead);
            mRanking.setText("第" + (position + 1) + "名");
            mApart.setText("距离" + datasBean.getDistance() + "米");
            mTodayStep.setText(datasBean.getStepNumberTotal() + "");
            mTodayMoney.setText(datasBean.getCoin() + Constant.MONEY);
        }

    }
}
