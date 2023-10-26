package com.xiaopeng.lib.framework.netchannelmodule.messaging.profile;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.MessagingException;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.exception.MessagingExceptionImpl;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ProfileFactory {
    private static Map<IMessaging.CHANNEL, AbstractChannelProfile> map = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.ProfileFactory$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$lib$framework$moduleinterface$netchannelmodule$messaging$IMessaging$CHANNEL;

        static {
            int[] iArr = new int[IMessaging.CHANNEL.values().length];
            $SwitchMap$com$xiaopeng$lib$framework$moduleinterface$netchannelmodule$messaging$IMessaging$CHANNEL = iArr;
            try {
                iArr[IMessaging.CHANNEL.COMMUNICATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$framework$moduleinterface$netchannelmodule$messaging$IMessaging$CHANNEL[IMessaging.CHANNEL.REPORTING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$framework$moduleinterface$netchannelmodule$messaging$IMessaging$CHANNEL[IMessaging.CHANNEL.TESTING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static AbstractChannelProfile channelProfile(IMessaging.CHANNEL channel) throws MessagingException {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$lib$framework$moduleinterface$netchannelmodule$messaging$IMessaging$CHANNEL[channel.ordinal()];
        if (i == 1) {
            AbstractChannelProfile abstractChannelProfile = map.get(channel);
            if (abstractChannelProfile == null) {
                CommunicationChannelProfile communicationChannelProfile = new CommunicationChannelProfile();
                map.put(channel, communicationChannelProfile);
                return communicationChannelProfile;
            }
            return abstractChannelProfile;
        } else if (i == 2) {
            AbstractChannelProfile abstractChannelProfile2 = map.get(channel);
            if (abstractChannelProfile2 == null) {
                ReportingChannelProfile reportingChannelProfile = new ReportingChannelProfile();
                map.put(channel, reportingChannelProfile);
                return reportingChannelProfile;
            }
            return abstractChannelProfile2;
        } else if (i == 3) {
            AbstractChannelProfile abstractChannelProfile3 = map.get(channel);
            if (abstractChannelProfile3 == null) {
                TestingChannelProfile testingChannelProfile = new TestingChannelProfile();
                map.put(channel, testingChannelProfile);
                return testingChannelProfile;
            }
            return abstractChannelProfile3;
        } else {
            throw new MessagingExceptionImpl(16);
        }
    }
}
