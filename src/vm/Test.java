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
    ILT,       // 12 ---
    BRF, 24,   // 13 --- if x <= y , jump to 24 (HALT), else ...
    GLOAD, 1,  // 15 ---
    ICONST, 1, // 17 ---
    IADD,      // 19 ---
    GSTORE, 1, // 20 --- y++
    BR, 8,     // 22 --- jump back to 8 (WHILE LOOP!!)
    GLOAD, 1,  // 24 --- load y and push to top of stack
    PRINT,     // 26 --- print top of stack
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