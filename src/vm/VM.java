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
    this.globals = new int[datasize];
    this.stack = new int[DEFAULT_STACK_SIZE];
  }

  public void exec() {
    stack[ip] = 0; // simulate OS call
    cpu();
  }

  protected void cpu() {
    int opcode = code[ip];
    int addr, offset;
    int a, b;

    while (opcode != HALT && ip < code.length) {
      if (trace) System.out.println(disassemble() + "\t" + stackString());

      // Fetch, decode, execute, repeat.
      ip++; 
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
          stack[++sp] = a < b ? TRUE : FALSE;
          break;

        case IEQ :
          b = stack[sp--];
          a = stack[sp--];
          stack[++sp] = a == b ? TRUE : FALSE;
          break;

        case ICONST :
          stack[++sp] = code[ip++]; // push new val onto stack
          break;

        case BR :
          ip = code[ip++]; // jump to line indicated by bytecode 
          break;

        case BRT :
          addr = code[ip++];
          if (stack[sp--] == TRUE) ip = addr; // jump to line if false
          break;

        case BRF :
          addr = code[ip++];
          if (stack[sp--] == FALSE) ip = addr; // jump to line if true
          break;

        case GLOAD :
          addr = code[ip++]; // provided by bytecode
          stack[++sp] = globals[addr]; // push global
          break;

        case LOAD :
          offset = code[ip++];
          stack[++sp] = stack[fp+offset]; // fetch from local mem and push to stack
          break;

        case GSTORE :
          addr = code[ip++]; // where to store (provided by bytecode)
          globals[addr] = stack[sp--];
          break;

        case STORE :
          offset = code[ip++];
          stack[fp+offset] = stack[sp--]; // pop from stack and add to local mem
          break;

        case PRINT :
          System.out.println(stack[sp--]);
          break;

        case CALL :
          // TO-DO
          break;

        case RET :
          // TO-DO
          break;

        case POP :
          --sp;
          break;

        case HALT :
          return; // exit

        default :
          System.out.println("Error: unrecognized opcode " + opcode);
          break;
      }

      opcode = code[ip];
    }

    printMemory();

  }

  protected String disassemble () {
    int opcode = code[ip];
    String opName = Bytecode.instructions[opcode].name;
    StringBuilder buf = new StringBuilder();

    // append: memory location, instruction pointer, instruction name
    buf.append(String.format("%04d:\t%-11s", ip, opName));

    // append: ARGS (if applicable)
    int nargs = Bytecode.instructions[opcode].nOperands;
    if ( nargs > 0 ) {
      // add args to a list
      List<String> operands = new ArrayList<String>();
      for (int i = ip + 1; i <= ip + nargs; i++) {
        operands.add(String.valueOf(code[i]));
      }
      // construct string from list
      for (int i = 0; i < operands.size(); i++) {
        String s = operands.get(i);
        if ( i > 0 ) buf.append(", "); // comma-separated
        buf.append(s); // 
      }
    }

    return buf.toString();
  };

  protected String stackString() {
    StringBuilder buf = new StringBuilder();
    buf.append("Stack: ");
    for (int i = 0; i <= sp; i++) {
      buf.append(" ");
      buf.append(stack[i]);
    }
    return buf.toString();
  }

  protected void printMemory() {
    System.out.println("Data memory: ");
    int addr = 0;
    for (int k : globals) {
      System.out.printf("%04d: %s\n", addr, k);
      addr++;
    }
    System.out.println();
  }
}