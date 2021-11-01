(ns beatme.pieces.pawn
  (:require [beatme.board :as b]))

(defn straight-ahead-move-allowed? [board old-pos new-pos]
  (and (b/is-inside-board? new-pos)
       (not (b/square-occupied? board new-pos))
       (= (first old-pos) (first new-pos))
       (> (last new-pos) (last old-pos))
       (< (- (last new-pos) (last old-pos)) (if (= 1 (last old-pos)) 3 2))))

(defn capture-move-allowed? [board old-pos new-pos player-color]
  (and (b/is-inside-board? new-pos)
       (b/square-occupied-by-opponent? board new-pos player-color)
       (#{(dec (first old-pos)) (inc (first old-pos))} (first new-pos))
       (= (last old-pos) (dec (last new-pos)))))

(defn allowed-move? [board old-pos new-pos player-color]
  (if (= (first old-pos) (first new-pos))
    (straight-ahead-move-allowed? board old-pos new-pos)
    (capture-move-allowed? board old-pos new-pos player-color)))



