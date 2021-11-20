(ns beatme.pieces.queen
  (:require [beatme.pieces.rook :as r]
            [beatme.pieces.bishop :as b]
            [beatme.board :as board]))

(defn possible-queen-moves [old-pos]
  (concat [(partition 2 (interleave (range (dec (first old-pos)) -1 -1) (range (dec (second old-pos)) -1 -1)))]
          [(partition 2 (interleave (range (dec (first old-pos)) -1 -1) (repeat (second old-pos))))]
          [(partition 2 (interleave (range (dec (first old-pos)) -1 -1) (range (inc (second old-pos)) 8)))]
          [(partition 2 (interleave (repeat (first old-pos)) (range (dec (second old-pos)) -1 -1)))]
          [(partition 2 (interleave (range (inc (first old-pos)) 8) (range (dec (second old-pos)) -1 -1)))]
          [(partition 2 (interleave (range (inc (first old-pos)) 8) (repeat (second old-pos))))]
          [(partition 2 (interleave (range (inc (first old-pos)) 8) (range (inc (second old-pos)) 8)))]
          [(partition 2 (interleave (repeat (first old-pos)) (range (inc (second old-pos)) 8)))]))

(defn move-allowed? [board current-player old-pos new-pos]
  (and (not= old-pos new-pos)
       (board/is-inside-board? new-pos)
       (or (and (r/is-straight-line? old-pos new-pos)
                (r/is-path-open? board current-player old-pos new-pos))
           (and (b/is-diagonal-line? old-pos new-pos)
                (b/is-path-open? board current-player old-pos new-pos)))))




