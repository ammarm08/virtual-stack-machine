package vm;

import static vm.Bytecode.*;

public class VM {
  int[] data; // data memory
  int[] code; // code memory
  int[] stack; // call stack

  // registers
  int ip; // instruction pointer
  int sp = -1; // stack pointer starts neg to offset for first instruction
  int fp; // frame pointer

  boolean trace = false;

  // constructor
  public VM(int[] code, int main, int datasize) {
    this.code = code;
    this.ip = main;
    this.data = new int[datasize]; // init size of data memory
    this.stack = new int[100]; // arbitrary size for call stack
  }

  public void cpu() {

    while (ip < code.length) {
      // Fetch
      int opcode = code[ip];
      ip++;

      // print stack trace!
      if (trace) {
        System.err.printf("%04d: %d\n", ip, opcode);
      }

      // Decode
      switch (opcode) {
        case ICONST :
          int v = code[ip]; // ip already got incremented (line 28)
          ip++;
          sp++; // prepare stack pointer
          stack[sp] = v; // push new val onto stack
          break;
        case PRINT :
          v = stack[sp]; // grab val from top of stack
          sp--; // "pop"
          System.out.println(v);
          break;
        case HALT :
          return; // exit
      }
    }
    


  }
}