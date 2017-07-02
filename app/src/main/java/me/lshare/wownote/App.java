package me.lshare.wownote;

import android.app.Application;

import me.lshare.wownote.helper.AppUtils;
import me.lshare.wownote.helper.GitHubSDKHelper;

/**
 * @author Lshare
 * @date 2017/7/2
 */
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    initThreePart();
  }

  private void initThreePart() {
    AppUtils.syncIsDebug(this);
    GitHubSDKHelper.init(this);
  }
}
