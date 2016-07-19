package xyf.com.appframe.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyf.com.appframe.R;
import xyf.com.appframe.javabean.RVLabelBean;
import xyf.com.appframe.javabean.RecentWeatherRetDataTodayBean;
import xyf.com.appframe.javabean.RecentWeatherRetDataTodayIndexBean;
import xyf.com.appframe.javabean.RecentWeatherRetDataforecastBean;
import xyf.com.appframe.recycleviewtools.BaseViewHolder;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class WeatherAdapter extends RecyclerView.Adapter{

    private List<Object> datas;

    public WeatherAdapter ()
    {
        datas = new ArrayList<>();
    }

    public void addData(List<Object> data)
    {
        datas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ITEM_TYPE.TYPE_LABEL.ordinal())
        {
            return new LabelViewHolder(inflater.inflate(R.layout.item_rv_label,null));
        }
        else if(viewType == ITEM_TYPE.TYPE_WEATHER_FORECAST.ordinal())
        {
            return new ForecastViewHolder(inflater.inflate(R.layout.item_rv_weather,null));
        }
        else if(viewType == ITEM_TYPE.TYPE_WEATHER_TODAY.ordinal())
        {
            return new TodayViewHolder((inflater.inflate(R.layout.item_rv_weather,null)));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).setData(getItem(position));
    }

    enum ITEM_TYPE
    {
        TYPE_WEATHER_TODAY,
        TYPE_WEATHER_FORECAST,
        TYPE_LABEL
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public Object getItem(int position)
    {
        return datas == null ? null : position >= datas.size() ? null : datas.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof RVLabelBean)
        {
            return ITEM_TYPE.TYPE_LABEL.ordinal();
        }
        else if (getItem(position) instanceof RecentWeatherRetDataforecastBean)
        {
            return ITEM_TYPE.TYPE_WEATHER_FORECAST.ordinal();
        }
        else if (getItem(position) instanceof RecentWeatherRetDataTodayBean)
        {
            return ITEM_TYPE.TYPE_WEATHER_TODAY.ordinal();
        }
        return super.getItemViewType(position);
    }

    protected class ForecastViewHolder extends BaseViewHolder<RecentWeatherRetDataforecastBean>
    {
        @BindView(R.id.weather_image)
        SimpleDraweeView image;
        @BindView(R.id.weather_date) TextView date;
        @BindView(R.id.weather_week) TextView week;
        @BindView(R.id.weather_type) TextView type;
        @BindView(R.id.weather_scrope) TextView scrope;

        @BindView(R.id.weather_currenttmp)
        RelativeLayout currenttmp;
        @BindView(R.id.weather_gm) RelativeLayout gm;
        @BindView(R.id.weather_fs) RelativeLayout fs;
        @BindView(R.id.weather_ct) RelativeLayout ct;
        @BindView(R.id.weather_yd) RelativeLayout yd;
        @BindView(R.id.weather_xc) RelativeLayout xc;
        @BindView(R.id.weather_ls) RelativeLayout ls;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            currenttmp.setVisibility(View.GONE);
            gm.setVisibility(View.GONE);
            fs.setVisibility(View.GONE);
            ct.setVisibility(View.GONE);
            yd.setVisibility(View.GONE);
            xc.setVisibility(View.GONE);
            ls.setVisibility(View.GONE);

        }

        public void setData(RecentWeatherRetDataforecastBean data)
        {
            Context mContext = itemView.getContext();
            date.setText(data.date);
            week.setText(data.week);
            type.setText(String.format(mContext.getString(R.string.weather_type),data.type));
            scrope.setText(String.format(mContext.getString(R.string.weather_scope),String.format("%s-%s",data.lowtemp,data.hightemp)));

            String imageUrl = WeatherPicAdapter.getPicUrl(data.type);
            if (TextUtils.isEmpty(imageUrl))
            {
                image.setVisibility(View.GONE);
            }
            else
            {
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
                        .build();

                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .build();

                image.setController(controller);
            }
        }

    }

    protected class TodayViewHolder extends BaseViewHolder<RecentWeatherRetDataTodayBean>
    {

        @BindView(R.id.weather_image)
        SimpleDraweeView image;
        @BindView(R.id.weather_date) TextView date;
        @BindView(R.id.weather_week) TextView week;
        @BindView(R.id.weather_type) TextView type;
        @BindView(R.id.weather_currentWeather) TextView currentWeather;
        @BindView(R.id.weather_aqi) TextView aqi;
        @BindView(R.id.weather_scrope) TextView scrope;

        @BindView(R.id.weather_gm_detail) TextView gm_detail;
        @BindView(R.id.weather_fs_detail) TextView fs_detail;
        @BindView(R.id.weather_ct_detail) TextView ct_detail;
        @BindView(R.id.weather_yd_detail) TextView yd_detail;
        @BindView(R.id.weather_xc_detail) TextView xc_detail;
        @BindView(R.id.weather_ls_detail) TextView ls_deatil;


        public TodayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(RecentWeatherRetDataTodayBean data)
        {
            Context mContext = itemView.getContext();
            date.setText(data.date);
            week.setText(data.week);
            type.setText(String.format(mContext.getString(R.string.weather_type),data.type));
            currentWeather.setText(String.format(mContext.getString(R.string.weather_currenttmp),data.curTemp));
            aqi.setText(String.format(mContext.getString(R.string.weather_aqi),data.aqi));
            scrope.setText(String.format(mContext.getString(R.string.weather_scope),String.format("%s-%s",data.lowtemp,data.hightemp)));

            if (data.message != null)
            {
                for (RecentWeatherRetDataTodayIndexBean tmp : data.message)
                {
                    if (tmp.code.equals("gm"))
                    {
                        gm_detail.setText(tmp.details);
                    }
                    else if (tmp.code.equals("fs"))
                    {
                        fs_detail.setText(tmp.details);
                    }
                    else if (tmp.code.equals("ct"))
                    {
                        ct_detail.setText(tmp.details);
                    }
                    else if (tmp.code.equals("yd"))
                    {
                        yd_detail.setText(tmp.details);
                    }
                    else if (tmp.code.equals("xc"))
                    {
                        xc_detail.setText(tmp.details);
                    }
                    else if (tmp.code.equals("ls"))
                    {
                        ls_deatil.setText(tmp.details);
                    }
                }
            }

            String imageUrl = WeatherPicAdapter.getPicUrl(data.type);
            if (TextUtils.isEmpty(imageUrl))
            {
                image.setVisibility(View.GONE);
            }
            else
            {
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
                        .build();

                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .build();

                image.setController(controller);
            }
        }

    }

    protected class LabelViewHolder extends BaseViewHolder<RVLabelBean>
    {

        @BindView(R.id.item_rv_label_name)
        TextView label_name;

        public LabelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(RVLabelBean data)
        {
            label_name.setText(data.labelName);
        }
    }

}
