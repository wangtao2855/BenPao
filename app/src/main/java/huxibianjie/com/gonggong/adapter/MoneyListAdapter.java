package huxibianjie.com.gonggong.adapter;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.runmining.R;

import java.util.ArrayList;
import java.util.List;

import huxibianjie.com.gonggong.bean.GetMyCoinBean;
import huxibianjie.com.gonggong.bean.GetMyCoinDetailBean;

/**
 * Created by wangtao on 2018/6/25.
 */

public class MoneyListAdapter extends BaseExpandableListAdapter {


    private static final String TAG = "IndicatorExpandableList";
    private List<GetMyCoinDetailBean.DatasBean> childData = new ArrayList<>();
    private List<GetMyCoinBean.DatasBean> groupData = new ArrayList<>();
    private SparseArray<ImageView> mIndicators;
    private OnGroupExpandedListener mOnGroupExpandedListener;

    public MoneyListAdapter() {
        mIndicators = new SparseArray<>();
    }

    public void bounGroupData(List<GetMyCoinBean.DatasBean> groupData) {
        this.groupData = groupData;
        notifyDataSetChanged();
    }

    public void bounChildData(List<GetMyCoinDetailBean.DatasBean> childData) {
        this.childData = childData;
        notifyDataSetChanged();
    }

    public void setOnGroupExpandedListener(OnGroupExpandedListener onGroupExpandedListener) {
        mOnGroupExpandedListener = onGroupExpandedListener;
    }

    //            根据分组的展开闭合状态设置指示器
    public void setIndicatorState(int groupPosition, boolean isExpanded) {
        if (isExpanded) {
            mIndicators.get(groupPosition).setImageResource(R.mipmap.ic_expand_less);
        } else {
            mIndicators.get(groupPosition).setImageResource(R.mipmap.ic_expand_more);
        }
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_money, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.BTC_text = (TextView) convertView.findViewById(R.id.BTC_text);
            groupViewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
            groupViewHolder.ivIndicator = (ImageView) convertView.findViewById(R.id.iv_indicator);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.BTC_text.setText(groupData.get(groupPosition).getCoinName());
        groupViewHolder.tv3.setText(groupData.get(groupPosition).getCoin() + "");
        //      把位置和图标添加到Map
        mIndicators.put(groupPosition, groupViewHolder.ivIndicator);
        //      根据分组状态设置Indicator
        setIndicatorState(groupPosition, isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_child, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_left = (TextView) convertView.findViewById(R.id.tv_left);
            childViewHolder.tv_right = (TextView) convertView.findViewById(R.id.tv_right);
            childViewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tv_left.setText(childData.get(childPosition).getCoinName());
        childViewHolder.tv_right.setText(childData.get(childPosition).getCoin());
        childViewHolder.tv_time.setText(childData.get(childPosition).getCreateTime());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        Log.d(TAG, "onGroupExpanded() called with: groupPosition = [" + groupPosition + "]");
        if (mOnGroupExpandedListener != null) {
            mOnGroupExpandedListener.onGroupExpanded(groupPosition);
        }
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        Log.d(TAG, "onGroupCollapsed() called with: groupPosition = [" + groupPosition + "]");
    }

    private static class GroupViewHolder {
        TextView tv1, tv3, BTC_text;
        ImageView ivIndicator;
        RelativeLayout rl;
    }

    private static class ChildViewHolder {
        TextView tv_left, tv_right, tv_time;
    }
}
