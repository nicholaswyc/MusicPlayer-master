package com.example.musicplayer;

import static android.app.Service.START_STICKY;
import static android.content.Context.WINDOW_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class FloatingMusicPlayerService extends Service {

    private WindowManager windowManager;
    private View floatingPlayerView;

    @Override
    public void onCreate() {
        super.onCreate();
        // 创建悬浮窗布局
        floatingPlayerView = LayoutInflater.from(FloatingMusicPlayerService.this).inflate(R.layout.floating_music_player, null);
//        setContentView(R.layout.activity_main);
        // 设置悬浮窗的布局参数
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        // 设置悬浮窗显示位置为屏幕底部
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER;


        // 获取 WindowManager 并将悬浮窗布局添加到窗口中
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(floatingPlayerView, layoutParams);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 返回 START_STICKY 以确保服务在被杀死后能够重新启动
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 移除悬浮窗布局并释放资源
        if (windowManager != null && floatingPlayerView != null) {
            windowManager.removeView(floatingPlayerView);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
