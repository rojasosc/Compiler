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
square:
    ;
    mem[0] = (mem[1] + 3);
    mem[(mem[1] + 0 )] = mem[(mem[1] - 2)] * mem[(mem[1] - 2)];
    mem[(mem[1] + 1 )] = mem[(mem[1] + 0 )] + 500;
    mem[(mem[1] + 2 )] = mem[(mem[1] + 1 )] / 1000;
    mem[(mem[1] - 1)] = mem[(mem[1] + 2 )];
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
complex_abs_squared:
    ;
    mem[0] = (mem[1] + 4);
    mem[mem[0]] = (long) &&_R0;
    mem[(mem[0] + 1)] = mem[(mem[1] + 3)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto square;
_R0:
    ;
    mem[(mem[1] + 0 )] = mem[(mem[0] + 4)];
mem[mem[0]] = (long) &&_R1;
    mem[(mem[0] + 1)] = mem[(mem[1] + 3)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto square;
_R1:
    ;
    mem[(mem[1] + 0 )] = mem[(mem[0] + 4)];
    mem[(mem[1] + 0 )] = mem[(mem[1] + 0 )] + mem[(mem[1] + 0 )];
    mem[(mem[1] - 1)] = mem[(mem[1] + 0 )];
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
check_for_bail:
    ;
    mem[0] = (mem[1] + 3);
    if ( mem[(mem[1] - 2)] > 4000 ) goto L1;
    goto L2;
L1:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L2:
    ;
    mem[mem[0]] = (long) &&_R2;
    mem[(mem[0] + 1)] = mem[(mem[1] + 1)];
    mem[(mem[0] + 2)] = mem[(mem[1] + 0)];
    mem[(mem[0] + 3)] = mem[1];
    mem[(mem[0] + 4)] = mem[0];
    mem[1] = (mem[0] + 6);
    goto complex_abs_squared;
_R2:
    ;
    mem[(mem[1] + 0 )] = mem[(mem[0] + 4)];
if ( 1600 > mem[(mem[1] + 0 )] ) goto L3;
    goto L4;
L3:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L4:
    ;
    mem[(mem[1] - 1)] = 1;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
absval:
    ;
    mem[0] = (mem[1] + 3);
    if ( mem[(mem[1] - 2)] < 0 ) goto L5;
    goto L6;
L5:
    ;
    mem[(mem[1] + 0 )] = -1 * mem[(mem[1] - 2)];
    mem[(mem[1] - 1)] = mem[(mem[1] + 0 )];
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L6:
    ;
    mem[(mem[1] - 1)] = mem[(mem[1] - 2)];
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
checkpixel:
    ;
    mem[0] = (mem[1] + 13);
    mem[(mem[1] + 0 )] = 0;
    mem[(mem[1] + 1 )] = 0;
    mem[(mem[1] + 3 )] = 0;
    mem[(mem[1] + 4 )] = 16000;
    L7:
    ;
    if ( mem[(mem[1] + 3 )] < 255 ) goto L8;

    goto L9;

L8:
    ;
    mem[mem[0]] = (long) &&_R3;
    mem[(mem[0] + 1)] = mem[(mem[1] + 16)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto square;
_R3:
    ;
    mem[(mem[1] + 5 )] = mem[(mem[0] + 4)];
mem[mem[0]] = (long) &&_R4;
    mem[(mem[0] + 1)] = mem[(mem[1] + 16)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto square;
_R4:
    ;
    mem[(mem[1] + 5 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 5 )] = mem[(mem[1] + 5 )] - mem[(mem[1] + 5 )];
    mem[(mem[1] + 6 )] = mem[(mem[1] + 5 )] + mem[(mem[1] - 12)];
    mem[(mem[1] + 2 )] = mem[(mem[1] + 6 )];
    mem[(mem[1] + 7 )] = 2 * mem[(mem[1] + 0 )];
    mem[(mem[1] + 8 )] = mem[(mem[1] + 7 )] * mem[(mem[1] + 1 )];
    mem[(mem[1] + 9 )] = mem[(mem[1] + 8 )] + 500;
    mem[(mem[1] + 10 )] = mem[(mem[1] + 9 )] / 1000;
    mem[(mem[1] + 11 )] = mem[(mem[1] + 10 )] + mem[(mem[1] - 12)];
    mem[(mem[1] + 1 )] = mem[(mem[1] + 11 )];
    mem[(mem[1] + 0 )] = mem[(mem[1] + 2 )];
    mem[mem[0]] = (long) &&_R5;
    mem[(mem[0] + 1)] = mem[(mem[1] + 16)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto absval;
_R5:
    ;
    mem[(mem[1] + 12 )] = mem[(mem[0] + 4)];
mem[mem[0]] = (long) &&_R6;
    mem[(mem[0] + 1)] = mem[(mem[1] + 16)];
    mem[(mem[0] + 2)] = mem[1];
    mem[(mem[0] + 3)] = mem[0];
    mem[1] = (mem[0] + 5);
    goto absval;
_R6:
    ;
    mem[(mem[1] + 12 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 12 )] = mem[(mem[1] + 12 )] + mem[(mem[1] + 12 )];
    if ( mem[(mem[1] + 12 )] > 5000 ) goto L10;
    goto L11;
L10:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
L11:
    ;
    mem[(mem[1] + 13 )] = mem[(mem[1] + 3 )] + 1;
    mem[(mem[1] + 3 )] = mem[(mem[1] + 13 )];
    goto L7;

L9:
    ;
    mem[(mem[1] - 1)] = 1;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
main:
    ;
    mem[0] = (mem[1] + 6);
    mem[(mem[1] + 1 )] = 950;
    L12:
    ;
    if ( mem[(mem[1] + 1 )] > -950 ) goto L13;

    goto L14;

L13:
    ;
    mem[(mem[1] + 0 )] = -2100;
    L15:
    ;
    if ( mem[(mem[1] + 0 )] < 1000 ) goto L16;

    goto L17;

L16:
    ;
    mem[mem[0]] = (long) &&_R7;
    mem[(mem[0] + 1)] = mem[(mem[1] + 4)];
    mem[(mem[0] + 2)] = mem[(mem[1] + 3)];
    mem[(mem[0] + 3)] = mem[1];
    mem[(mem[0] + 4)] = mem[0];
    mem[1] = (mem[0] + 6);
    goto checkpixel;
_R7:
    ;
    mem[(mem[1] + 3 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 2 )] = mem[(mem[1] + 3 )];
    if ( 1 == mem[(mem[1] + 2 )] ) goto L18;
    goto L19;
L18:
    ;
    printf("X");
L19:
    ;
    if ( 0 == mem[(mem[1] + 2 )] ) goto L20;
    goto L21;
L20:
    ;
    printf(" ");
L21:
    ;
    mem[(mem[1] + 3 )] = mem[(mem[1] + 0 )] + 40;
    mem[(mem[1] + 0 )] = mem[(mem[1] + 3 )];
    goto L15;

L17:
    ;
    printf("\n");
    mem[(mem[1] + 4 )] = mem[(mem[1] + 1 )] - 50;
    mem[(mem[1] + 1 )] = mem[(mem[1] + 4 )];
    goto L12;

L14:
    ;
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
_exit:
    ;
    return mem[(mem[0] + 3)];
}

