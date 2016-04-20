package vm;

import static vm.Bytecode.*;

import java.util.List;
import java.util.ArrayList;

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
      if (trace) {
        disassemble(opcode); // print stack trace
      }      

      // Decode
      ip++;
      switch (opcode) {
        case ICONST :
          int v = code[ip];
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

  private void disassemble (int opcode) {
    // print instructions and operands
    Instruction instr = Bytecode.instructions[opcode];
    System.err.printf("%04d: %s", ip, instr.name);
    if (instr.nOperands==1) {
      System.err.printf(" %d", code[ip+1]);
    }
    else if (instr.nOperands==2) {
      System.err.printf(" %d, %d", code[ip+1], code[ip+2]);
    }

    // print stack
    List<Integer> stck = new ArrayList<Integer>();
    for (int i = 0; i <= sp; i++) {
      stck.add(stack[i]);
    }
    System.err.print("\t\t" + stck);

    System.err.println(); // new line
  };
}