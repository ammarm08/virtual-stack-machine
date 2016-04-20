package vm;

public class Bytecode {
  /** 
   * 
   * NOTE: Simple VM ONLY deals with INTEGERS
   *
   */

  // add, subtract, multiply
  public static final short IADD = 1;
  public static final short ISUB = 2;
  public static final short IMULT = 3;

  // less than, equal
  public static final short ILT = 4;
  public static final short IEQ = 5;

  // branch, branch if true, branch if false
  public static final short BR = 6;
  public static final short BRT = 7;
  public static final short BRF = 8;

  // push constant to stack
  public static final short ICONST = 9;

  // load from locals, load from globals
  public static final short LOAD = 10;
  public static final short GLOAD = 11;

  // store to locals, store to globals
  public static final short STORE = 12;
  public static final short GSTORE = 13;

  // print, pop off stack, halt program
  public static final short PRINT = 14;
  public static final short POP = 15;
  public static final short HALT = 16;

}