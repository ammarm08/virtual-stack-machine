package vm;

import static vm.Bytecode.*;

import java.util.List;
import java.util.ArrayList;

public class VM {
  public static final int DEFAULT_STACK_SIZE = 1000;
  public static final int TRUE = 1;
  public static final int FALSE = 0;

  // registers
  int ip; // instruction pointer
  int sp = -1; // stack pointer starts neg to offset for first instruction
  int fp = -1; // frame pointer

  // memory
  int[] code; // code memory
  int[] globals; // global variable memory
  int[] stack; // call stack

  boolean trace = false;

  // constructor
  public VM(int[] code, int startip, int datasize) {
    this.code = code;
    this.ip = startip;
    globals = new int[datasize];
    stack = new int[DEFAULT_STACK_SIZE];
  }

  public void exec() {
    stack[ip] = 0; // simulate OS call
    cpu();
  }

  protected void cpu() {
    int addr, offset;
    int a, b;

    while (ip < code.length) {
      // Fetch
      int opcode = code[ip];
      if (trace) { disassemble(); }  
      ip++; 

      // Decode
      switch (opcode) {

        case IADD :
          b = stack[sp--];
          a = stack[sp--];
          stack[++sp] = a + b; // push a + b
          break;

        case ISUB :
          b = stack[sp--];
          a = stack[sp--];
          stack[++sp] = a - b; // push a - b
          break;

        case IMULT :
          b = stack[sp--];
          a = stack[sp--];
          stack[++sp] = a * b; // push a * b
          break;

        case ILT : 
          b = stack[sp--];
          a = stack[sp--];
          stack[++sp] = a < b ? 1 : 0;
          break;

        case IEQ :
          b = stack[sp--];
          a = stack[sp--];
          stack[++sp] = a == b ? 1 : 0;
          break;

        case ICONST :
          stack[++sp] = code[ip++]; // push new val onto stack
          break;

        case BR :
          break;

        case BRT :
          break;

        case BRF :
          break;

        case GLOAD :
          addr = code[ip++]; // provided by bytecode
          stack[++sp] = globals[addr];
          break;

        case LOAD :
          break;

        case GSTORE :
          addr = code[ip++]; // where to store (provided by bytecode)
          globals[addr] = stack[sp--];
          break;

        case STORE :
          break;

        case PRINT :
          System.out.println(stack[sp--]);
          break;

        case HALT :
          break; // exit

        case RET :
          break;

        case CALL :
          break;

        case POP :
          --sp;
          break;

        default :
          System.out.println("Error: unrecognized opcode " + opcode);
          break;
      }
    }
  }

  protected void disassemble () {
    int opcode = code[ip];
    String opName = Bytecode.instructions[opcode].name;
    System.out.printf("%04d:\t%-11s", ip, opName);

    int nargs = Bytecode.instructions[opcode].nOperands;
    if ( nargs > 0 ) {
      // add operands to list
      List<String> operands = new ArrayList<String>();
      for (int i = ip + 1; i <= ip + nargs; i++) {
        operands.add(String.valueOf(code[i]));
      }
      // construct string
      for (int i = 0; i < operands.size(); i++) {
        String s = operands.get(i);
        if ( i > 0 ) System.out.print(", ");
        System.out.print(s);
      }
    }
    System.out.println();
  };
}