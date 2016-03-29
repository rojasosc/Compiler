#include <stdio.h>
#define read(x) scanf("%d",&x)
#define write(x) printf("%d\n",x)
long mem[2000];

int main(void) {
    mem[0] = 2;
    mem[1] = 6;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[(mem[0] + 3)] = 0;
    mem[mem[0]] = (long) &&_exit;
    goto main;
recursedigit:
    ;
    mem[0] = (mem[1] + 5);
    if ( 0 == mem[(mem[1] - 4)] ) goto L1;
    goto L2;
L1:
    ;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L2:
    ;
    mem[(mem[1] + 0 )] = 0;
    mem[(mem[1] + 1 )] = mem[(mem[1] - 4)] / 2;
    mem[(mem[1] + 2 )] = mem[(mem[1] + 1 )] * 2;
    mem[(mem[1] + 3 )] = mem[(mem[1] - 4)] - mem[(mem[1] + 2 )];
    if ( 0 != mem[(mem[1] + 3 )] ) goto L3;
    goto L4;
L3:
    ;
    mem[(mem[1] + 0 )] = 1;
L4:
    ;
    mem[(mem[1] + 4 )] = mem[(mem[1] - 4)] / 2;
    mem[mem[0]] = (long) &&_R0;
    mem[(mem[0] + 1)] = mem[(mem[1] + 4)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto recursedigit;
_R0:
    ;
    if ( 0 == mem[(mem[1] + 0 )] ) goto L5;
    goto L6;
L5:
    ;
    printf("0");
L6:
    ;
    if ( 1 == mem[(mem[1] + 0 )] ) goto L7;
    goto L8;
L7:
    ;
    printf("1");
L8:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
main:
    ;
    mem[0] = (mem[1] + 3);
    mem[(mem[1] + 0 )] = 0;
    L9:
    ;
    if ( 0 >= mem[(mem[1] + 0 )] ) goto L10;

    goto L11;

L10:
    ;
    printf("Give me a number: ");
    read(mem[(mem[1] + 0 )]);
    if ( 0 >= mem[(mem[1] + 0 )] ) goto L12;
    goto L13;
L12:
    ;
    printf("I need a positive integer.\n");
L13:
    ;
    goto L9;

L11:
    ;
    printf("The binary representation of: ");
    write(mem[(mem[1] + 0 )]);
    printf("is: ");
    mem[mem[0]] = (long) &&_R1;
    mem[(mem[0] + 1)] = mem[(mem[1] + 0)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto recursedigit;
_R1:
    ;
    printf("\n\n");
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
_exit:
    ;
    return mem[(mem[0] + 3)];
}

