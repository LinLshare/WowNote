package me.lshare.wownote.helper;

import android.util.Log;

public final class DLog {
  public static void i(String tag, String msg, Object... args) {
    if (AppUtils.isDebug()) {
      Log.i(tag, format(msg, args));
    }
  }

  public static void d(String tag, String msg, Object... args) {
    if (AppUtils.isDebug()) {
      Log.d(tag, format(msg, args));
    }
  }

  public static void w(String tag, String msg, Throwable tr) {
    if (AppUtils.isDebug()) {
      Log.w(tag, msg, tr);
    }
  }

  public static void w(String tag, String msg, Object... args) {
    if (AppUtils.isDebug()) {
      Log.w(tag, format(msg, args));
    }
  }

  public static void e(String tag, String msg, Throwable tr) {
    if (AppUtils.isDebug()) {
      Log.e(tag, msg, tr);
    }
  }

  public static void e(String tag, String msg, Object... args) {
    if (AppUtils.isDebug()) {
      Log.e(tag, format(msg, args));
    }
  }

  public static void v(String tag, String msg, Object... args) {
    if (AppUtils.isDebug()) {
      Log.v(tag, format(msg, args));
    }
  }

  private static String format(String msg, Object[] args) {
    if (args.length > 0) {
      msg = String.format(msg, args);
    }
    return msg;
  }
}
