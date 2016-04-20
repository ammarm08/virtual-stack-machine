package vm;

import static vm.Bytecode.*;

public class Test {

  // program that pushes 99 onto stack then prints it
  static int[] programBytecode = {
    ICONST, 99,
    GSTORE, 0,
    GLOAD, 0,
    PRINT,
    HALT
  };

  public static void main(String[] args) {
    int datasize = 1;
    int mainIp = 0;
    VM vm = new VM(programBytecode, mainIp, datasize);
    vm.trace = true;
    vm.cpu();
    System.out.println("Tests done.");
  }
}