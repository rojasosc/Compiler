#include <stdio.h>
#define read(x) scanf("%d",&x)
#define write(x) printf("%d\n",x)
long mem[2000];

int main(void) {
    mem[0] = 34;
    mem[1] = 38;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[(mem[0] + 3)] = 0;
    mem[mem[0]] = (long) &&_exit;
    goto main;
initialize_array:
    ;
    mem[0] = (mem[1] + 4);
    mem[(mem[1] + 1 )] = 32;
    mem[(mem[1] + 0 )] = 0;
    L1:
    ;
    if ( mem[(mem[1] + 0 )] < mem[(mem[1] + 1 )] ) goto L2;

    goto L3;

L2:
    ;
    mem[2 + (mem[(mem[1] + 0 )])] = -1;
    mem[(mem[1] + 2 )] = mem[(mem[1] + 0 )] + 1;
    mem[(mem[1] + 0 )] = mem[(mem[1] + 2 )];
    goto L1;

L3:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
fib:
    ;
    mem[0] = (mem[1] + 4);
    if ( mem[(mem[1] - 3)] < 2 ) goto L4;
    goto L5;
L4:
    ;
    mem[(mem[1] - 1)] = 1;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L5:
    ;
    if ( mem[2 + (mem[(mem[1] - 3)])] == -1 ) goto L6;
    goto L7;
L6:
    ;
    mem[(mem[1] + 0 )] = mem[(mem[1] - 3)] - 1;
    mem[mem[0]] = (long) &&_R0;
    mem[(mem[0] + 1)] = mem[(mem[1] + 3)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto fib;
_R0:
    ;
    mem[(mem[1] + 1 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 1 )] = mem[(mem[1] - 3)] - 2;
    mem[mem[0]] = (long) &&_R1;
    mem[(mem[0] + 1)] = mem[(mem[1] + 3)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto fib;
_R1:
    ;
    mem[(mem[1] + 2 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 2 )] = mem[(mem[1] + 1 )] + mem[(mem[1] + 2 )];
    mem[2 + (mem[(mem[1] - 3)])] = mem[(mem[1] + 2 )];
L7:
    ;
    mem[(mem[1] - 1)] = mem[2 + (mem[(mem[1] - 3)])];
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
main:
    ;
    mem[0] = (mem[1] + 5);
    mem[(mem[1] + 1 )] = 32;
    mem[mem[0]] = (long) &&_R2;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto initialize_array;
_R2:
    ;
    mem[(mem[1] + 0 )] = 0;
    printf("The first few digits of the Fibonacci sequence are:\n");
    L8:
    ;
    if ( mem[(mem[1] + 0 )] < mem[(mem[1] + 1 )] ) goto L9;

    goto L10;

L9:
    ;
    mem[mem[0]] = (long) &&_R3;
    mem[(mem[0] + 1)] = mem[(mem[1] + 3)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto fib;
_R3:
    ;
    mem[(mem[1] + 2 )] = mem[(mem[0] + 4)];
write(mem[(mem[1] + 2 )]);
    mem[(mem[1] + 2 )] = mem[(mem[1] + 0 )] + 1;
    mem[(mem[1] + 0 )] = mem[(mem[1] + 2 )];
    goto L8;

L10:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
_exit:
    ;
    return mem[(mem[0] + 3)];
}

