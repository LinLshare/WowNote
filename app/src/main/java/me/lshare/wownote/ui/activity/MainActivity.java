package me.lshare.wownote.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.meisolsson.githubsdk.model.ContentCommit;
import com.meisolsson.githubsdk.model.Repository;
import com.meisolsson.githubsdk.model.User;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import me.lshare.wownote.R;
import me.lshare.wownote.helper.DLog;
import me.lshare.wownote.helper.DToast;
import me.lshare.wownote.helper.GitHubSDKHelper;
import me.lshare.wownote.persistencce.AccountManager;
import me.lshare.wownote.ui.widget.LoadingDialog;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private Context context;
  private LoadingDialog loadingDialog;
  private AccountManager accountManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    setContentView(R.layout.activity_main);
    init();
  }

  private void init() {
    loadingDialog = new LoadingDialog(context);
    loadingDialog.show();
    accountManager = new AccountManager(context);
    getUserInfo();
  }

  private void getUserInfo() {
    GitHubSDKHelper.userObserver(context).subscribe(new SingleObserver<Response<User>>() {
      @Override
      public void onSubscribe(Disposable d) {
      }

      @Override
      public void onSuccess(Response<User> userResponse) {
        loadingDialog.dismiss();
        User user = userResponse.body();
        accountManager.store(user.id(), user.name(), user.avatarUrl());
        DLog.d(TAG, user.toString());
      }

      @Override
      public void onError(Throwable e) {
        loadingDialog.dismiss();
        DLog.e(TAG, e.getMessage());
      }
    });
  }

  private void createGithubPageProject() {
    GitHubSDKHelper.createRepositoryObserver(context,
                                             AccountManager.currentUser().getName().toLowerCase() +
                                             ".github.io")
                   .subscribe(new SingleObserver<Response<Repository>>() {
                     @Override
                     public void onSubscribe(Disposable d) {
                     }

                     @Override
                     public void onSuccess(Response<Repository> repositoryResponse) {
                       Repository repository = repositoryResponse.body();
                       DToast.show(context, TAG, "create " + repository.fullName() + " success!");
                       DLog.e(TAG, repository.fullName());
                     }

                     @Override
                     public void onError(Throwable e) {
                       e.printStackTrace();
                     }
                   });
  }

  private void uploadFileToGithubPage(String filePath, String html) {
    GitHubSDKHelper.createFileObserver(context, filePath, html)
                   .subscribe(new SingleObserver<Response<ContentCommit>>() {
                     @Override
                     public void onSubscribe(Disposable d) {
                     }

                     @Override
                     public void onSuccess(Response<ContentCommit> contentCommitResponse) {
                       ContentCommit contentCommit = contentCommitResponse.body();
                       Log.e(TAG, contentCommit.toString());
                     }

                     @Override
                     public void onError(Throwable e) {
                       e.printStackTrace();
                     }
                   });
  }
}
