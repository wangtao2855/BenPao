package huxibianjie.com.gonggong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.runmining.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettlementActivity extends AutoLayoutActivity {

    @BindView(R.id.xiangqing4)
    ImageView mXiangqing4;
    @BindView(R.id.get_today_many)
    TextView mGetTodayMany;
    @BindView(R.id.this_git_stepnumber_max)
    TextView mThisGitStepnumberMax;
    @BindView(R.id.game_over)
    Button mGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.game_over)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.game_over:
                startActivity(new Intent(this,WalkingActivity.class));
                break;
        }
    }
}
