package xyf.com.appframe;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyf.com.appframe.adapter.CityListRvAdapter;
import xyf.com.appframe.javabean.City;
import xyf.com.appframe.javabean.CityListBean;
import xyf.com.appframe.network.NetWork;
import xyf.com.appframe.recycleviewtools.ProgressDialogUtils;
import xyf.com.appframe.recycleviewtools.RecycleViewListener;
import xyf.com.appframe.recycleviewtools.SoftInputUtils;
import xyf.com.appframe.recycleviewtools.itemDecoration;

public class MainActivity extends AppCompatActivity implements RecycleViewListener{

    @BindView(R.id.edit_input)
    EditText input;

    @BindView(R.id.edit_result)
    RecyclerView result;

    @OnClick(R.id.edit_btn)
    public void onsearch()
    {
        if (mSubscription != null && mSubscription.isUnsubscribed())
        {
            mSubscription.unsubscribe();
        }
        SoftInputUtils.hideSoftInput(this);
        ProgressDialogUtils.showProgress(MainActivity.this);
        mSubscription = NetWork.getClitListApi().getcitylist(input.getText().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private CityListRvAdapter adapter = new CityListRvAdapter();
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        result.setLayoutManager(new LinearLayoutManager(this));
        result.addItemDecoration(new itemDecoration(this));
        ScaleInAnimator scalein = new ScaleInAnimator();
        result.setItemAnimator(scalein);
        result.setAdapter(adapter);
        adapter.setCallback(this);

        setTitle("搜索天气城市列表");
    }

    Observer<CityListBean> observer = new Observer<CityListBean>() {
        @Override
        public void onCompleted() {
            ProgressDialogUtils.dismissProgress(MainActivity.this);
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(CityListBean cityListBean) {
            if (cityListBean.errNum == 0)
            {
                adapter.setCities(cityListBean.retData);
                setTitle(input.getText().toString().trim());
            }
            else
            {
                Toast.makeText(MainActivity.this, cityListBean.errMsg, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public void OnItemClickListener(View v, int position) {
        Toast.makeText(MainActivity.this, ((City)adapter.getItem(position)).name_cn, Toast.LENGTH_SHORT).show();

        startActivity(WeatherDetailActivity.getIntent(this,(City) adapter.getItem(position)));
    }

    @Override
    public void OnItemLongClickListener(View v, int position) {

    }
}
