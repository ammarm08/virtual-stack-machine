package vm;

import static vm.Bytecode.*;

public class Test {

  static int[] hello {
    HALT;
  };

  public static void main(String args[]) {
    VM vm = new VM(hello, 0, 0); // bytecode[], ip, datasize
    vm.cpu();
  }
}