package vm;

import static vm.Bytecode.*;

public class Test {

  // program that pushes 99 onto stack then prints it
  static int[] hello = {
    ICONST, 99,
    PRINT,
    HALT
  };

  public static void main(String[] args) {
    VM vm = new VM(hello, 0, 0); // bytecode[], ip, datasize
    vm.cpu();
    System.out.println("Tests done.");
  }
}