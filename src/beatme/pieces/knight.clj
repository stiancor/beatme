(ns beatme.pieces.knight
  (:require [beatme.board :as b]))

(defn possible-moves [[a b]]
  (filter b/is-inside-board?
          [[(- a 1) (- b 2)]
           [(- a 1) (+ b 2)]
           [(- a 2) (- b 1)]
           [(- a 2) (+ b 1)]
           [(+ a 1) (- b 2)]
           [(+ a 1) (+ b 2)]
           [(+ a 2) (- b 1)]
           [(+ a 2) (+ b 1)]]))

(defn is-possible-move? "Only checks that the knight moves according to its bounds.
 Not checking if the destination square is occupied"
  [old-pos new-pos]
  (some #(= % new-pos) (possible-moves old-pos)))

(defn allowed-move? [board current-player old-pos new-pos]
  (and (is-possible-move? old-pos new-pos)
       (b/square-occupied-by-opponent? board new-pos current-player)
       (b/available-square? board current-player old-pos new-pos)))
