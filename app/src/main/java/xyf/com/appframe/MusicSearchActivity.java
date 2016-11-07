package xyf.com.appframe;

import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.kogitune.activity_transition.ActivityTransitionLauncher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyf.com.appframe.adapter.MusicSearchAdapter;
import xyf.com.appframe.javabean.MusicSearchResult;
import xyf.com.appframe.network.NetWork;
import xyf.com.appframe.recycleviewtools.ProgressDialogUtils;
import xyf.com.appframe.recycleviewtools.SoftInputUtils;
import xyf.com.appframe.widget.MotionRecyclerViewItemClickListener;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class MusicSearchActivity extends AppCompatActivity implements MotionRecyclerViewItemClickListener{

    @BindView(R.id.music_search_input)
    EditText input;

    @BindView(R.id.music_search_rv)
    EasyRecyclerView result;

    private MusicSearchAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_music_search);
        ButterKnife.bind(this);

        initEasyRecycleView();
        setTitle("搜索歌曲");
    }

    private void initEasyRecycleView()
    {
        result.setLayoutManager(new LinearLayoutManager(this));
        SlideInUpAnimator slideInUpAnimator = new SlideInUpAnimator();
        result.setItemAnimator(slideInUpAnimator);
        adapter = new MusicSearchAdapter(this);
        adapter.setOnItemClickListener(this);
        adapter.setNoMore(R.layout.item_rv_nomore);
        adapter.setMore(R.layout.dialog_progress, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                load(searchkey,"" +pageno);
            }
        });

        adapter.setError(R.layout.item_rv_loaderr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });

        result.setAdapter(adapter);
        result.showRecycler();
    }

    @OnClick(R.id.music_search_btn)
    public void onSearch()
    {
        SoftInputUtils.hideSoftInput(MusicSearchActivity.this);
        adapter.clear();
        pageno = 1;
        ProgressDialogUtils.showProgress(MusicSearchActivity.this);
        searchkey = input.getText().toString().trim();
        setTitle(searchkey);
        load(searchkey,"" +pageno);
    }

    private Subscription mSubcription;

    private void load(String key,String pageno)
    {
        if (mSubcription != null && !mSubcription.isUnsubscribed())
        {
            mSubcription.unsubscribe();
        }
        mSubcription = NetWork.getMusicQueryApi().getMusic(key,PAGE_SIZE,pageno)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    private static final String PAGE_SIZE = "10";
    private static int pageno = 1;
    private static String searchkey;

    Observer<MusicSearchResult> observer = new Observer<MusicSearchResult>() {
        @Override
        public void onCompleted() {
            ProgressDialogUtils.dismissProgress(MusicSearchActivity.this);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            adapter.pauseMore();
        }

        @Override
        public void onNext(MusicSearchResult musicSearchResult) {
            if (musicSearchResult.codeStr == 0)
            {
                adapter.addAll(musicSearchResult.mdata.datalist);

                pageno++;
            }
            else
            {
                adapter.pauseMore();
            }
        }
    };

    @Override
    public void onItemClick(int position, View[] views) {
        ActivityTransitionLauncher.with(this).from(views[0]).launch(MusicPlayActivity.getIntent(this,adapter.getItem(position)));
    }
}


