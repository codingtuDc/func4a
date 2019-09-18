package core.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import java.util.UUID;

public abstract class BaseService extends Service {

    protected HandlerThread thread;
    protected ServiceHandler handler;
    protected boolean mRedelivery;
    protected int startId;

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Intent data = (Intent) msg.obj;
            onHandleIntent(data);
        }
    }

    public ServiceHandler getHandler() {
        return handler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new HandlerThread("BaseService[" + UUID.randomUUID().toString() + "]");
        thread.start();
        handler = new ServiceHandler(thread.getLooper());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        this.startId = startId;
        action(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
    }

    public void action(Intent intent) {
        Message msg = handler.obtainMessage();
        msg.obj = intent;
        handler.sendMessage(msg);
    }

    public void action(Intent intent, long delay) {
        Message msg = handler.obtainMessage();
        msg.obj = intent;
        handler.sendMessageDelayed(msg, delay);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (thread != null) {
            thread.quit();
            thread = null;
        }
    }


    protected abstract void onHandleIntent(Intent data);

}
