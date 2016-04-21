package vm;

import static vm.Bytecode.*;

public class Test {

  static int[] programBytecode = {
    ICONST, 1,
    ICONST, 2,
    IADD,
    PRINT,
    HALT
  };

  public static void main(String[] args) {
    int datasize = 1;
    int mainIp = 0;
    VM vm = new VM(programBytecode, mainIp, datasize);
    vm.trace = true;
    vm.exec();
    System.out.println("Tests done.");
  }
}