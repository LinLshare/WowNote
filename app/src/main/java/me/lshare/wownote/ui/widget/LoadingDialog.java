package me.lshare.wownote.ui.widget;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import me.lshare.wownote.R;

/**
 * @author Lshare
 * @date 2017/7/2
 */
public class LoadingDialog {

  private final MaterialDialog dialog;

  public LoadingDialog(Context activityContext) {
    dialog = new MaterialDialog.Builder(activityContext).content(R.string.dialog_hint_loading)
                                                        .progress(true, 0)
                                                        .build();
  }

  public void show() {
    dialog.show();
  }

  public void dismiss() {
    dialog.hide();
  }
}
