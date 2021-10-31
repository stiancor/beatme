(ns beatme.pieces.pawn
  (:require [beatme.board :as b]))

(defn allowed-move? [board old-pos new-pos]
  (and (= (first old-pos) (first new-pos))
       (> (last new-pos) (last old-pos))
       (< (- (last new-pos) (last old-pos)) (if (= 1 (second old-pos)) 3 2))
       (< (last new-pos) 8)))



