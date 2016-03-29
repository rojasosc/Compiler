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
getinput:
    ;
    mem[0] = (mem[1] + 3);
    mem[(mem[1] + 0 )] = -1;
    L1:
    ;
    if ( 0 > mem[(mem[1] + 0 )] ) goto L2;

    goto L3;

L2:
    ;
    read(mem[(mem[1] + 0 )]);
    if ( 0 > mem[(mem[1] + 0 )] ) goto L4;
    goto L5;
L4:
    ;
    printf("I need a non-negative number: ");
L5:
    ;
    goto L1;

L3:
    ;
    mem[(mem[1] - 1)] = mem[(mem[1] + 0 )];
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
main:
    ;
    mem[0] = (mem[1] + 39);
    printf("Welcome to the United States 1040 federal income tax program.\n");
    printf("(Note: this isn't the real 1040 form. If you try to submit your\n");
    printf("taxes this way, you'll get what you deserve!\n\n");
    printf("Answer the following questions to determine what you owe.\n\n");
    printf("Total wages, salary, and tips? ");
    mem[mem[0]] = (long) &&_R0;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getinput;
_R0:
    ;
    mem[(mem[1] + 25 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 0 )] = mem[(mem[1] + 25 )];
    printf("Taxable interest (such as from bank accounts)? ");
    mem[mem[0]] = (long) &&_R1;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getinput;
_R1:
    ;
    mem[(mem[1] + 25 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 1 )] = mem[(mem[1] + 25 )];
    printf("Unemployment compensation, qualified state tuition, and Alaska\n");
    printf("Permanent Fund dividends? ");
    mem[mem[0]] = (long) &&_R2;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getinput;
_R2:
    ;
    mem[(mem[1] + 25 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 2 )] = mem[(mem[1] + 25 )];
    mem[(mem[1] + 25 )] = mem[(mem[1] + 0 )] + mem[(mem[1] + 1 )];
    mem[(mem[1] + 26 )] = mem[(mem[1] + 25 )] + mem[(mem[1] + 2 )];
    mem[(mem[1] + 3 )] = mem[(mem[1] + 26 )];
    printf("Your adjusted gross income is: ");
    write(mem[(mem[1] + 3 )]);
    printf("Enter <1> if your parents or someone else can claim you on their");
    printf(" return. \nEnter <0> otherwise: ");
    mem[mem[0]] = (long) &&_R3;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getinput;
_R3:
    ;
    mem[(mem[1] + 27 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 14 )] = mem[(mem[1] + 27 )];
    if ( 0 != mem[(mem[1] + 14 )] ) goto L6;
    goto L7;
L6:
    ;
    mem[(mem[1] + 27 )] = mem[(mem[1] + 0 )] + 250;
    mem[(mem[1] + 16 )] = mem[(mem[1] + 27 )];
    mem[(mem[1] + 17 )] = 700;
    mem[(mem[1] + 18 )] = mem[(mem[1] + 17 )];
    if ( mem[(mem[1] + 18 )] < mem[(mem[1] + 16 )] ) goto L7;
    goto L8;
L7:
    ;
    mem[(mem[1] + 18 )] = mem[(mem[1] + 16 )];
L8:
    ;
    printf("Enter <1> if you are single, <0> if you are married: ");
    mem[mem[0]] = (long) &&_R4;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getinput;
_R4:
    ;
    mem[(mem[1] + 28 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 15 )] = mem[(mem[1] + 28 )];
    if ( 0 != mem[(mem[1] + 15 )] ) goto L9;
    goto L10;
L9:
    ;
    mem[(mem[1] + 19 )] = 7350;
L10:
    ;
    mem[(mem[1] + 19 )] = 7350;
    mem[(mem[1] + 20 )] = mem[(mem[1] + 18 )];
    if ( mem[(mem[1] + 20 )] > mem[(mem[1] + 19 )] ) goto L12;
    goto L13;
L12:
    ;
    mem[(mem[1] + 20 )] = mem[(mem[1] + 19 )];
L13:
    ;
    mem[(mem[1] + 21 )] = 0;
    if ( mem[(mem[1] + 15 )] == 0 ) goto L14;
    goto L15;
L14:
    ;
    printf("Enter <1> if your spouse can be claimed as a dependant, ");
    printf("enter <0> if not: ");
    mem[mem[0]] = (long) &&_R5;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getinput;
_R5:
    ;
    mem[(mem[1] + 28 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 24 )] = mem[(mem[1] + 28 )];
    if ( 0 == mem[(mem[1] + 24 )] ) goto L15;
    goto L16;
L15:
    ;
    mem[(mem[1] + 21 )] = 2800;
L16:
    ;
L17:
    ;
    mem[(mem[1] + 28 )] = mem[(mem[1] + 20 )] + mem[(mem[1] + 21 )];
    mem[(mem[1] + 22 )] = mem[(mem[1] + 28 )];
    mem[(mem[1] + 4 )] = mem[(mem[1] + 22 )];
L18:
    ;
    if ( 0 == mem[(mem[1] + 14 )] ) goto L19;
    goto L20;
L19:
    ;
    printf("Enter <1> if you are single, <0> if you are married: ");
    mem[mem[0]] = (long) &&_R6;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getinput;
_R6:
    ;
    mem[(mem[1] + 29 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 15 )] = mem[(mem[1] + 29 )];
    if ( 0 != mem[(mem[1] + 15 )] ) goto L20;
    goto L21;
