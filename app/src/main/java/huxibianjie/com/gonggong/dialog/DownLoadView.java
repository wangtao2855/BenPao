package huxibianjie.com.gonggong.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.home.runmining.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import huxibianjie.com.gonggong.util.ThreadManager;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

@SuppressLint("WrongConstant")
public class DownLoadView extends View {
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    private DialogUtils dialogUtils;
    private Context context;
    private String latest_version = "wankuang";
    private String updateMsg;
    private int progress = 0;
    private boolean isInterceptDownload = false;
    private String downUrl;

    public DownLoadView(Context context, String updateMsg, String url) {
        super(context);
        this.context = context;
        this.updateMsg = updateMsg;
        this.downUrl = url;
        init();
    }

    public DownLoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DownLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        dialogUtils = new DialogUtils();
        View view = LayoutInflater.from(context).inflate(R.layout.view_download, null);
        ButterKnife.bind(this, view);
        tvMsg.setText(updateMsg);
        //这是强制更新
        dialogUtils.displayDialog(context, view, Gravity.CENTER);
        //点击返回按钮不返回
        dialogUtils.setBackLis();
    }

    @OnClick({R.id.tv_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_btn:
                tvInfo.setText("下载完成 0%");
                tvBtn.setVisibility(View.GONE);
                ThreadManager.getInstance().ExecutorServiceThread(new DownRunable());
                break;
        }
    }

    private void installApk() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File apkFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getString(R.string.updateApkFile) + "/" +
                    latest_version + ".apk");
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            if (Build.VERSION.SDK_INT >= 24) { //适配安卓7.0
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri apkFileUri = FileProvider.getUriForFile(context.getApplicationContext(),
                        context.getPackageName() + ".fileprovider", apkFile);
                intent.setDataAndType(apkFileUri, "application/vnd.android.package-archive");
            } else {
                Uri apkUri =
                        FileProvider.getUriForFile(context, context.getString(R.string.provider_name), apkFile);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } else {
            File apkFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getString(R.string.updateApkFile) + "/" +
                    latest_version + ".apk");
            if (!apkFile.exists()) {
                return;
            }
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 安装，如果签名不一致，可能出现程序未安装提示
            i.setDataAndType(Uri.fromFile(new File(apkFile.getAbsolutePath())), "application/vnd.android.package-archive");
            context.startActivity(i);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x111:
                    tvInfo.setText("下载完成" + progress + "%");
                    break;
                case 0x222:
                    isInterceptDownload = true;
                    dialogUtils.dismissDialog();
                    tvInfo.setText("");
//                    if (Build.VERSION.SDK_INT >= 26) {
//                        boolean b = RE.getPackageManager().canRequestPackageInstalls();
//                        if (b) {
//                            publicApk();//安装应用的逻辑(写自己的就可以)
//                        } else {
//                            //请求安装未知应用来源的权限
//                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
//                        }
//                    }else {
//
//                    }

                        installApk();
                    break;
            }
        }
    };


    class DownRunable implements Runnable {

        @Override
        public void run() {
            try {
                URL url = new URL(downUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getString(R.string.updateApkFile) + "/");
                if (!file.exists()) {
                    //如果文件夹不存在,则创建
                    file.mkdir();
                }
                String apkFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getString(R.string.updateApkFile) + "/" +
                        latest_version + ".apk";
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numRead = is.read(buf);
                    count += numRead;
                    //更新进度条
                    progress = (int) (((float) count / length) * 100);
                    Message message_ = new Message();
                    message_.what = 0x111;
                    handler.sendMessage(message_);
                    if (numRead <= 0) {
                        Message message = new Message();
                        message.what = 0x222;
                        handler.sendMessage(message);
                        break;
                    }
                    fos.write(buf, 0, numRead);
                } while (!isInterceptDownload);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showDialog() {
        if (dialogUtils != null) {
            dialogUtils.showDialog();
        }
    }

    public void DisDialog() {
        if (dialogUtils != null) {
            dialogUtils.dismissDialog();
        }
    }
}


