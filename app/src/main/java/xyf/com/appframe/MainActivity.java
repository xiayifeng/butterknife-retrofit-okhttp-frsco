package xyf.com.appframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyf.com.appframe.javabean.CityListBean;
import xyf.com.appframe.network.NetWork;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_input)
    EditText input;

    @BindView(R.id.edit_result)
    TextView result;

    @OnClick(R.id.edit_btn)
    public void onsearch()
    {
        NetWork.getClitListApi().getcitylist(input.getText().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
            result.setText(cityListBean.toString());
        }
    };


}
