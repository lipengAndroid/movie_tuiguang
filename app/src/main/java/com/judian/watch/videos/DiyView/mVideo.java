package com.judian.watch.videos.DiyView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.BrightnessUtils;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.TimeUtils;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.Utils.VolumeUtils;

import java.text.DecimalFormat;

/**
 * Created by 李鹏 2018/1/3 0003.
 */

public class mVideo extends RelativeLayout {
    private MediaPlayer videoPlayer;
    private AppCompatImageView pay, imgFull;
    private View content;
    private TextView payTime, wholeTime, title;
    private ProgressBar progressBar2;
    private SeekBar seekBar;
    private GestureDetector mGestureDetector;
    private Activity activity;
    private static Handler mHandler;
    private int i = 0;
    private View rootView;
    private boolean isSureGone = true;
    private boolean ProgressisChange = false, payisv = false;
    private boolean huadongzhong = false;
    private ImageView imageView;
    private View backView, back;
    private boolean ispaying = false;//视频是否正在加载  如果在加载 就不允许切换集数
    private Runnable runnable;
    private View toWebLay, toWeb;
    private SurfaceView surfaceView;

    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public mVideo(Context context) {
        super(context);
        initView();
    }


    public mVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public View getToWebLay() {
        return toWebLay;
    }

    public View getToWeb() {
        return toWeb;
    }

    public mVideo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public boolean isIspaying() {
        return ispaying;
    }

    public void setPayisv(boolean payisv) {
        this.payisv = payisv;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        View view = View.inflate(getContext(), R.layout.m_video_view, this);
        pay = view.findViewById(R.id.pay);
        imgFull = view.findViewById(R.id.img_full);
        content = view.findViewById(R.id.content);
        payTime = view.findViewById(R.id.pay_time);
        wholeTime = view.findViewById(R.id.whole_time);
        progressBar2 = view.findViewById(R.id.progressBar2);
        seekBar = view.findViewById(R.id.seek_bar);
        rootView = view.findViewById(R.id.root_view);
        surfaceView = view.findViewById(R.id.surface_view);
        imageView = view.findViewById(R.id.img);
        title = view.findViewById(R.id.title);
        back = view.findViewById(R.id.back);
        backView = view.findViewById(R.id.back_layout);

        toWebLay = view.findViewById(R.id.to_web_pay_layout);
        toWeb = view.findViewById(R.id.to_web_pay);
        mGestureDetector = new GestureDetector(getContext(), new MyGestureListener());
        mHandler = new Handler();

        surfaceView.setZOrderOnTop(false);
//        videoSuf.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        videoPlayer = new MediaPlayer();


        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                try {
                    videoPlayer.setDisplay(holder);
                } catch (Exception e) {
                }

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        videoPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                imageView.setVisibility(GONE);

                ViewGone();

                ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
                double phonwH = (double) MyApplication.getInstance().getY(); // 1920
                double videoW = (double) mp.getVideoWidth(); // 1034
                double videoH = (double) mp.getVideoHeight(); // 504
                double w = phonwH / videoW * videoH;
                lp.height = (int) w;
                lp.width = (int) phonwH;
                surfaceView.setLayoutParams(lp);

                videoPlayer.start();
                ispaying = true;
                wholeTime.setText(TimeUtils.getTime(videoPlayer.getDuration()));
                payTime.setText(TimeUtils.getTime(videoPlayer.getCurrentPosition()));
                seekBar.setMax(videoPlayer.getDuration());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                }
                runnable = new Runnable() {
                    @Override
                    public void run() {

                        if (progressBar2.getVisibility() == View.VISIBLE) {
                            progressBar2.setVisibility(GONE);
                        }
                        if (seekBar.getVisibility() == View.VISIBLE) {
                            try {
                                payTime.setText(TimeUtils.getTime(videoPlayer.getCurrentPosition()));

                                seekBar.setProgress(videoPlayer.getCurrentPosition());
                            } catch (Exception ignored) {
                            }
                        }

                        if (i > 4) {
                            i = 0;
                            try {
                                if (videoPlayer.isPlaying()) {
                                    ViewGone();
                                }
                            } catch (Exception ignored) {

                            }

                            i = 0;
                        }
                        if (isSureGone && ProgressisChange && !huadongzhong) {
                            i++;
                        }
                        mHandler.postDelayed(this, 1000);
                    }
                };
                mHandler.post(runnable);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                payTime.setText(TimeUtils.getTime(seekBar.getProgress()));
                ProgressisChange = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progressBar2.setVisibility(VISIBLE);
                videoPlayer.seekTo(seekBar.getProgress());
                try {
                    videoPlayer.start();
                    ProgressisChange = false;
                } catch (Exception ignored) {
                }
            }
        });

        imgFull.setVisibility(GONE);
        imgFull.setOnClickListener((View v) -> {

            Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
            int ori = mConfiguration.orientation; //获取屏幕方向
            if (ori == Configuration.ORIENTATION_LANDSCAPE) {
                //横屏
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
            } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
            }

        });
        rootView.setOnTouchListener((v, event) -> {
            if (mGestureDetector.onTouchEvent(event)){
                return true;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    huadongzhong = true;
                    isSureGone = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    isSureGone = false;
                    break;
                case MotionEvent.ACTION_UP:
                    huadongzhong = false;
                    if (pay.getVisibility() == View.GONE && payisv) {
                        ViewVisible();
                    }
                    isSureGone = true;
                    break;
            }

            return true;
        });

