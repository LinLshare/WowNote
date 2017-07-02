/*
 * Copyright (c) 2015 PocketHub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lshare.wownote.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;

import me.lshare.wownote.R;
import me.lshare.wownote.ui.widget.LoadingDialog;
import me.lshare.wownote.ui.widget.WebView;

public class LoginWebViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    WebView webView = new WebView(this);
    setContentView(webView);
    // Needs the be activated to allow GitHub to perform their requests.
    webView.getSettings().setJavaScriptEnabled(true);

    webView.setWebViewClient(new WebViewClient() {
      LoadingDialog dialog = new LoadingDialog(LoginWebViewActivity.this);

      @Override
      public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
        dialog.show();
      }

      @Override
      public void onPageFinished(android.webkit.WebView view, String url) {
        dialog.dismiss();
      }

      @Override
      public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
        Uri uri = Uri.parse(url);
        if (uri.getScheme().equals(getString(R.string.github_redirect_uri_schema))) {
          Intent data = new Intent();
          data.setData(uri);
          setResult(RESULT_OK, data);
          finish();
          return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
      }
    });
    webView.loadUrl(getIntent().getStringExtra(LoginActivity.INTENT_EXTRA_URL));
  }
}
