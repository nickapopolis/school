Nicholas Tierney
100772439
-----------------------------------
Question 1 
-----------------------------------

> (define a (make-interval 6 4))
> (define b (make-interval 3 2))
> a
(6 . 4)
> (upper-bound a)
6
> (lower-bound a)
4
> (add-interval a b)
(6 . 9)
> (subtract-interval a b)
(1 . 4)
> (multiply-interval a b)
(8 . 18)
> (divide-interval a b)
(1 1/3 . 3)
> (define c (make-interval 0 0))
> (divide-interval a c)
"Division by zero"

-----------------------------------
Question 2
-----------------------------------
; Create a new list of records
> (define a (record "yellow submarine" "the beatles" "Apple"))
> (define b (record "appetite for destruction" "Guns N' Roses" "Geffen"))
> (define rec (list a))
> (define rec (add-record rec b))

; Find record 
> (find-record rec "appetite for destruction")
("appetite for destruction" "Guns N' Roses" "Geffen")
> (find-record rec "yellow submarine")
("yellow submarine" "the beatles" "Apple")
> (find-record rec "sdasda")
"Cannot find record."

; Delete record
(define rec (delete-record rec "appetite for destruction"))
> rec
(("yellow submarine" "the beatles" "Apple"))

; List records
(define a (record "yellow submarine" "the beatles" "Apple"))
> (define b (record "appetite for destruction" "Guns N' Roses" "Geffen"))
> (define rec (list a))
> (define rec (add-record rec b))
> (find-record rec "appetite for destruction")
> (define c (record "other record" "the beatles" "Apple"))
> (define rec (add-record rec c))
> rec      
(("other record" "the beatles" "Apple") ("appetite for destruction" "Guns N' Roses" "Geffen") ("yellow submarine" "the beatles" "Apple")) ; list of all records  before search
> (list-records rec "the beatles")
(("yellow submarine" "the beatles" "Apple") ("other record" "the beatles" "Apple")) ; list of all records after search

-----------------------------------
Question 3 
-----------------------------------
Part 1

> (define b (triple 10 11 12))
> (first b)
10
> (second b)
11
> (third b)
12
> 

Part 2
(define a (new-triple 1 2 3))
> ((a 'first))
1
> ((a 'second))
2
> ((a 'third))
3
> ((a 'set-first) 4)
> ((a 'set-second) 5)
> ((a 'set-third) 6)
> ((a 'first))
4
> ((a 'second))
5
> ((a 'third))
6