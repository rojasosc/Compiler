#include <stdio.h>
//This test should be okay, since while statements are defined.
long mem[2000];

int main(void) {
    mem[0] = 2;
    mem[1] = 6;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[(mem[0] + 3)] = 0;
    mem[mem[0]] = (long) &&_exit;
    goto main;
main:
    ;
    mem[0] = (mem[1] + 3);
    mem[(mem[1] + 0 )] = 0;
    L1:
    ;
    if ( mem[(mem[1] + 0 )] < 10 ) goto L2;

    goto L3;

L2:
    ;
    printf("hello\n");
    mem[(mem[1] + 1 )] = mem[(mem[1] + 0 )] + 1;
    mem[(mem[1] + 0 )] = mem[(mem[1] + 1 )];
    goto L1;

L3:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
_exit:
    ;
    return mem[(mem[0] + 3)];
}

