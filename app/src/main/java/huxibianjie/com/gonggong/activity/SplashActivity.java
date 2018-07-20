package huxibianjie.com.gonggong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.home.runmining.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import huxibianjie.com.gonggong.util.CountDownTimerUtils;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.tiaoguo)
    TextView tiaoguo;
    private CountDownTimerUtils countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        ButterKnife.bind(this);
        countDownTimer = CountDownTimerUtils.getCountDownTimer();
        countDownTimer.setMillisInFuture(4 * 1000);
        countDownTimer.setCountDownInterval(1000);
        countDownTimer.setFinishDelegate(new CountDownTimerUtils.FinishDelegate() {
            @Override
            public void onFinish() {
                tiaoguo.setText("跳过(0)");
                redirectTo();
            }
        });
        countDownTimer.setTickDelegate(new CountDownTimerUtils.TickDelegate() {
            @Override
            public void onTick(long pMillisUntilFinished) {
                tiaoguo.setText("跳过(" + pMillisUntilFinished / 1000 + ")");
            }
        });
        countDownTimer.start();
    }

    @OnClick({R.id.tiaoguo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tiaoguo:
                countDownTimer.cancel();
                redirectTo();
                break;
        }
    }

    private void redirectTo() {
        Intent intent = new Intent(SplashActivity.this, WalkingActivity.class);
        startActivity(intent);
        finish();
    }
}
