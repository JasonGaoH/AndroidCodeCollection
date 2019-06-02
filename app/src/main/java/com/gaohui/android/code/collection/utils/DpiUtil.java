package com.gaohui.android.code.collection.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class DpiUtil {
    private static final String TAG = DpiUtil.class.getSimpleName();
    private static Display defaultDisplay;
    private static Point outSize = null;

    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (context == null) {
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int percentWidth(Context context, float f) {
        return (int) (((float) getWidth(context)) * f);
    }

    public static int percentHeight(Context context, float f) {
        return (int) (((float) getHeight(context)) * f);
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        if (displayMetrics == null) {
            return 0;
        }
        return displayMetrics.widthPixels;
    }

    public static int getHeight(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        if (displayMetrics == null) {
            return 0;
        }
        return displayMetrics.heightPixels;
    }

    public static int dip2px(Context context, float f) {
        return (int) (((double) (getDensity(context) * f)) + 0.5d);
    }

    public static int sp2px(Context context, float f) {
        return (int) (((double) (getFontDensity(context) * f)) + 0.5d);
    }

    public static int px2dip(Context context, float f) {
        return (int) (((double) (f / getDensity(context))) + 0.5d);
    }

    public static int px2sp(Context context, float f) {
        return (int) (((double) (f / getFontDensity(context))) + 0.5d);
    }

    public static float getDensity(Context context) {
        if (context == null) {
            return 2.0f;
        }
        return context.getResources().getDisplayMetrics().density;
    }

    public static float getFontDensity(Context context) {
        if (context == null) {
            return 2.0f;
        }
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int getContentHeight(Activity activity) {
        View contentView = getContentView(activity);
        if (contentView == null) {
            return 0;
        }
        return contentView.getHeight();
    }

    private static View getContentView(Activity activity) {
        if (activity == null) {
            return null;
        }
        return activity.getWindow().getDecorView().findViewById(16908290);
    }

    public static int getAppWidth(Activity activity) {
        if (activity != null) {
            try {
                Point point = new Point();
                activity.getWindowManager().getDefaultDisplay().getSize(point);
                return point.x;
            } catch (Exception e) {
            }
        }
        if (outSize == null) {
            Class<DpiUtil> cls = DpiUtil.class;
            synchronized (DpiUtil.class) {
                if (outSize == null) {
                    getPxSize(activity);
                }
            }
        }
        return outSize.x;
    }

    public static void getPxSize(Context context) {
        Display defaultDisplay2 = getDefaultDisplay(context);
        outSize = new Point();
        defaultDisplay2.getSize(outSize);
    }

    public static Display getDefaultDisplay(Context context) {
        if (defaultDisplay == null) {
            defaultDisplay = ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay();
        }
        return defaultDisplay;
    }
}

