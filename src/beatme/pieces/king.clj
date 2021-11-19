(ns beatme.pieces.king
  (:require
    [beatme.board :as b]))

(defn possible-king-moves [old-pos]
  (remove nil?
          [(b/is-inside-board? [(-> old-pos first dec) (-> old-pos second dec)])
           (b/is-inside-board? [(-> old-pos first) (-> old-pos second dec)])
           (b/is-inside-board? [(-> old-pos first inc) (-> old-pos second dec)])
           (b/is-inside-board? [(-> old-pos first dec) (-> old-pos second)])
           (b/is-inside-board? [(-> old-pos first inc) (-> old-pos second)])
           (b/is-inside-board? [(-> old-pos first dec) (-> old-pos second inc)])
           (b/is-inside-board? [(-> old-pos first) (-> old-pos second inc)])
           (b/is-inside-board? [(-> old-pos first inc) (-> old-pos second inc)])]))

(defn is-possible-move?
  [old-pos new-pos]
  (and (some #(= % new-pos) (possible-king-moves old-pos))))

(defn allowed-move? [board current-player old-pos new-pos]
  (and (is-possible-move? old-pos new-pos)
       (b/square-occupied-by-opponent? board new-pos current-player)))