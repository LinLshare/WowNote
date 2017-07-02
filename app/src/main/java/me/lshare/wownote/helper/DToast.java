package me.lshare.wownote.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Lshare
 * @date 2017/7/2
 */
public final class DToast {
  public static void show(Context context, String msg) {
    if (AppUtils.isDebug()) {
      Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
  }

  public static void show(Context context, String tag, String msg) {
    show(context, "[" + tag + "]: " + msg);
  }
}
