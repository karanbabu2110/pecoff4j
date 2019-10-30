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

import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.ResourceDirectory;
import org.boris.pecoff4j.io.PEParser;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RCEdit {
  public static void main(String[] args) throws Exception {
    launch(new String[0]);
    launch(new String[]{"/I", "test/WinRun4J.exe", "test/eclipse.ico"});//NO i18N
  }

  public static void launch( String[] args) throws Exception {
    assertArgCount(args, 2, 3);

    String option = args[0].toUpperCase();
    if ("/I".equals(option)) {//NO i18N
      assertArgCount(args, 3, 3);
      addIcon(args[1], args[2]);
    } else if ("/N".equals(option)) {//NO i18N
      assertArgCount(args, 3, 3);
      setIni(args[1], args[2]);
    } else if ("/S".equals(option)) {//NO i18N
      assertArgCount(args, 3, 3);
      setSplash(args[1], args[2]);
    }
  }

  private static void addIcon(String exe, String icon) throws IOException {
    PE pe = PEParser.parse(exe);
    IconFile ic = IconFile.parse(icon);
  }

  private static void setIni(String exe, String ini) throws IOException {
    PE pe = PEParser.parse(exe);
    byte[] inib = IO.toBytes(new File(ini));
    ResourceDirectory rd = pe.getImageData().getResourceTable();
  }

  private static void setSplash(String exe, String splash) throws IOException {
    PE pe = PEParser.parse(exe);
    byte[] spb = IO.toBytes(new File(splash));
    ResourceDirectory rd = pe.getImageData().getResourceTable();

  }

  private static void assertArgCount( String[] args, int min, int max) {
    if (args.length < min || args.length > max) {
      printUsage();
      System.exit(1);
    }
  }

  private static void printUsage() {
    printf("WinRun4J Resource Editor v2.0 (winrun4j.sf.net)\n\n");//NO i18N
    printf("Edits resources in executables (EXE) and dynamic link-libraries (DLL).\n\n");//NO i18N
    printf("RCEDIT <option> <exe/dll> [resource]\n\n");//NO i18N
    printf("  filename\tSpecifies the filename of the EXE/DLL.\n");//NO i18N
    printf("  resource\tSpecifies the name of the resource to add to the EXE/DLL.\n");//NO i18N
    printf("  /I\t\tSet the icon as the default icon for the executable.\n");//NO i18N
    printf("  /A\t\tAdds an icon to the EXE/DLL.\n");//NO i18N
    printf("  /N\t\tSets the INI file.\n");//NO i18N
    printf("  /J\t\tAdds a JAR file.\n");//NO i18N
    printf("  /E\t\tExtracts a JAR file from the EXE/DLL.\n");//NO i18N
    printf("  /S\t\tSets the splash image.\n");//NO i18N
    printf("  /C\t\tClears all resources from the EXE/DLL.\n");//NO i18N
    printf("  /L\t\tLists the resources in the EXE/DLL.\n");//NO i18N
    printf("  /P\t\tOutputs the contents of the INI file in the EXE.\n");//NO i18N
  }

  private static void printf(String s) {
    Logger.getLogger(RCEdit.class.getName()).log(Level.INFO, s);
  }
}
