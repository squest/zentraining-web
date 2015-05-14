(ns webtrain.play
  (:require [defun :refer [defun fun letfun]]))

(defn sum-sieve
  [^long lim]
  (let [refs (boolean-array (+ lim 1))
        llim (int (Math/sqrt lim))]
    (loop [i (int 3) res (int 2)]
      (if (> i lim)
        res
        (if (not (aget refs i))
          (if (<= i llim)
            (do (loop [j (int (* i i))]
                  (if (> j lim)
                    nil
                    (do (aset refs j true)
                        (recur (+ j i i)))))
                (recur (+ i 2) (+ i res)))
            (recur (+ i 2) (+ i res)))
          (recur (+ i 2) res))))))

(defun
  fibo-lim
  ([lim] (recur 1 0 1 lim))
  ([a b i lim] (if (> a lim) i (recur (+' a b) a (+ i 1) lim))))

(defun
  expt
  ([a 0] 1)
  ([a 1] a)
  ([a m] (let [parts (expt a (quot m 2))]
           (if (even? m) (*' parts parts) (*' a parts parts)))))

(defn fibo-1
  ([lim] (fibo-1 1 0 1 lim))
  ([a b i lim] (if (> a lim) i (recur (+' a b) a (+' 1 i) lim))))

(defn fibo-2
  [a b ^long i lim]
  (if (> a lim) i (recur (+' a b) a (+ 1 i) lim)))

(defun
  fibo
  ([i] (recur 1 0 i []))
  ([a b 0 res] res)
  ([a b i res] (recur (+' a b) a (- i 1) (conj res a))))

(def lfibo (lazy-cat [1 2] (map #(+' %1 %2) lfibo (rest lfibo))))









