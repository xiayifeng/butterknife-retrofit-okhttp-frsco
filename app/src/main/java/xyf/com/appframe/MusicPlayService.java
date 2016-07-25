package xyf.com.appframe;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import xyf.com.appframe.javabean.MusicPlayingMessage;

/**
 * Created by sh-xiayf on 16/7/20.
 */
public class MusicPlayService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnInfoListener,MediaPlayer.OnErrorListener,MediaPlayer.OnBufferingUpdateListener{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String COMMAND = "command";
    private static final String START = "command_start";
    private static final String PAUSE = "command_pause";
    private static final String RESUME = "command_resume";
    private static final String STOP = "command_stop";

    public static Intent getStopIntent(Context mContext)
    {
        Intent intent = new Intent(mContext,MusicPlayService.class);
        intent.putExtra(COMMAND,STOP);
        return intent;
    }

    public static Intent getStartIntent(Context mContext,String musicpath)
    {
        Intent intent = new Intent(mContext,MusicPlayService.class);
        intent.putExtra("path",musicpath);
        intent.putExtra(COMMAND,START);
        return intent;
    }

    public static Intent getResumeIntent(Context mContext)
    {
        Intent intent = new Intent(mContext,MusicPlayService.class);
        intent.putExtra(COMMAND,RESUME);
        return  intent;
    }

    public static Intent getPauseIntent(Context mContext)
    {
        Intent intent = new Intent(mContext,MusicPlayService.class);
        intent.putExtra(COMMAND,PAUSE);
        return intent;
    }

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer == null)
        {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnInfoListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnBufferingUpdateListener(this);

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaPlayer.setLooping(true);
        }
    }

    private Timer mTimer ;
    private ProgressTask mTask;

    private class ProgressTask extends TimerTask {

        @Override
        public void run() {

            if (mediaPlayer == null) return;

            if (mediaPlayer.isPlaying())
            {
                sendMessage(buffedprecent,100 * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration(),
                        mediaPlayer.isPlaying() ? 1 : 0,mediaPlayer.getCurrentPosition());
            }

        }
    };

    private void stopTimer()
    {
        if (mTask != null)
        {
            mTask.cancel();
            mTask = null;
        }

        if (mTask != null)
        {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void startTimer()
    {
        if (mTimer == null)
        {
            mTimer = new Timer();
        }
        if (mTask == null)
        {
            mTask = new ProgressTask();
        }

        if (mTimer != null && mTask != null)
        {
            try
            {
                mTimer.schedule(mTask,0,500);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String commad = intent.getStringExtra(COMMAND);
        if (commad.equals(START))
        {
            play(intent.getStringExtra("path"));
        }
        else if (commad.equals(PAUSE))
        {
            pause();
        }
        else if (commad.equals(RESUME))
        {
            resume();
        }
        else if (commad.equals(STOP))
        {
            stop();
        }

        return START_STICKY;
    }

    private void stop()
    {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            stopTimer();
        }
        else
        {
            resume();
        }
    }

    private void resume()
    {
        if (mediaPlayer != null && !mediaPlayer.isPlaying())
        {
            mediaPlayer.start();
            sendMessage(buffedprecent,100 * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration(),
                    mediaPlayer.isPlaying() ? 1 : 0,mediaPlayer.getCurrentPosition());
            startTimer();
        }else
        {
            stop();
        }
    }

    private void play(String path)
    {
        Log.d("XYF","uri:"+path);
        if (mediaPlayer == null)
        {
            return;
        }

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void pause()
    {
        if (mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
            sendMessage(buffedprecent,100 * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration(),
                    mediaPlayer.isPlaying() ? 1 : 0,mediaPlayer.getCurrentPosition());
            stopTimer();
        }
    }

    private void sendMessage(int buffedprecent,int curprogress,int status,long currenttime)
    {
        MusicPlayingMessage message = new MusicPlayingMessage();
        message.bufferedprogress = buffedprecent;
        message.currentprogress = curprogress;
        message.status = status;
        message.currenttime = currenttime;
        message.totaltime = mediaPlayer.getDuration();

        EventBus.getDefault().post(message);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopTimer();

        sendMessage(buffedprecent,100,0,mediaPlayer.getDuration());
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d("XYF","music prepared");
        mediaPlayer.start();

        startTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
        {
            mTimer.cancel();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    private int buffedprecent;

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        this.buffedprecent = percent;
    }
}
