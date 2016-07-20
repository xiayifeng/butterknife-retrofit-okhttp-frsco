package xyf.com.appframe.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sh-xiayf on 16/7/20.
 */
public class MusicKrcInfo {
    public @SerializedName("code") int codeInt;
    public String status;
    public String msg;
    public @SerializedName("data") MusicKrcInfoData mdata;
}
