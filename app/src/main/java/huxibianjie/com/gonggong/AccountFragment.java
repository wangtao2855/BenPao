package huxibianjie.com.gonggong;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.home.runmining.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import huxibianjie.com.gonggong.adapter.MoneyAdapter;

public class AccountFragment extends AutoLayoutActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_fragment);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MoneyAdapter(this));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(new MoneyAdapter(this));
    }
}
