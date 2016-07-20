package xyf.com.appframe.widget;

import java.util.List;

import xyf.com.appframe.javabean.LrcContent;

/**
 * Created by sh-xiayf on 16/7/20.
 */
public class LrcUtils {

    public static int lrcIndex(int index, long currentTime, long duration, List<LrcContent> lrcList) {
        if(currentTime < duration) {
            for (int i = 0; i < lrcList.size(); i++) {
                if (i < lrcList.size() - 1) {
                    if (currentTime < lrcList.get(i).getLrcTime() && i == 0) {
                        index = i;
                    }
                    if (currentTime > lrcList.get(i).getLrcTime()
                            && currentTime < lrcList.get(i + 1).getLrcTime()) {
                        index = i;
                    }
                }
                if (i == lrcList.size() - 1
                        && currentTime > lrcList.get(i).getLrcTime()) {
                    index = i;
                }
            }
        }
        return index;
    }

}
