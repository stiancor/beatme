(ns beatme.pieces.bishop
  (:require [beatme.board :as b]))

(defn is-diagonal-line? [old-pos new-pos]
  (let [x-diff (- (first new-pos) (first old-pos))
        y-diff (- (second new-pos) (second old-pos))]
    (= (Math/abs x-diff) (Math/abs y-diff))))

(defn get-travelling-path [old-pos new-pos]
  (let [x-direction (if (> (first old-pos) (first new-pos)) -1 1)
        y-direction (if (> (last old-pos) (last new-pos)) -1 1)]
    (map vec (partition 2 (interleave (range (first old-pos)
                                             (if (= x-direction 1)
                                               (inc (first new-pos))
                                               (dec (first new-pos)))
                                             x-direction)
                                      (range (last old-pos)
                                             (if (= y-direction 1)
                                               (inc (last new-pos))
                                               (dec (last new-pos)))
                                             y-direction))))))

(defn is-path-open? [board current-player old-pos new-pos]
  (every? true? (for [square (get-travelling-path old-pos new-pos)]
                  (do (prn square)
                      (b/available-square? board current-player square new-pos)))))

(defn allowed-move? [board current-player old-pos new-pos]
  (and (not= old-pos new-pos)
       (b/is-inside-board? new-pos)
       (is-diagonal-line? old-pos new-pos)
       (is-path-open? board current-player old-pos new-pos)))