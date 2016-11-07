package xyf.com.appframe.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyf.com.appframe.R;
import xyf.com.appframe.javabean.MusicSearchResultDataData;
import xyf.com.appframe.javabean.MusicSingerInfo;
import xyf.com.appframe.network.NetWork;
import xyf.com.appframe.recycleviewtools.FileSizeUtils;
import xyf.com.appframe.recycleviewtools.TImeUtils;
import xyf.com.appframe.widget.MotionRecyclerViewItemClickListener;

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

    private MotionRecyclerViewItemClickListener OnItemClickListener;

    public void setOnItemClickListener(MotionRecyclerViewItemClickListener onItemClickListener) {
        OnItemClickListener = onItemClickListener;
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

        @BindView(R.id.music_play_bg)
        SimpleDraweeView singer_icon;

        public MusicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (OnItemClickListener != null) {
                        OnItemClickListener.onItemClick(getAdapterPosition() - headers.size(),new View[]{singer_icon});
                    }
                }
            });
        }

        @Override
        public void setData(final MusicSearchResultDataData data) {
            Context mContext = itemView.getContext();
            filename.setText(String.format(mContext.getString(R.string.music_filename),data.filename));
            singername.setText(String.format(mContext.getString(R.string.music_signername),data.singername));
            if (!TextUtils.isEmpty(data.album_name))
            {
                albumname.setText(String.format(mContext.getString(R.string.music_albumname),data.album_name));
            }
            duration.setText(String.format(mContext.getString(R.string.music_duration), TImeUtils.secToTime((int) data.duration)));
            filesize.setText(String.format(mContext.getString(R.string.music_filesize), FileSizeUtils.convertFileSize(data.filesize)));

            singer_icon.setTag(data.singername);
            NetWork.getMusicSingerInfoApi().getSingerInfo(data.singername)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MusicSingerInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(MusicSingerInfo musicSingerInfo) {
                            if (musicSingerInfo.codeint == 0) {
                                data.singerurl = musicSingerInfo.mdata.imageStr;

                                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(musicSingerInfo.mdata.imageStr)).build();

                                DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(imageRequest).build();

                                if (singer_icon.getTag().equals(musicSingerInfo.mdata.singername)) {
                                    singer_icon.setController(draweeController);
                                }
                            }
                        }
                    });

        }
    }

}
