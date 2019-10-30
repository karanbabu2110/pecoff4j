/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.asm;



public class Register {

  public static String to32(int register) {
    switch (register) {
      case 0:
        return "eax";//NO i18N
      case 1:
        return "ecx";//NO i18N
      case 2:
        return "edx";//NO i18N
      case 3:
        return "ebx";//NO i18N
      case 4:
        return "esp";//NO i18N
      case 5:
        return "ebp";//NO i18N
      case 6:
        return "esi";//NO i18N
      case 7:
        return "edi";//NO i18N
    }
    return null;
  }
}
