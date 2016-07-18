package xyf.com.appframe.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyf.com.appframe.R;
import xyf.com.appframe.javabean.City;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class CityListRvAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_citylist,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CityViewHolder cityViewHolder = (CityViewHolder) holder;
        City tmp = cities.get(position);
        cityViewHolder.cityname.setText(tmp.name_cn);
    }

    List<City> cities;

    public void setCities(List<City> citiess)
    {
        this.cities = citiess;
        notifyItemRangeInserted(0,citiess.size());
    }

    public void clear()
    {
        if (cities == null)
        {
            return;
        }

        int size = cities.size();
        cities.clear();
        notifyItemRangeRemoved(0,size);

    }

    public void addCity(City city)
    {
        if (cities == null)
        {
            cities = new ArrayList<City>();
        }
        cities.add(city);
        notifyItemInserted(cities.size());
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }


    protected class CityViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_rv_citylist_name)
        TextView cityname;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
