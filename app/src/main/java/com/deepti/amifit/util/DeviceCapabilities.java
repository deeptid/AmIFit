package com.deepti.amifit.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.util.UUID;
/**
 * Created by deepti on 11/01/17.
 */

public class DeviceCapabilities {

    // LogCat tag
    private static String TAG = DeviceCapabilities.class.getSimpleName();



    private static DeviceCapabilities instance = new DeviceCapabilities();

    public static String DEVICE_TYPE = "Android";
    public static String IDIOM_TAB = "TAB";

    public static String IDIOM_BRICK = "BRICK";

    protected volatile static UUID uuid;

    private static Context _context;

    public synchronized static DeviceCapabilities getInstance() {
        return instance;
    }

    public synchronized static void initialize(Context context) {
        _context = context;
        if (instance == null) {
            instance = new DeviceCapabilities();
        }
    }

    public static Display display(Context context) {
        Display display = null;

        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return display;
    }

    public static Point getScreenSizeInDP(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        int dpHeight = Math.round(displayMetrics.heightPixels / displayMetrics.density);
        int dpWidth = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        Point point = new Point(dpWidth, dpHeight);
        return point;
    }


    public static String getDeviceType() {
        return DEVICE_TYPE;
    }

    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getOs() {
        return Build.VERSION.RELEASE;
    }

    public static String getOsVersionString() {
        return Build.VERSION.CODENAME;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }

    public static String getModel() {
        String model = Build.MODEL;
        return model;
    }


    public static String getIdiom(Context context) {

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            return IDIOM_TAB;
        }else{
            return IDIOM_BRICK;
        }

    }

    public static int getCapabilities(Context context) {
        int caps = 1;

        PackageManager pm = context.getPackageManager();
        boolean hasCompass = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);


        return caps;
    }


}

