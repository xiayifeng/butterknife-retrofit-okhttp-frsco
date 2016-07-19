package xyf.com.appframe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyf.com.appframe.adapter.WeatherAdapter;
import xyf.com.appframe.javabean.City;
import xyf.com.appframe.javabean.RVLabelBean;
import xyf.com.appframe.javabean.RecentWeatherBean;
import xyf.com.appframe.network.NetWork;
import xyf.com.appframe.recycleviewtools.ProgressDialogUtils;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class WeatherDetailActivity extends AppCompatActivity {

    @BindView(R.id.weather_rv)
    RecyclerView rv;

    private WeatherAdapter adapter;

    public static Intent getIntent(Context mContext, City city)
    {
        Intent jumpIntent = new Intent(mContext,WeatherDetailActivity.class);
        jumpIntent.putExtra("city",city);
        return jumpIntent;
    }

    private City currentCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        currentCity = getIntent().getParcelableExtra("city");

        if (currentCity == null)
        {
            finish();
            return;
        }

        setTitle(currentCity.name_cn);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WeatherAdapter();
        rv.setAdapter(adapter);

        loadData(currentCity.name_cn,currentCity.area_id);
    }

    private void loadData(String name,String id)
    {
        ProgressDialogUtils.showProgress(WeatherDetailActivity.this);
        NetWork.getRecentWeathersApi()
                .getRecentWeather(name,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    Observer<RecentWeatherBean> observer = new Observer<RecentWeatherBean>() {
        @Override
        public void onCompleted() {
            ProgressDialogUtils.dismissProgress(WeatherDetailActivity.this);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(RecentWeatherBean recentWeatherBean) {
            if (recentWeatherBean.errNum == 0)
            {
                if (recentWeatherBean.retData != null)
                {
                    ArrayList<Object> result = new ArrayList<>();

                    RVLabelBean today = new RVLabelBean();
                    today.labelName = "今天";

                    result.add(today);
                    result.add(recentWeatherBean.retData.today);

                    RVLabelBean force = new RVLabelBean();
                    force.labelName = "未来天气";

                    result.add(force);
                    result.addAll(recentWeatherBean.retData.forecast);

                    RVLabelBean history = new RVLabelBean();
                    history.labelName = "历史天气";

                    result.add(history);
                    result.addAll(recentWeatherBean.retData.history);

                    adapter.addData(result);

                    setTitle(currentCity.name_cn);
                }

            }
            else {
                setTitle(recentWeatherBean.errMsg);
                loadData(currentCity.name_cn,currentCity.area_id);
            }
        }
    };



}
