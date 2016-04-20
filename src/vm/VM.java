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

  // constructor
  public VM(int[] code, int main, int datasize) {
    this.code = code;
    this.ip = main;
    this.data = new int[datasize]; // init size of data memory
    this.stack = new int[100]; // arbitrary size for call stack
  }

  public void cpu() {
    // Fetch
    int opcode = code[ip];
    
    ip++;

    // Decode
    switch (opcode) {
      case HALT :
        return;
    }


  }
}