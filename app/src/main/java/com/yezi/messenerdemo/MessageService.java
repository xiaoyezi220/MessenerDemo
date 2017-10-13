package com.yezi.messenerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ZQ on 2017/10/13.
 */

public class MessageService extends Service {
    private String TAG = this.getClass().getSimpleName();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return messenger.getBinder();

    }

    private class MessenerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 101:
                    Log.e(TAG,"收到客户端的消息：" + msg.getData().getString("msg"));

                    /***
                     * 这里得到客户端传递过来的信使对象，用于和客户端通信
                     */
                    Messenger clientMessener =  msg.replyTo;
                    Message message = new Message();
                    message.what = 102;
                    Bundle bundle = new Bundle();
                    bundle.putString("smsg","I am server ,I received the message!");
                    message.setData(bundle);
                    try {
                        clientMessener.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private Messenger messenger = new Messenger(new MessenerHandler());
}
