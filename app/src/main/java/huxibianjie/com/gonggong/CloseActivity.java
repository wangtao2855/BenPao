package huxibianjie.com.gonggong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.home.runmining.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CloseActivity extends AppCompatActivity {

    @BindView(R.id.btn_ac)
    TextView btn_ac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_ac)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ac:
                finish();
                break;
        }
    }
}
