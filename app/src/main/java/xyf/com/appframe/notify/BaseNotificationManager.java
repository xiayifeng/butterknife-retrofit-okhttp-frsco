package xyf.com.appframe.notify;

import android.app.Notification;

/**
 * Created by sh-xiayf on 16/10/8.
 */
public abstract class BaseNotificationManager {

    public abstract Notification create(BaseNotifyData data);

    public abstract void notify(Notification notification,int type);

}
