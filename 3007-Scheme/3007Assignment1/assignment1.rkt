;Name: Nicholas Tierney
;Student Number: 100772439
;Question 1
(define algebraOne (+ (/ 9 4) (* 2 4) (* (+ (* 3 5) (* 6 7)) 9)))
(define algebraTwo (* 2 (+ -1 (*  (+ 3 (* 4 2) -6) (/ 3 (* 7 2))))))
;Question 2
(define (f n)
  (if (< n 4)
      n
      (+  (f (- n 1))  (* 2 (f (- n 2)))  (* 3 (f (- n 3)))  (* 4 (f (- n 4))))))
;Question 3
(define (pascal x y)
  
  
  (define (calcSum s t)
    (if (and (= t 1) (= s 1)) ;root check
        1  
        (if (or (> s t)(< s 1)) ;bound check
            0
            (+ (calcSum (- s 1) (- t 1)) (calcSum s (- t 1))) ;calculate value by recursion
            )
        )
    )
  (calcSum x y)
  )
;Question 4
(define (power b n) 
  
  (define (iterate-power num exp prod)
    (if (= exp 1);base case
        (* num prod)
        (if (odd? exp)
            (iterate-power num (- exp 1) (* prod num));if power is odd will multiply num by product
            (iterate-power (* num num) (/ exp 2) prod);if power is even will square num
            )
        )
    )
  (if (= n 0)
      1
      (iterate-power b n 1))
  )
;Question 5
(define (multiply x y)
  (define (multiply-iterate a b total)
    (if (or (= a 0) (= b 0));base case
        total
        (multiply-iterate a (- b 1) (+ total a)) 
        )
    )
  (if  (or (and (< y 0) (< x 0)) (and (>= y 0) (>= x 0)));check for sign on product
       (multiply-iterate (abs x) (abs y) 0);positive product
       (* -1 (multiply-iterate (abs x) (abs y) 0));negative product
       )
  )

;Question 6
(define (multiply-it x y)
  (define (iterate b n prod)
    (if (or (= b 1) (= n 1))
        (multiply (if (= b 1) n b) (power 2 prod)) 
        (if (odd? n)
            (iterate (/ (multiply b n) (- n 1)) (- n 1) prod);if odd will not add to product
            (iterate b (/ n 2) (+ prod 1) );if even will add to product for future multiplication
            )
        )
    )
  (if (or (= y 0) (= x 0))
      0
      (if  (or (and (< y 0) (< x 0)) (and (>= y 0) (>= x 0)));check for sign of product
           (iterate (abs x) (abs y) 0); positive product
           (* -1 (iterate (abs x) (abs y) 0)); negative product
           )
      )
  )

;Question 7
(define (p)  (p))

(define (test x y)
  (if    (= x  0)
         0
         y))

;Question 8
(define (cube-root x)
  
  (define (good-enough? guess); if off by 0.001 will stop trying to improve cube root
    (< (abs (- (cube guess) x)) 0.001))
  (define (cube num); num cubed
    (* num num num)
    )
  (define (square num);num squared
    (* num num)
    )
  (define (average first second);average of numbers (3 in this case for cube)
    (/ (+ first second) 3)
    )
  (define (improve guess); (2y * x/y^2)
    (average (* 2 guess) ( / x (square guess))))
  
  (define (sqrt-iteration guess)
    (if (good-enough? guess)
        guess
        (sqrt-iteration (improve guess))))
  
  (sqrt-iteration 1.0)) ;start value for guessing

;Question 9
(define (a-or-b-depending-on-value a b)
  ((cond ((< b a) +) ((= b 0) -) ((= a 0) /) (else *)) a b)) 

;Question 10
(define (quadratic a b c n)
  (define (undefined? num)
    (if (= num 0)
        "Not quadratic"
        (left-or-right n)
        )
    )
    (define (left-or-right num)
      (if (< (- (* b b) (* 4 a c)) 0)
          "Imaginary root"
      (if (< n 0) 
          (/ (- (* -1 b) (sqrt (- (* b b) (* 4 a c)))) (* 2 a)) ;first root
          (/ (+ (* -1 b) (sqrt (- (* b b) (* 4 a c)))) (* 2 a)) ;second root
      )
      )
      )
    
  (undefined? a)
  )

(define (reciprocal x)
  (if (= x 0)
      "Error: x=0"
      (/ 1 x)
      )
  )

