package me.lshare.wownote.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.meisolsson.githubsdk.core.TokenStore;
import com.meisolsson.githubsdk.model.GitHubToken;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import me.lshare.wownote.R;
import me.lshare.wownote.helper.DLog;
import me.lshare.wownote.helper.GitHubSDKHelper;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

  public static final String INTENT_EXTRA_URL = "intent_extra_url";
  private static final String TAG = LoginActivity.class.getSimpleName();
  private static final int WEBVIEW_REQUEST_CODE = 233;
  private Button loginButton;
  private Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    setContentView(R.layout.activity_login);
    init();
  }

  private void init() {
    loginButton = (Button) findViewById(R.id.login_button);
    loginButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.login_button:
        openLoginPage();
        break;
      default:
        break;
    }
  }

  private void openLoginPage() {
    Intent intent = new Intent(this, LoginWebViewActivity.class);
    intent.putExtra(INTENT_EXTRA_URL, GitHubSDKHelper.getOAuthUrl(context));
    startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == WEBVIEW_REQUEST_CODE && resultCode == RESULT_OK) {
      onUserLoggedIn(data.getData());
    }
  }

  private void onUserLoggedIn(Uri uri) {
    if (uri == null || !uri.getScheme().equals(getString(R.string.github_redirect_uri_schema))) {
      return;
    }
    GitHubSDKHelper.accessTokenObserver(context, uri)
                   .subscribe(new SingleObserver<Response<GitHubToken>>() {
                     @Override
                     public void onSubscribe(Disposable d) {
                     }

                     @Override
                     public void onSuccess(Response<GitHubToken> gitHubTokenResponse) {
                       handleLoginResult(gitHubTokenResponse);
                     }

                     @Override
                     public void onError(Throwable e) {
                       DLog.e(TAG, e.getMessage());
                     }
                   });
  }

  private void handleLoginResult(Response<GitHubToken> gitHubTokenResponse) {
    GitHubToken token = gitHubTokenResponse.body();
    if (token.accessToken() != null) {
      TokenStore.getInstance(context).saveToken(token.accessToken());
      openMainActivity();
    } else if (token.error() != null) {
      DLog.e(TAG, token.error());
    }
  }

  private void openMainActivity() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }
}
