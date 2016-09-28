Nicholas Tierney
100772439

Question 1
a)
> algebraOne
523 1/4

b)
> algebraTwo
1/7

Question 3
The way this takes input is through a coordinate system. The rot of the triangle is 
x=1 y=1. To move down the triangle increase y, to move right in the triangle increase
x. For an example row 5 which is 1 4 6 4 1 entry 3 would be 6. 
> (pascal 3 5)
6

Question 4
To calculate b to the power of n type in 
(power b n).
Example 3^5:
(power 3 5)
243

Question 5
To multiply two numbers x * y together enter (multiply x y)
Example 2*3:
> (multiply 2 3)
6

Question 6
To multiply two numbers x * y together using the logarithmic procedure 
enter (multiply-it x y)
Example 2*3:
> (multiply-it 2 3)
6

Question 7
This procedure uses applicative order execution, due to the fact that it got
stuck in a loop trying to calculate p. In applicative order execution, 
all procedure arguments are evaluated before applying the procedure. This means that
before applying the procedure (test x y) it had to calculate the value of both 
arguments, one of which was an infinite loop. Had this used normal order execution, 
test would have been computed first and the exit condition would be met when it 
looked at the first argument 0.

Question 8
To calculate the cube root of a number x  enter (cube-root x)
Example 429:
> (cube-root 429)
7.541986732613226

Question 9
For this procedure there are multiple possible outcomes,
The possible outcomes are:

b<a and b=0
(cond ((#t)+) ((#t)+) ((#f)+) ((#f)+))
result:a+b

b<a and a=0
(cond ((#t)+) ((#f)+) ((#t)+) ((#f)+))
result:a+b

b<a
(cond ((#t)+) ((#f)+) ((#f)+) ((#f)+))
result:a+b

a<b and a=0
(cond ((#f)+) ((#f)+) ((#t)+) ((#t)+))
result:a/b

a<b and b=0
(cond ((#f)+) ((#t)+) ((#f)+) ((#t)+))
result:a/b

a<b
(cond ((#f)+) ((#f)+) ((#f)+) ((#t)+))
result:a*b

b=a=0
(cond ((#f)+) ((#t)+) ((#t)+) ((#t)+))
result:a-b

Question 10
a)
To calculate the roots of a quadratic equation ax^2 + bx +c we must use the quadratic
equation which returns 2 roots. The input values of this function (quadratic a b c n)
a, b, c represent the values of a b and c and if n<0 it returns the first root and if 
n>=0 it returns the second root
> (quadratic 2 -4 -3 -1)
-0.5811388300841898
> (quadratic 2 -4 -3 1)
2.58113883008419
> (quadratic 1 6 10 -1)
"Imaginary root"
> (quadratic 1 6 10 1)
"Imaginary root"
> 

b)
To calculate the reciprocal of a number x we use the equation 1/x. Enter (reciprocal x)
to calculate the value of the reciprocal of this number.
> (reciprocal 0)
"Error: x=0"
> (reciprocal 1.5)
0.6666666666666666
> 




