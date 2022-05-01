(ns beatme.pieces.knight
  (:require [beatme.board :as b]
            [beatme.pieces :as p]))

(defonce move-template
         [{:diff 17 :x 1 :y 2}
          {:diff 10 :x 2 :y 1}
          {:diff -6 :x 2 :y -1}
          {:diff -15 :x 1 :y -2}
          {:diff -17 :x -1 :y -2}
          {:diff -10 :x -2 :y -1}
          {:diff 6 :x -2 :y 1}
          {:diff 15 :x -1 :y 2}])

(defn get-east-west [d]
  (if (pos-int? d) :east :west))

(defn get-north-south [d]
  (if (pos-int? d) :south :north))

(defn find-all-legal-moves [board pos]
  (let [square (b/square-info pos)]
    (remove nil?
            (for [m move-template]
              (when (and (>= ((get-east-west (:x m)) square) (abs (:x m)))
                         (>= ((get-north-south (:y m)) square) (abs (:y m)))
                         (or (= (get board (+ pos (:diff m))) p/none)
                             (not (p/is-friendly? (get board pos) (get board (+ pos (:diff m)))))))
                (+ pos (:diff m)))))))
