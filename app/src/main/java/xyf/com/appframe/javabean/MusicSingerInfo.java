package xyf.com.appframe.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sh-xiayf on 16/7/20.
 */
public class MusicSingerInfo {

    public @SerializedName("code") int codeint;
    public String status;
    public String msg;
    public @SerializedName("data") MusicSingerInfoData mdata;

}
