package xyf.com.appframe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.internal.schedulers.NewThreadWorker;
import rx.schedulers.Schedulers;
import xyf.com.appframe.javabean.MusicKrcInfo;
import xyf.com.appframe.javabean.MusicPlayInfo;
import xyf.com.appframe.javabean.MusicPlayingMessage;
import xyf.com.appframe.javabean.MusicSearchResultDataData;
import xyf.com.appframe.javabean.MusicSingerInfo;
import xyf.com.appframe.network.NetWork;
import xyf.com.appframe.recycleviewtools.TImeUtils;
import xyf.com.appframe.widget.LrcParser;
import xyf.com.appframe.widget.LrcUtils;
import xyf.com.appframe.widget.LrcView;

/**
 * Created by sh-xiayf on 16/7/20.
 */
public class MusicPlayActivity extends AppCompatActivity{

    @BindView(R.id.music_play_bg)
    SimpleDraweeView bg;

    @BindView(R.id.music_play_filename)
    TextView filename;

    @BindView(R.id.music_play_lrcview)
    LrcView lrcview;

    @BindView(R.id.music_play_progress)
    ProgressBar progress;

    @BindView(R.id.music_play_currenttime)
    TextView currentTime;

    @BindView(R.id.music_play_totaltime)
    TextView totaltime;

    @BindView(R.id.music_play_start_pause)
    ImageButton startpause;

    private MusicSearchResultDataData musicdata;
    private LrcParser parser;

    public static Intent getIntent(Context mContext,MusicSearchResultDataData data)
    {
        Intent intent = new Intent(mContext, MusicPlayActivity.class);
        intent.putExtra("music",data);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        ButterKnife.bind(this);

        musicdata = getIntent().getParcelableExtra("music");
        if (musicdata == null)
        {
            finish();
            return;
        }

        setTitle(musicdata.filename);

        load();
    }

    private static int playingstatus;

    @OnClick(R.id.music_play_start_pause)
    public void onStartPause(ImageButton button)
    {
        if (playingstatus == 1)
        {
            startService(MusicPlayService.getPauseIntent(MusicPlayActivity.this));
        }
        else
        {
            startService(MusicPlayService.getResumeIntent(MusicPlayActivity.this));
        }
        changeButtonIcon();
    }

    private void changeButtonIcon()
    {
        if (playingstatus == 1)
        {
            startpause.setImageResource(android.R.drawable.ic_media_pause);
        }
        else if (playingstatus == 0)
        {
            startpause.setImageResource(android.R.drawable.ic_media_play);
        }

    }

    private Subscription singerSubcription;
    private Subscription playSubcription;
    private Subscription lrcSubcription;

    private void load()
    {
        singerSubcription = NetWork.getMusicSingerInfoApi().getSingerInfo(musicdata.singername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singerInfoObserver);

        playSubcription = NetWork.getMusicPlayInfoApi().getPlayInfo(musicdata.hash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playInfoObserver);

        lrcSubcription = NetWork.getMusicKrcInfoApi().getKrcInfo(musicdata.filename,musicdata.hash, "" +musicdata.duration)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(krcInfoObserver);

    }

    @Override
    protected void onDestroy() {
        startService(MusicPlayService.getStopIntent(MusicPlayActivity.this));
        super.onDestroy();
        if (singerSubcription != null && !singerSubcription.isUnsubscribed())
        {
            singerSubcription.unsubscribe();
        }
        if (playSubcription !=null && !playSubcription.isUnsubscribed())
        {
            playSubcription.unsubscribe();
        }
        if (lrcSubcription != null && !lrcSubcription.isUnsubscribed())
        {
            lrcSubcription.unsubscribe();
        }
    }

    Observer<MusicKrcInfo> krcInfoObserver = new Observer<MusicKrcInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(MusicKrcInfo musicKrcInfo) {
            if (musicKrcInfo.codeInt == 0)
            {
                parser = new LrcParser();
                parser.readLRC(musicKrcInfo.mdata.content);

                lrcview.setmLrcList(parser.getLrcList());
            }
        }
    };

    Observer<MusicSingerInfo> singerInfoObserver = new Observer<MusicSingerInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(MusicSingerInfo musicSingerInfo) {
            if (musicSingerInfo.codeint == 0)
            {
                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(musicSingerInfo.mdata.imageStr)).build();

                DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(imageRequest).build();

                bg.setController(draweeController);
            }
        }
    };

    Observer<MusicPlayInfo> playInfoObserver = new Observer<MusicPlayInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(MusicPlayInfo musicPlayInfo) {
            if (musicPlayInfo.codeInt == 0)
            {
                progress.setProgress(0);
                currentTime.setText("00:00");
                totaltime.setText(TImeUtils.secToTime((int) musicPlayInfo.mdata.timeLength));
                startService(MusicPlayService.getStartIntent(MusicPlayActivity.this,musicPlayInfo.mdata.url));
            }

        }
    };

    private static int index = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MusicPlayingMessage message)
    {
        if (playingstatus != message.status)
        {
            playingstatus = message.status;
            changeButtonIcon();
        }

        currentTime.setText(TImeUtils.secToTime((int) message.currenttime / 1000));
        progress.setProgress(message.currentprogress);
        if (message.bufferedprogress > 0)
        {
            progress.setSecondaryProgress(message.bufferedprogress);
        }

        if (parser != null)
        {
            lrcview.setIndex(LrcUtils.lrcIndex(0,message.currenttime,message.totaltime,parser.getLrcList()));
            lrcview.invalidate();
//            lrcview.startAnimation(AnimationUtils.loadAnimation(MusicPlayActivity.this,R.anim.anim_lrc));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

}
