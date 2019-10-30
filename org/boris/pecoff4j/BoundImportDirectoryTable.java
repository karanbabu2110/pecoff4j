/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j;



import java.util.ArrayList;
import java.util.List;

public class BoundImportDirectoryTable {

  private List<BoundImport> imports = new ArrayList<BoundImport>();

  public void add(BoundImport bi) {
    imports.add(bi);

    if (imports.size() > 0x10000) {
      // What is this? A Lotus Notes exe?
      throw new IllegalStateException("Too many imports, are you sure the executable is valid?");//NO i18N
    }
  }

  public int size() {
    return imports.size();
  }

  public BoundImport get(int index) {
    return imports.get(index);
  }
}
