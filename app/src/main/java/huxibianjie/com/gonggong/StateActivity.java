package huxibianjie.com.gonggong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.home.runmining.R;

public class StateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        Handler sh = new Handler();
        sh.postDelayed(new Runnable() {//延迟多少秒执行Runnable()
            @Override
            public void run() {
                redirectTo();
            }
        }, 3000);//1秒后跳转

    }

    private void redirectTo() {
        Intent intent = new Intent(StateActivity.this,WalkingActivity.class);
        startActivity(intent);
        finish();
    }
}
