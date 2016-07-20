package xyf.com.appframe.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class MusicSearchResultData {

    public int current_page;
    public String keyword;
    public int total_rows;
    public int total_page;
    public int page_size;
    public @SerializedName("data") List<MusicSearchResultDataData> datalist;

}
