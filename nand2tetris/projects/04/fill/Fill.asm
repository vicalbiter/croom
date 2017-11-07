// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed.
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
  @8192
  D=A
  @n
  M=D       // n=8192

(CHECK)
  @i
  M=0       // i=0
  @SCREEN
  D=A
  @addr
  M=D       // addr=16384

  @KBD
  D=M       // Listen to keyboard register
  @CLEAR
  D;JEQ     // If there are no keys pressed, GOTO CLEAR
  @FILL
  0;JMP     // If there is something pressed, GOTO FILL

(FILL)
  @i
  D=M     // D=i
  @n
  D=D-M   // D=i-n
  @CHECK
  D;JEQ

  @addr
  A=M
  M=-1    // Set RAM[addr] to -1 (fill screen)

  @i
  M=M+1   // i+=1
  @addr
  M=M+1   // addr+=1

  @FILL
  0;JMP

(CLEAR)     // Clear the screen
  @i
  D=M     // D=i
  @n
  D=D-M   // D=i-n
  @CHECK
  D;JEQ

  @addr
  A=M
  M=0     // Set RAM[addr] to 0 (clear screen)

  @i
  M=M+1   // i+=1
  @addr
  M=M+1   // addr+=1

  @CLEAR
  0;JMP
