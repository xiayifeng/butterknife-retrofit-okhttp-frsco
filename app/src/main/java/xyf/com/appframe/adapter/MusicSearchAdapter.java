package xyf.com.appframe.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyf.com.appframe.R;
import xyf.com.appframe.javabean.MusicSearchResultDataData;
import xyf.com.appframe.recycleviewtools.FileSizeUtils;
import xyf.com.appframe.recycleviewtools.TImeUtils;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class MusicSearchAdapter extends RecyclerArrayAdapter<MusicSearchResultDataData>{

    public MusicSearchAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MusicViewHolder(inflater.inflate(R.layout.item_rv_music_search,null));
    }

    protected class MusicViewHolder extends BaseViewHolder<MusicSearchResultDataData>
    {
        @BindView(R.id.music_search_filename)
        TextView filename;

        @BindView(R.id.music_search_singername)
        TextView singername;

        @BindView(R.id.music_search_album_name)
        TextView albumname;

        @BindView(R.id.music_search_duration)
        TextView duration;

        @BindView(R.id.music_search_filesize)
        TextView filesize;

        public MusicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void setData(MusicSearchResultDataData data) {
            Context mContext = itemView.getContext();
            filename.setText(String.format(mContext.getString(R.string.music_filename),data.filename));
            singername.setText(String.format(mContext.getString(R.string.music_signername),data.singername));
            if (!TextUtils.isEmpty(data.album_name))
            {
                albumname.setText(String.format(mContext.getString(R.string.music_albumname),data.album_name));
            }
            duration.setText(String.format(mContext.getString(R.string.music_duration), TImeUtils.secToTime((int) data.duration)));
            filesize.setText(String.format(mContext.getString(R.string.music_filesize), FileSizeUtils.convertFileSize(data.filesize)));
        }
    }

}
