(ns beatme.pieces.rook
  (:require [beatme.board :as b]))

(defn is-straight-line? [old-pos new-pos]
  (or (and (= (first old-pos) (first new-pos))
           (not= (last old-pos) (last new-pos)))
      (and (= (last old-pos) (last new-pos))
           (not= (first old-pos) (first new-pos)))))

(defn- define-range [start end]
  (range start end (if (> start end) -1 1)))

(defn is-path-open? [board current-player old-pos new-pos]
  (let [first-new-pos (first new-pos)
        last-new-pos (last new-pos)]
    (every? true? (if (= (first old-pos) first-new-pos)
                    (for [i (define-range (last old-pos) (inc last-new-pos))]
                      (b/available-square? board current-player [first-new-pos i] new-pos))
                    (for [i (define-range (first old-pos) (inc first-new-pos))]
                      (b/available-square? board current-player [i (last old-pos)] new-pos))))))

(defn allowed-move? [board current-player old-pos new-pos]
  (and (b/is-inside-board? new-pos)
       (is-straight-line? old-pos new-pos)
       (is-path-open? board current-player old-pos new-pos)))
