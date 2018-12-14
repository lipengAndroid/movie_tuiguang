package com.judian.watch.videos.Utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * 音量调节工具
 * Created by 李鹏 2018/1/3 0003.
 */

public class VolumeUtils {
    private static VolumeUtils mApplication;
    private int mVolume = -1;
    private int mMaxVolume;
    private AudioManager mAudioManager;

    public static synchronized VolumeUtils getMy() {
        if (mApplication == null) {
            mApplication = new VolumeUtils();
        }

        return mApplication;
    }

    public void onVolumeSlide(float percent) {
        mAudioManager = (AudioManager) MyApplication.getInstance().getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;
        }
        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume) {
            ToastUtils.show("s");
            index = mMaxVolume;

        } else if (index < 0) {
            index = 0;
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
    }
}
