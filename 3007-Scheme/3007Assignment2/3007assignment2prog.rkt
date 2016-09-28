;--------------------------------------------------------------------------------------------------
;Question 1
;--------------------------------------------------------------------------------------------------

;initializes the interval with values x and y
(define (make-interval x y)
  (define a (cons x y))
    a)


;returns the lower bound of interval
(define (lower-bound x)
  (if (<= (car x) (cdr x))
      (car x)
      (cdr x)
      )
  )

;returns the upper bound of interval
(define (upper-bound x)
  (if (> (car x) (cdr x))
      (car x)
      (cdr x)
      )
  )
;finds the minimum value of input of cons
(define (minimum x y)
  (min (* (lower-bound x) (lower-bound y))
       (* (lower-bound x) (upper-bound y))       
       (* (upper-bound x) (lower-bound y))      
       (* (upper-bound x) (upper-bound y))
       )
  )

;finds the maximum value of input of cons
(define (maximum x y)
  (max (* (lower-bound x) (lower-bound y))
       (* (lower-bound x) (upper-bound y))       
       (* (upper-bound x) (lower-bound y))      
       (* (upper-bound x) (upper-bound y))
       )
  )

;checks for 0 in interval
(define (contains-zero? x)
  (if (or (= (lower-bound x) 0) (= (upper-bound x) 0))
      #t
      #f
  )
  )
;adds two intervals
(define (add-interval x y) ;[a,b] + [c,d]
  (define a (cons 
             (+ (lower-bound x) (lower-bound y)) ;[a+c]
             (+ (upper-bound x) (upper-bound y)) ;[b+d]
             )
    )
  a
   )

;subtracts two intervals
(define (subtract-interval x y)
  (define a (cons 
             (- (lower-bound x) (upper-bound y)) ;[a-d]
             (- (upper-bound x) (lower-bound y)) ;[b-c]
             )
    )
  a
  )

;multiplies two intervals
(define (multiply-interval x y)
  (define a (cons 
             (minimum x y) ; [min(ac,ad,bc,bd)
             (maximum x y) ; max(ac,ad,bc,bd)]
             )
    )
  a
  )

;divides two intervals
(define (divide-interval x y)
  (if (contains-zero? y)
      "Division by zero"
       (multiply-interval x (make-interval (/ 1 (upper-bound y)) (/ 1 (lower-bound y))))     
       )
  )
;--------------------------------------------------------------------------------------------------
;Question 2
;--------------------------------------------------------------------------------------------------
; constructor for record 
(define (record title artist label)
  (list title artist label) 
  )
; returns title
(define (get-title x)
  (car x)
  )
; returns artist
(define (get-artist x)
  (cadr x)
  )
; returns label
(define (get-label x)
  (caddr x)
  )

; add record function
(define (add-record record-list record-entry)
   (cons record-entry record-list)
    )
      
; find record function
(define (find-record record-list name)
  (if (and (null? (cdr record-list)) (not (equal? (get-title (car record-list)) name)) )
      "Cannot find record."
      (if (equal? (get-title (car record-list)) name) 
          (car record-list)
          (find-record (cdr record-list) name) ))
  )

; delete record function
(define (delete-record record-list name) 
  (define (parse list-a list-b)
    
  (if (and (null? (cdr list-b)) (not (equal? (get-title (car list-b)) name)) )
      list-a
      (if (equal? (get-title (car list-b)) name) 
          (append list-a (cdr list-b))
          (parse (add-record list-a (car list-b)) (cdr list-b)) ))
  )
  (define a (list ))
  (parse a record-list)
  )

;list records function
(define (list-records record-list name)
  (define (parse list-a list-b)
    
    
  (if (and (null? (cdr list-b)) (not (equal? (get-artist (car list-b)) name)) )
      list-a
      (if (and (null? (cdr list-b)) (equal? (get-artist (car list-b)) name)) 
          (add-record list-a (car list-b))
          (if (equal? (get-artist (car list-b)) name)
              (parse (add-record list-a (car list-b)) (cdr list-b))
          (parse list-a (cdr list-b)) ))
  )
  
  )
  ; empty list a
  (define a (list ))
  (parse a record-list)
  )
;--------------------------------------------------------------------------------------------------
;Question 3
;--------------------------------------------------------------------------------------------------
;Part a
(define (triple x y z)
  (lambda (m) (m x y z)))
(define (first z) (z (lambda (p q r) p))) 
(define (second z) (z (lambda (p q r) q)))
(define (third z) (z (lambda (p q r) r)))
;Part b
(define (new-triple x y z) 
	(let ((first-num x) (second-num y) (third-num z))

	      
; set functions
         (define (set-first value)
		(set! first-num value))
          
          (define (set-second value)
		(set! second-num value))
          
          (define (set-third value)
		(set! third-num value))
; get functions
         (define (first)
		first-num) 
          (define (second)
		second-num)
          (define (third)
		third-num)
; dispatch
         (define (dispatch method) 
             (cond     	
				((eq? method 'set-first) set-first) 
				((eq? method 'set-second) set-second) 
				((eq? method 'set-third) set-third)
                                ((eq? method 'first) first) 
				((eq? method 'second) second) 
				((eq? method 'third) third) 
				(else (error "Unknown Request" method)))) 
         dispatch))
