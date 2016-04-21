package vm;

import static vm.Bytecode.*;

public class Test {

  static int[] programBytecode = {
    ICONST, 100, //  0 --- 
    GSTORE, 0, //  2 --- x = 100
    ICONST, 2, //  4 ---
    GSTORE, 1, //  6 --- y = 2
    GLOAD, 1,  //  8 ---
    GLOAD, 0,  // 10 ---
    ILT,       // 12 --- if x <= y ...
    BRF, 24,   // 13 --- jump to 24 (HALT)
    GLOAD, 1,  // 15 ---
    ICONST, 1, // 17 ---
    IADD,      // 19 --- increment y by 1
    GSTORE, 1, // 20 --- store updated value of y
    BR, 8,     // 22 --- jump back to 8 (WHILE LOOP!!)
    GLOAD, 1,  // 24 --- load y
    PRINT,     // 26 --- print y
    HALT       // 28 --- end
  };

  public static void main(String[] args) {
    int datasize = 100;
    int mainIp = 0;
    VM vm = new VM(programBytecode, mainIp, datasize);
    vm.trace = true;
    vm.exec();
    System.out.println("Tests done.");
  }
}