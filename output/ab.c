#include <stdio.h>
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
    mem[0] = (mem[1] + 5);
    mem[(mem[1] + 1 )] = 0;
    mem[(mem[1] + 0 )] = 1;
    mem[(mem[1] + 3 )] = mem[(mem[1] + 0 )] + mem[(mem[1] + 1 )];
    mem[(mem[1] + 2 )] = mem[(mem[1] + 3 )];
    printf("19 s=%2d\n", mem[(mem[1] + 2 )]);
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
_exit:
    ;
    return mem[(mem[0] + 3)];
}

