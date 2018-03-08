package pl.fulllegitcode.storage;

import android.content.Intent;

public class ActivityResultCallback {

  interface ActivityResultInnerCallback {
    String onActivityResult(int resultCode, Intent data);
  }


  private int _requestCode;
  public int requestCode() { return _requestCode; }

  private ActivityResultInnerCallback _innerCallback;
  private ActivityResultInnerCallback innerCallback() { return _innerCallback; }

  ActivityResultCallback(int requestCode, ActivityResultInnerCallback innerCallback) {
    _requestCode = requestCode;
    _innerCallback = innerCallback;
  }

  public String onActivityResult(int resultCode, Intent data) {
    return innerCallback().onActivityResult(resultCode, data);
  }

}
