package com.mob.mobpush_plugin;

import android.content.Context;

import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;

import java.util.Collections;
import java.util.HashMap;

import com.mob.tools.utils.Hashon;
import io.flutter.plugin.common.EventChannel;

/**
 * MobpushPlugin
 */
public class MobpushReceiverPlugin implements EventChannel.StreamHandler {
    private static MobPushReceiver mobPushReceiver;
    private static Hashon hashon = new Hashon();

    public static MobPushReceiver getMobPushReceiver(){
        return mobPushReceiver;
    }

    private MobPushReceiver createMobPushReceiver(final EventChannel.EventSink event) {
        mobPushReceiver = new MobPushReceiver() {
            @Override
            public void onCustomMessageReceive(Context context, MobPushCustomMessage mobPushCustomMessage) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("action", 0);
                map.put("result", Collections.singletonMap("extrasMap", mobPushCustomMessage.getExtrasMap()));
                event.success(map);
            }

            @Override
            public void onNotifyMessageReceive(Context context, MobPushNotifyMessage mobPushNotifyMessage) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("action", 1);
                map.put("result", Collections.singletonMap("extrasMap", mobPushNotifyMessage.getExtrasMap()));
                event.success(map);
            }

            @Override
            public void onNotifyMessageOpenedReceive(Context context, final MobPushNotifyMessage mobPushNotifyMessage) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("action", 2);
                map.put("result", Collections.singletonMap("extrasMap", mobPushNotifyMessage.getExtrasMap()));
                event.success(map);
            }

            @Override
            public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {

            }

            @Override
            public void onAliasCallback(Context context, String alias, int operation, int errorCode) {

            }
        };
        return mobPushReceiver;
    }

    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {
        mobPushReceiver = createMobPushReceiver(eventSink);
        MobPush.addPushReceiver(mobPushReceiver);
    }

    @Override
    public void onCancel(Object o) {
        MobPush.removePushReceiver(mobPushReceiver);
    }
}
