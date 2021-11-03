(ns beatme.pieces.rook
  (:require [beatme.board :as b]))

(defn allowed-move-on-empty-board? [old-pos new-pos]
  (and (b/is-inside-board? new-pos)
       (or (and (= (first old-pos) (first new-pos))
                (not= (last old-pos) (last new-pos)))
           (and (= (last old-pos) (last new-pos))
                (not= (first old-pos) (first new-pos))))))
