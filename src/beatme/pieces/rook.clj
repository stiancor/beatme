(ns beatme.pieces.rook
  (:require [beatme.board :as b]
            [beatme.pieces.common :as common]))

(defn- define-range [start end]
  (let [is-positive? (> (- end start) 0)]
    (range start (if is-positive? (inc end) (dec end)) (if is-positive? 1 -1))))

(defn is-straight-line? [old-pos new-pos]
  (or (and (= (first old-pos) (first new-pos))
           (not= (last old-pos) (last new-pos)))
      (and (= (last old-pos) (last new-pos))
           (not= (first old-pos) (first new-pos)))))

(defn get-travelling-path [old-pos new-pos]
  (map vec (partition 2 (if (= (first old-pos) (first new-pos))
                          (interleave (repeat (first new-pos)) (define-range (last old-pos) (last new-pos)))
                          (interleave (define-range (first old-pos) (first new-pos)) (repeat (last new-pos)))))))

(defn is-path-open? [board current-player old-pos new-pos]
  (every? true? (for [square (get-travelling-path old-pos new-pos)]
                  (b/available-square? board current-player square new-pos))))

(defn allowed-move? [board current-player old-pos new-pos]
  (and (common/not-same-square? old-pos new-pos)
    (b/is-inside-board? new-pos)
       (is-straight-line? old-pos new-pos)
       (is-path-open? board current-player old-pos new-pos)))