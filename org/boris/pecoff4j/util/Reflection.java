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




import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reflection {
  public static String toString( Object o) {
    StringBuilder sb = new StringBuilder();
    Field[] fields = o.getClass().getDeclaredFields();
    for (Field f : fields) {
      if (Modifier.isStatic(f.getModifiers())) {
        continue;
      }
      f.setAccessible(true);
      sb.append(f.getName());
      sb.append(": ");
      try {
        Object val = f.get(o);
        if (val instanceof Integer) {
          sb.append(f.get(o));
          sb.append(" (0x");//NO i18N
          sb.append(Integer.toHexString(((Integer) val).intValue()));
          sb.append(")");//NO i18N
        } else if (val instanceof Long) {
          sb.append(f.get(o));
          sb.append(" (0x");//NO i18N
          sb.append(Long.toHexString(((Long) val).longValue()));
          sb.append(")");//NO i18N
        } else if (val != null && val.getClass().isArray()) {
          if (val instanceof int[]) {
            int[] arr = (int[]) val;
            for (int i = 0; i < arr.length && i < 10; i++) {
              if (i != 0) {
                sb.append(", ");//NO i18N
              }
              sb.append(arr[i]);
            }
          } else if (val instanceof byte[]) {
            byte[] arr = (byte[]) val;
            for (int i = 0; i < arr.length && i < 10; i++) {
              if (i != 0) {
                sb.append(", ");//NO i18N
              }
              sb.append(Integer.toHexString(arr[i] & 0xff));
            }
          } else {
            Object[] arr = (Object[]) val;
            for (int i = 0; i < arr.length && i < 10; i++) {
              if (i != 0) {
                sb.append(", ");//NO i18N
              }
              sb.append(arr[i]);
            }
          }
        } else {
          sb.append(f.get(o));
        }
      } catch (Exception e) {
        sb.append(e.getMessage());
      }
      sb.append("\n");//NO i18N
    }
    return sb.toString();
  }


  public static String getConstantName( Class clazz, int value) throws Exception {
    Field[] fields = clazz.getDeclaredFields();
    Integer valObj = new Integer(value);
    for (int i = 0; i < fields.length; i++) {
      Field f = fields[i];
      if (Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers())) {
        if (f.get(null).equals(valObj)) {
          return f.getName();
        }
      }
    }

    return null;
  }

  public static void println( Object o) {
    Logger.getLogger(Reflection.class.getName()).log(Level.INFO,toString(o));
  }
}
