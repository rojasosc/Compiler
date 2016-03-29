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
getnextdigit:
    ;
    mem[0] = (mem[1] + 3);
    L1:
    ;
    if ( 0 == 0 ) goto L2;

    goto L3;

L2:
    ;
    printf("Give me a number (-1 to quit): ");
    read(mem[(mem[1] + 0 )]);
    if ( -1 <= mem[(mem[1] + 0 )] ) goto L4;
    goto L5;
L4:
    ;
    break ;
L5:
    ;
    printf("I need a number that's either 0 or 1.\n");
    goto L1;

L3:
    ;
    mem[(mem[1] - 1)] = mem[(mem[1] + 0 )];
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
state_0:
    ;
    mem[0] = (mem[1] + 4);
    mem[mem[0]] = (long) &&_R0;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getnextdigit;
_R0:
    ;
    mem[(mem[1] + 1 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 0 )] = mem[(mem[1] + 1 )];
    if ( -1 == mem[(mem[1] + 0 )] ) goto L6;
    goto L7;
L6:
    ;
    printf("You gave me an even number of 0's.\n");
    printf("You gave me an even number of 1's.\n");
    printf("I therefore accept this input.\n");
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L7:
    ;
    if ( 0 == mem[(mem[1] + 0 )] ) goto L8;
    goto L9;
L8:
    ;
    mem[mem[0]] = (long) &&_R1;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto state_2;
_R1:
    ;
L9:
    ;
    if ( 1 == mem[(mem[1] + 0 )] ) goto L10;
    goto L11;
L10:
    ;
    mem[mem[0]] = (long) &&_R2;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto state_1;
_R2:
    ;
L11:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
state_1:
    ;
    mem[0] = (mem[1] + 4);
    mem[mem[0]] = (long) &&_R3;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getnextdigit;
_R3:
    ;
    mem[(mem[1] + 1 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 0 )] = mem[(mem[1] + 1 )];
    if ( -1 == mem[(mem[1] + 0 )] ) goto L12;
    goto L13;
L12:
    ;
    printf("You gave me an even number of 0's.\n");
    printf("You gave me an odd number of 1's.\n");
    printf("I therefore reject this input.\n");
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L13:
    ;
    if ( 0 == mem[(mem[1] + 0 )] ) goto L14;
    goto L15;
L14:
    ;
    mem[mem[0]] = (long) &&_R4;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto state_3;
_R4:
    ;
L15:
    ;
    if ( 1 == mem[(mem[1] + 0 )] ) goto L16;
    goto L17;
L16:
    ;
    mem[mem[0]] = (long) &&_R5;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto state_0;
_R5:
    ;
L17:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
state_2:
    ;
    mem[0] = (mem[1] + 4);
    mem[mem[0]] = (long) &&_R6;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getnextdigit;
_R6:
    ;
    mem[(mem[1] + 1 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 0 )] = mem[(mem[1] + 1 )];
    if ( -1 == mem[(mem[1] + 0 )] ) goto L18;
    goto L19;
L18:
    ;
    printf("You gave me an odd number of 0's.\n");
    printf("You gave me an even number of 1's.\n");
    printf("I therefore reject this input.\n");
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L19:
    ;
    if ( 0 == mem[(mem[1] + 0 )] ) goto L20;
    goto L21;
L20:
    ;
    mem[mem[0]] = (long) &&_R7;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto state_0;
_R7:
    ;
L21:
    ;
    if ( 1 == mem[(mem[1] + 0 )] ) goto L22;
    goto L23;
L22:
    ;
    mem[mem[0]] = (long) &&_R8;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto state_3;
_R8:
    ;
L23:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
state_3:
    ;
    mem[0] = (mem[1] + 4);
    mem[mem[0]] = (long) &&_R9;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getnextdigit;
_R9:
    ;
    mem[(mem[1] + 1 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 0 )] = mem[(mem[1] + 1 )];
    if ( -1 == mem[(mem[1] + 0 )] ) goto L24;
    goto L25;
L24:
    ;
    printf("You gave me an odd number of 0's.\n");
    printf("You gave me an odd number of 1's.\n");
    printf("I therefore reject this input.\n");
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L25:
    ;
    if ( 0 == mem[(mem[1] + 0 )] ) goto L26;
    goto L27;
L26:
    ;
    mem[mem[0]] = (long) &&_R10;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto state_1;
_R10:
    ;
L27:
    ;
    if ( 1 == mem[(mem[1] + 0 )] ) goto L28;
    goto L29;
L28:
    ;
    mem[mem[0]] = (long) &&_R11;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto state_2;
_R11:
    ;
L29:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
main:
    ;
    mem[0] = (mem[1] + 2);
    mem[mem[0]] = (long) &&_R12;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto state_0;
_R12:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
_exit:
    ;
    return mem[(mem[0] + 3)];
}

