(ns beatme.pieces)

(defonce none 0)
(defonce king 1)
(defonce pawn 2)
(defonce bishop 3)
(defonce knight 4)
(defonce rook 5)
(defonce queen 6)

(defonce white 8)
(defonce black 16)

(defn is-white? [piece]
  (and (> piece white)
       (< piece black)))

(defn is-black? [piece]
  (> piece black))

(defn is-friendly? [p1 p2]
  (or (and (is-black? p1) (is-black? p2))
      (and (is-white? p1) (is-white? p2))))