package vm;

import static vm.Bytecode.*;

public class Test {

  static int[] helloTest = {
    ICONST, 99,
    PRINT,
    HALT
  };

  static int[] whileLoop = {
    ICONST, 15, //  0 --- 
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

  static int[] factorial = {
    // CONTROL FLOW
    LOAD, -3,   // 0 --- load n from locals
    ICONST, 2,  // 2 ---
    ILT,        // 4 --- if 2 < n...
    BRF, 10,    // 5 --- jump to 10
    // BASE CASE
    ICONST, 1,  // 7 --- base case: 1
    RET,        // 9 --- unwind, jump to 20
    // RECURSIVE CASE
    LOAD, -3,   // 10 -- n
    LOAD, -3,   // 12 --
    ICONST, 1,  // 14 --
    ISUB,       // 16 -- n-1
    CALL, 0, 1, // 17 -- build locals, jump to 0
    // THIS
    IMULT,      // 20 -- n * n - 1
    RET,        // 21 -- unwind at 20, finish at 27
    ICONST, 5,  // 22 --
    CALL, 0, 1, // 24 --
    PRINT,      // 27 -- print top of stack
    HALT        // 28 --
  };

  public static void main(String[] args) {
    // test 1: print integer
    VM vm = new VM(helloTest, 0, 0);
    vm.trace = true;
    vm.exec();

    // test 2: while loop, incrementing, and storing/loading globally
    vm = new VM(whileLoop, 0, 2);
    vm.trace = true;
    vm.exec();

    // test 3: factorial(5)
    vm = new VM(factorial, 22, 0); // START PROGRAM AT IP = 22
    vm.trace = true;
    vm.exec();
    System.out.println("Tests done.");
  }
}