package xyf.com.appframe.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class MusicSearchResult {

    public @SerializedName("code") int codeStr;
    public String status;
    public String msg;
    public @SerializedName("data") MusicSearchResultData mdata;

}
