package me.lshare.wownote.helper;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;

import com.meisolsson.githubsdk.core.ServiceGenerator;
import com.meisolsson.githubsdk.model.ContentCommit;
import com.meisolsson.githubsdk.model.GitHubToken;
import com.meisolsson.githubsdk.model.Repository;
import com.meisolsson.githubsdk.model.User;
import com.meisolsson.githubsdk.model.request.RequestToken;
import com.meisolsson.githubsdk.model.request.repository.CreateContent;
import com.meisolsson.githubsdk.model.request.repository.CreateRepository;
import com.meisolsson.githubsdk.service.repositories.RepositoryContentService;
import com.meisolsson.githubsdk.service.repositories.RepositoryService;
import com.meisolsson.githubsdk.service.users.UserService;

import net.danlew.android.joda.JodaTimeAndroid;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.lshare.wownote.R;
import okhttp3.HttpUrl;
import retrofit2.Response;

/**
 * @author Lshare
 * @date 2017/7/2
 */
public class GitHubSDKHelper {
  public static void init(Context context) {
    JodaTimeAndroid.init(context.getApplicationContext());
  }

  public static String getOAuthUrl(Context context) {
    String initialScope = "user,public_repo,repo,delete_repo,notifications,gist";
    HttpUrl.Builder url = new HttpUrl.Builder().scheme("https")
                                               .host(context.getString(R.string.github_oauth_host))
                                               .addPathSegment("login")
                                               .addPathSegment("oauth")
                                               .addPathSegment("authorize")
                                               .addQueryParameter("scope", initialScope)
                                               .addQueryParameter("client_id",
                                                                  context.getString(R.string.github_client_id));
    return url.toString();
  }

  public static Single<Response<GitHubToken>> accessTokenObserver(Context context, Uri uri) {
    String code = uri.getQueryParameter("code");
    RequestToken request = RequestToken.builder()
                                       .clientId(context.getString(R.string.github_client_id))
                                       .clientSecret(context.getString(R.string.github_secret))
                                       .redirectUri(context.getString(R.string.github_redirect_uri))
                                       .code(code)
                                       .build();
    return ServiceGenerator.createAuthService()
                           .getToken(request)
                           .subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread());
  }

  public static Single<Response<User>> userObserver(Context context) {
    return ServiceGenerator.createService(context, UserService.class)
                           .getUser()
                           .subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread());
  }

  public static Single<Response<Repository>> createRepositoryObserver(Context context,
                                                                      String repositoryName) {
    CreateRepository createRepository = CreateRepository.builder().name(repositoryName)
                                                        //.isPrivate(false)
                                                        //.autoInit(true)
                                                        //.hasIssues(false)
                                                        //.hasWiki(false)
                                                        .build();
    return ServiceGenerator.createService(context, RepositoryService.class)
                           .createRepository(createRepository)
                           .subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread());
  }

  public static Single<Response<ContentCommit>> createFileObserver(Context context,
                                                                   String filePath,
                                                                   String html) {
    CreateContent createContent = CreateContent.builder()
                                               .message("upload " + filePath)
                                               .content(new String(Base64.encode(html.getBytes(),
                                                                                 Base64.DEFAULT)))
                                               .build();
    return ServiceGenerator.createService(context, RepositoryContentService.class)
                           .createFile("lsharel", "lsharel.github.io", filePath, createContent)
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribeOn(Schedulers.io());
  }
}
