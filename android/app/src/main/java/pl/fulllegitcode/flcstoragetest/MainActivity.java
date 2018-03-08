package pl.fulllegitcode.flcstoragetest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.util.Locale;

import pl.fulllegitcode.storage.ActivityResultCallback;
import pl.fulllegitcode.storage.Path;
import pl.fulllegitcode.storage.Storage;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "FlcStorageTest";

  private static final String[] PERMISSIONS = new String[] {
    Manifest.permission.WRITE_EXTERNAL_STORAGE
  };


  private ActivityResultCallback _getSDCardPathCallback;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    _requestPermissions();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    boolean allGranted = true;
    for (int result : grantResults) {
      if (result != PackageManager.PERMISSION_GRANTED) {
        allGranted = false;
        break;
      }
    }
    if (allGranted) {
      _test();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (_getSDCardPathCallback != null && requestCode == _getSDCardPathCallback.requestCode()) {
      String path = _getSDCardPathCallback.onActivityResult(resultCode, data);
      _handleSDCardPath(path);
    }
  }

  private void _requestPermissions() {
    ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
  }

  private void _test() {
    Storage.debug = true;
    _getSDCardPathCallback = Storage.getSDCardPath(this);
  }

  private void _handleSDCardPath(String path) {
    Log.d(TAG, String.format(Locale.ENGLISH, "sd card path: %s", path));
  }

}
