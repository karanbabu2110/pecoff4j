/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.util;



import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Diff {
  public static boolean equals( File f1,  File f2) throws IOException {
    return equals(IO.toBytes(f1), IO.toBytes(f2));
  }

  public static boolean equals(byte[] b1, byte[] b2) {
    return Arrays.equals(b1, b2);
  }

  public static boolean equals( byte[] b1,  byte[] b2, boolean ignoreLength) {
    if (ignoreLength) {
      for (int i = 0; i < b1.length && i < b2.length; i++) {
        if (b1[i] != b2[i]) {
          return false;
        }
      }
      return true;
    } else {
      return Arrays.equals(b1, b2);
    }
  }

  public static boolean findDiff( byte[] b1,  byte[] b2, boolean ignoreLength) {
    boolean diff = false;
    if (b1.length != b2.length && !ignoreLength) {
      Logger.getLogger(Diff.class.getName()).log(Level.INFO, "Different lengths: " + Integer.toHexString(b1.length) + ", " + Integer.toHexString(b2.length));//NO i18N
      diff = true;
    }
    for (int i = 0; i < b1.length && i < b2.length; i++) {
      if (b1[i] != b2[i]) {
        int p = i;
        if (p < 0) {
          p = 0;
        }
        Logger.getLogger(Diff.class.getName()).log(Level.INFO,"Diff at " + Integer.toHexString(i));
        HexDump.dump(b1, p, 100);
        Logger.getLogger(Diff.class.getName()).log(Level.INFO,"-----");
        HexDump.dump(b2, p, 100);
        return true;
      }
    }

    return diff;
  }
}
