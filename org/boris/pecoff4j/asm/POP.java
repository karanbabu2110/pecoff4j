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



public class POP extends AbstractInstruction {
  private int register;

  public POP(int register) {
    this.register = register;
    this.code = toCode(0x58 | register);
  }


  public String toIntelAssembly() {
    return "pop  " + Register.to32(register);//NO i18N
  }
}
