package com.modulos.libreria.utilidadeslibreria.util;

import java.lang.reflect.Method;

import android.content.Context;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

public final class TelefonoInfoUtil {

    private static TelefonoInfoUtil telefonoInfo;
    private String imeiSIM1;
    private String imeiSIM2;
    private boolean isSIM1Lista;
    private boolean isSIM2Lista;

    public String getImeiSIM1() {
        return imeiSIM1;
    }

/*public static void setImeiSIM1(String imeiSIM1) {
    TelephonyInfo.imeiSIM1 = imeiSIM1;
}*/

    public String getImeiSIM2() {
        return imeiSIM2;
    }

/*public static void setImeiSIM2(String imeiSIM2) {
    TelephonyInfo.imeiSIM2 = imeiSIM2;
}*/

    public boolean isSIM1Lista() {
        return isSIM1Lista;
    }

/*public static void setSIM1Ready(boolean isSIM1Lista) {
    TelephonyInfo.isSIM1Lista = isSIM1Lista;
}*/

    public boolean isSIM2Lista() {
        return isSIM2Lista;
    }

/*public static void setSIM2Ready(boolean isSIM2Lista) {
    TelephonyInfo.isSIM2Lista = isSIM2Lista;
}*/

    public boolean isDualSIM() {
        return imeiSIM2 != null;
    }

    private TelefonoInfoUtil() {
    }

    public static TelefonoInfoUtil getInstance(Context context) {

        if (telefonoInfo == null) {

            telefonoInfo = new TelefonoInfoUtil();

            TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));

            telefonoInfo.imeiSIM1 = telephonyManager.getDeviceId();
            ;
            telefonoInfo.imeiSIM2 = null;

            try {
                telefonoInfo.imeiSIM1 = getDeviceIdBySlot(context, "getDeviceIdGemini", 0);
                telefonoInfo.imeiSIM2 = getDeviceIdBySlot(context, "getDeviceIdGemini", 1);
            } catch (GeminiMethodNotFoundException e) {
                e.printStackTrace();

                try {
                    telefonoInfo.imeiSIM1 = getDeviceIdBySlot(context, "getDeviceId", 0);
                    telefonoInfo.imeiSIM2 = getDeviceIdBySlot(context, "getDeviceId", 1);
                } catch (GeminiMethodNotFoundException e1) {
                    //Call here for next manufacturer's predicted method name if you wish
                    e1.printStackTrace();
                }
            }

            telefonoInfo.isSIM1Lista = telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
            telefonoInfo.isSIM2Lista = false;

            try {
                telefonoInfo.isSIM1Lista = getSIMStateBySlot(context, "getSimStateGemini", 0);
                telefonoInfo.isSIM2Lista = getSIMStateBySlot(context, "getSimStateGemini", 1);
            } catch (GeminiMethodNotFoundException e) {

                e.printStackTrace();

                try {
                    telefonoInfo.isSIM1Lista = getSIMStateBySlot(context, "getSimState", 0);
                    telefonoInfo.isSIM2Lista = getSIMStateBySlot(context, "getSimState", 1);
                } catch (GeminiMethodNotFoundException e1) {
                    //Call here for next manufacturer's predicted method name if you wish
                    e1.printStackTrace();
                }
            }
        }

        return telefonoInfo;
    }

    private static String getDeviceIdBySlot(Context context, String predictedMethodName, int slotID) throws GeminiMethodNotFoundException {

        String imei = null;

        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephony, obParameter);

            if (ob_phone != null) {
                imei = ob_phone.toString();

            }
        } catch (Exception e) {
            throw new GeminiMethodNotFoundException(predictedMethodName);
        }

        return imei;
    }

    private static boolean getSIMStateBySlot(Context context, String predictedMethodName, int slotID) throws GeminiMethodNotFoundException {

        boolean isReady = false;

        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimStateGemini = telephonyClass.getMethod(predictedMethodName, parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimStateGemini.invoke(telephony, obParameter);

            if (ob_phone != null) {
                int simState = Integer.parseInt(ob_phone.toString());
                if (simState == TelephonyManager.SIM_STATE_READY) {
                    isReady = true;
                }
            }
        } catch (Exception e) {
            throw new GeminiMethodNotFoundException(predictedMethodName);
        }

        return isReady;
    }


    private static class GeminiMethodNotFoundException extends Exception {

        private static final long serialVersionUID = -996812356902545308L;

        public GeminiMethodNotFoundException(String info) {
            super(info);
        }
    }
}