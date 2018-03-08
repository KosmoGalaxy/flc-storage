package pl.fulllegitcode.storage;

import java.io.File;

public class Path {

  private static final String BEGINNING_DELIMITER_REGEX = "^/(.*)";
  private static final String END_DELIMITER_REGEX = "(.*?)/$";

  public static String join(String... paths) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < paths.length; i++) {
      String path = paths[i];
      if (i > 0) {
        path = path.replaceFirst(BEGINNING_DELIMITER_REGEX, "$1");
      }
      path = path.replaceFirst(END_DELIMITER_REGEX, "$1");
      result.append(i == 0 ? path : File.separator + path);
    }
    return result.toString();
  }

}
