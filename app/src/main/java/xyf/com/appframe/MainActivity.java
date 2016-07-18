package xyf.com.appframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyf.com.appframe.adapter.CityListRvAdapter;
import xyf.com.appframe.javabean.City;
import xyf.com.appframe.javabean.CityListBean;
import xyf.com.appframe.network.NetWork;
import xyf.com.appframe.recycleviewanimators.ScaleInOutItemAnimator;
import xyf.com.appframe.views.itemDecoration;

public class MainActivity extends AppCompatActivity {

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
        result.setItemAnimator(new ScaleInOutItemAnimator(result));
        result.setAdapter(adapter);
    }

    Observer<CityListBean> observer = new Observer<CityListBean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(CityListBean cityListBean) {
            Toast.makeText(MainActivity.this, input.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            adapter.clear();
            adapter.setCities(cityListBean.retData);
        }
    };


}
