package xyf.com.appframe.javabean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class MusicSearchResultDataData implements Parcelable{
    public String filename;
    public String extname;
    public long m4afilesize;
    public long filesize;
    public long bitrate;
    public int isnew;
    public long duration;
    public String album_name;
    public String singername;
    public String hash;

    protected MusicSearchResultDataData(Parcel in) {
        filename = in.readString();
        extname = in.readString();
        m4afilesize = in.readLong();
        filesize = in.readLong();
        bitrate = in.readLong();
        isnew = in.readInt();
        duration = in.readLong();
        album_name = in.readString();
        singername = in.readString();
        hash = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filename);
        dest.writeString(extname);
        dest.writeLong(m4afilesize);
        dest.writeLong(filesize);
        dest.writeLong(bitrate);
        dest.writeInt(isnew);
        dest.writeLong(duration);
        dest.writeString(album_name);
        dest.writeString(singername);
        dest.writeString(hash);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MusicSearchResultDataData> CREATOR = new Creator<MusicSearchResultDataData>() {
        @Override
        public MusicSearchResultDataData createFromParcel(Parcel in) {
            return new MusicSearchResultDataData(in);
        }

        @Override
        public MusicSearchResultDataData[] newArray(int size) {
            return new MusicSearchResultDataData[size];
        }
    };
}
