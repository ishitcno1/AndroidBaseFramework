package com.edinstudio.android.baseframework.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by albert on 15-5-20.
 */
public class SystemUtil {
    public static boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        return list.size() > 0;
    }

    public static void showToast(Context context, String message, int gravity) {
        if (context == null || TextUtils.isEmpty(message)) {
            return;
        }

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    public static void showToast(Context context, String message) {
        showToast(context, message, Gravity.BOTTOM);
    }

    public static void showNotification(Context context, int icon, String title, String content, PendingIntent pendingIntent) {
        if (context == null) {
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(icon);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setOngoing(false);
        builder.setAutoCancel(true);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Random random = new Random();
        int notifyId = random.nextInt();
        notificationManager.notify(notifyId, builder.build());
    }

    public static void toggleSoftInput(Context context, View view, boolean shouldShow) {
        if (context == null) {
            return;
        }

        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (shouldShow) {
            im.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } else {
            im.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void makeCall(Context context, String tel) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        if (isIntentAvailable(context, intent)) {
            context.startActivity(intent);
        } else {
            showToast(context, "请检查您的手机是否有拨号应用");
        }
    }

    // need permission com.android.launcher.permission.INSTALL_SHORTCUT
    public static void createShortCut(Activity activity, int iconResId,
                                      int appnameResId) {
        Intent shortcutintent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                activity.getString(appnameResId));

        Parcelable icon = Intent.ShortcutIconResource.fromContext(
                activity.getApplicationContext(), iconResId);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
                new Intent(activity.getApplicationContext(), activity.getClass()));

        activity.sendBroadcast(shortcutintent);
    }

    public static void saveToFile(Context context, Uri uri, String filePath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(context.getContentResolver().openInputStream(uri));
            bos = new BufferedOutputStream(new FileOutputStream(filePath, false));
            byte[] buffer = new byte[1024];

            bis.read(buffer);
            do {
                bos.write(buffer);
            } while (bis.read(buffer) != -1);
        } catch (IOException ioe) {

        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {

            }
        }
    }

    public static void vibrate(Context context, long milliseconds) {
        if (context == null) {
            return;
        }

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static Point displaySize(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    public static AlertDialog createDialog(Context context, int layout, int animation, boolean showSoftInput, int gravity) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (showSoftInput) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = gravity;
        window.setAttributes(lp);
        window.setWindowAnimations(animation);

        return dialog;
    }

    public static AlertDialog createDialogWithMargin(Context context, int layout, boolean showSoftInput, int margin) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (showSoftInput) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        Point size = displaySize(context);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = size.x - dpToPx(margin);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        return dialog;
    }

    public static AlertDialog createDialogFullScreen(Context context, int layout, boolean showSoftInput) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (showSoftInput) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        return dialog;
    }

    public static void registerLocalReceiver(Context context, BroadcastReceiver receiver, IntentFilter intentFilter) {
        if (context == null) {
            return;
        }

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.registerReceiver(receiver, intentFilter);
    }

    public static void unRegisterLocalReceiver(Context context, BroadcastReceiver receiver) {
        if (context == null) {
            return;
        }

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.unregisterReceiver(receiver);
    }

    public static void sendLocalBroadcast(Context context, Intent intent) {
        if (context == null) {
            return;
        }

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
    }
}
