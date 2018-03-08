package pl.fulllegitcode.storage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.provider.DocumentFile;
import android.util.Log;

import java.io.File;
import java.util.Locale;

public class Storage {

  private static final String TAG = "FlcStorage";
  public static boolean debug = false;

  //region log

  static void log(String message) {
    Log.d(TAG, message);
  }

  static void log(String message, boolean debug) {
    if (debug && Storage.debug) {
      log(message);
    }
  }

  static void logError(String message) {
    Log.e(TAG, message);
  }

  static void logError(String message, boolean debug) {
    if (debug && Storage.debug) {
      logError(message);
    }
  }

  //endregion


  public static boolean fileExists(String path) {
    File file = new File(path);
    return file.exists();
  }

  //region getSDCardPath

  public static ActivityResultCallback getSDCardPath(Activity activity) {
    File storage = new File("/storage");
    File[] drives = storage.listFiles();
    for (File drive : drives) {
      if (!drive.isDirectory()) {
        continue;
      }
      File file = new File(drive, "Android");
      if (file.exists()) {
        String path = drive.getPath();
        log(String.format(Locale.ENGLISH, "[Storage.getSDCardPath] valid path found. path=%s", path), true);
        return _requestSDCardPermissions(activity, path);
      }
    }
    return null;
  }

  private static ActivityResultCallback _requestSDCardPermissions(Activity activity, final String path) {
    int requestCode = 1337; //todo temporarily hardcoded
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    activity.startActivityForResult(intent, requestCode);
    return new ActivityResultCallback(requestCode, new ActivityResultCallback.ActivityResultInnerCallback() {
      @Override
      public String onActivityResult(int resultCode, Intent data) {
        Uri resultUri = data.getData();
        String resultPath = resultUri != null ? resultUri.getPath() : null;
        log(String.format(Locale.ENGLISH, "[Storage.requestSDCardPermissions] result. code=%d uri=%s", resultCode, resultPath), true);
        return path;
      }
    });
  }

  //endregion

  public static void writeFile(String path) {
    DocumentFile file = DocumentFile.fromFile(new File(path));
    log(String.format(Locale.ENGLISH, "%s %b", path, file.canWrite()));
  }

}
