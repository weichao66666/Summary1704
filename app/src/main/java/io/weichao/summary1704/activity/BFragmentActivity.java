package io.weichao.summary1704.activity;

import android.content.Intent;
import android.os.Bundle;

import io.weichao.activity.BaseFragmentActivity;

public class BFragmentActivity extends BaseFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全局捕获异常。
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();

                // app 崩溃后自动重启。debug 时不要开启，否则 logcat 刷新。
                Intent intent = new Intent(BFragmentActivity.this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BFragmentActivity.this.startActivity(intent);

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}
