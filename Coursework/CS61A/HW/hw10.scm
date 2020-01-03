(define (accumulate combiner start n term)
  (cond ((= n 0) start)
  	((= start n) (term n))
  	(else (combiner (term start) 
  		(accumulate combiner (+ 1 start) n term))
  	)
  )
)

(define (accumulate-tail combiner start n term)
  (define (accumulate-helper combiner start n term total)
  	(cond ((= n 0) start)
  		((= start n) (combiner total (term start)))
  		(else (accumulate-helper combiner (+ 1 start) n term
  				(combiner total (term start))))
  	)
  )
  (if (equal? combiner *) (accumulate-helper combiner start n term 1)
  		(accumulate-helper combiner start n term 0))
)

(define-macro (list-of expr for var in seq if filter-fn)
  (list 'map(list 'lambda (list var) expr) (list 'filter(
  	list 'lambda (list var) filter-fn) seq))
)



(define (deep-apply s f)
  (cond 
    ((null? s) nil)
    ((null? (cdr s)) (cons (deep-apply (car s) f)))
    ((list? (car s)) (cons (deep-apply (car(car s)) f) (cons (deep-apply (cdr(car s)) f))))
    (else (cons (f (car s)) (deep-apply (cdr s) f)))
  )
)