//        back.setOnClickListener(v -> {
////            Configuration mConfiguration = getResources().getConfiguration(); //获取设置的配置信息
////            int ori = mConfiguration.orientation; //获取屏幕方向
////            if (ori == Configuration.ORIENTATION_LANDSCAPE) {
////                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
////            } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
//                activity.finish();
////            }
//        });
    }

    public View getBack() {
        return back;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public MediaPlayer getVideoPlayer() {
        return videoPlayer;
    }

    public void ChangeUri() {
        getVideoPlayer().stop();
        getVideoPlayer().reset();
        stop();
        ViewVisible();
        getProgressBar2().setVisibility(GONE);
        getImageView().setVisibility(VISIBLE);
        ViewVisible();
    }

    public void stop() {
        mHandler.removeCallbacks(runnable);
    }

    public AppCompatImageView getPay() {
        return pay;
    }

    public AppCompatImageView getImgFull() {
        return imgFull;
    }

    public View getContent() {
        return content;
    }


    public ProgressBar getProgressBar2() {
        return progressBar2;
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (e.getX() > MyApplication.getInstance().getX() / 2) {
                if (seekBar.getProgress() + (1000 * 15) < seekBar.getMax()) {
                    videoPlayer.seekTo(seekBar.getProgress() + (1000 * 15));
                    ToastUtils.show("快进15秒");
                }
            } else {
                if (seekBar.getProgress() - (1000 * 15) > 0) {
                    videoPlayer.seekTo(seekBar.getProgress() - (1000 * 15));
                    ToastUtils.show("快退15秒");
                }
            }
            if (!videoPlayer.isPlaying()) {
                videoPlayer.start();
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            int windowWidth = getWidth();
            int windowHeight = getHeight();
            if (mOldX > windowWidth * 4.0 / 5) {// 右边滑动
                VolumeUtils.getMy().onVolumeSlide((mOldY - y) / windowHeight);
            } else if (mOldX < windowWidth / 5.0) { // 左边滑动
                if (activity != null) {
                    BrightnessUtils.getMy().onBrightnessSlide((mOldY - y) / windowHeight, activity);
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    private void ViewGone() {
        seekBar.setVisibility(View.GONE);
        pay.setVisibility(View.GONE);
        payTime.setVisibility(View.GONE);
        wholeTime.setVisibility(View.GONE);
        imgFull.setVisibility(View.GONE);
        progressBar2.setVisibility(GONE);
        backView.setVisibility(GONE);
    }

    public void ViewVisible() {
        i = 0;
        back.setVisibility(VISIBLE);
        seekBar.setVisibility(View.VISIBLE);
        pay.setVisibility(View.VISIBLE);
        payTime.setVisibility(View.VISIBLE);
        wholeTime.setVisibility(View.VISIBLE);
        imgFull.setVisibility(View.VISIBLE);
        backView.setVisibility(VISIBLE);
    }

}