L20:
    ;
    mem[(mem[1] + 4 )] = 12950;
L21:
    ;
    if ( 0 == mem[(mem[1] + 15 )] ) goto L22;
    goto L23;
L22:
    ;
    mem[(mem[1] + 4 )] = 7200;
L23:
    ;
L24:
    ;
    mem[(mem[1] + 29 )] = mem[(mem[1] + 3 )] - mem[(mem[1] + 4 )];
    mem[(mem[1] + 5 )] = mem[(mem[1] + 29 )];
    if ( mem[(mem[1] + 5 )] < 0 ) goto L25;
    goto L26;
L25:
    ;
    mem[(mem[1] + 5 )] = 0;
L26:
    ;
    printf("Your taxable income is: ");
    write(mem[(mem[1] + 5 )]);
    printf("Enter the amount of Federal income tax withheld: ");
    mem[mem[0]] = (long) &&_R7;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getinput;
_R7:
    ;
    mem[(mem[1] + 30 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 6 )] = mem[(mem[1] + 30 )];
    printf("Enter <1> if you get an earned income credit (EIC); ");
    printf("enter 0 otherwise: ");
    mem[mem[0]] = (long) &&_R8;
    mem[(mem[0] + 1)] = mem[1];
    mem[(mem[0] + 2)] = mem[0];
    mem[1] = (mem[0] + 4);
    goto getinput;
_R8:
    ;
    mem[(mem[1] + 30 )] = mem[(mem[0] + 4)];
mem[(mem[1] + 23 )] = mem[(mem[1] + 30 )];
    mem[(mem[1] + 7 )] = 0;
    if ( 0 != mem[(mem[1] + 23 )] ) goto L27;
    goto L28;
L27:
    ;
    printf("OK, I'll give you a thousand dollars for your credit.\n");
    mem[(mem[1] + 7 )] = 1000;
L28:
    ;
    mem[(mem[1] + 30 )] = mem[(mem[1] + 7 )] + mem[(mem[1] + 6 )];
    mem[(mem[1] + 8 )] = mem[(mem[1] + 30 )];
    printf("Your total tax payments amount to: ");
    write(mem[(mem[1] + 8 )]);
    mem[(mem[1] + 31 )] = mem[(mem[1] + 5 )] * 28;
    mem[(mem[1] + 32 )] = mem[(mem[1] + 31 )] + 50;
    mem[(mem[1] + 33 )] = mem[(mem[1] + 32 )] / 100;
    mem[(mem[1] + 9 )] = mem[(mem[1] + 33 )];
    printf("Your total tax liability is: ");
    write(mem[(mem[1] + 9 )]);
    mem[(mem[1] + 34 )] = mem[(mem[1] + 8 )] - mem[(mem[1] + 9 )];
    mem[(mem[1] + 10 )] = mem[(mem[1] + 34 )];
    if ( mem[(mem[1] + 10 )] < 0 ) goto L29;
    goto L30;
L29:
    ;
    mem[(mem[1] + 12 )] = 0;
L30:
    ;
    mem[(mem[1] + 12 )] = 0;
    if ( mem[(mem[1] + 10 )] > 0 ) goto L32;
    goto L33;
L32:
    ;
    printf("Congratulations, you get a tax refund of $");
    write(mem[(mem[1] + 10 )]);
L33:
    ;
    mem[(mem[1] + 35 )] = mem[(mem[1] + 9 )] - mem[(mem[1] + 8 )];
    mem[(mem[1] + 11 )] = mem[(mem[1] + 35 )];
    if ( mem[(mem[1] + 11 )] >= 0 ) goto L34;
    goto L35;
L34:
    ;
    printf("Bummer. You owe the IRS a check for $");
    write(mem[(mem[1] + 11 )]);
L35:
    ;
    if ( mem[(mem[1] + 11 )] < 0 ) goto L36;
    goto L37;
L36:
    ;
    mem[(mem[1] + 13 )] = 0;
L37:
    ;
    mem[(mem[1] + 13 )] = 0;
    write(mem[(mem[1] + 5 )]);
    write(mem[(mem[1] + 8 )]);
    write(mem[(mem[1] + 9 )]);
    write(mem[(mem[1] + 17 )]);
    write(mem[(mem[1] + 20 )]);
    write(mem[(mem[1] + 19 )]);
    write(mem[(mem[1] + 12 )]);
    write(mem[(mem[1] + 13 )]);
    mem[(mem[1] + 5 )] = mem[(mem[1] + 9 )];
    mem[(mem[1] + 7 )] = 0;
    mem[(mem[1] + 9 )] = 0;
    mem[(mem[1] + 36 )] = mem[(mem[1] + 17 )] + mem[(mem[1] + 13 )];
    mem[(mem[1] + 12 )] = mem[(mem[1] + 36 )];
    mem[(mem[1] + 37 )] = mem[(mem[1] + 20 )] + mem[(mem[1] + 19 )];
    mem[(mem[1] + 13 )] = mem[(mem[1] + 37 )];
    printf("Thank you for using ez-tax.\n");
    mem[(mem[1] - 1)] = 0;
    mem[0] = mem[(mem[1] - 2)];
    mem[1] = mem[(mem[1] - 3)];
    goto *(void *) mem[mem[0]];
_exit:
    ;
    return mem[(mem[0] + 3)];
}

