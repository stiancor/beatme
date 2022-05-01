(ns beatme.board
  (:require [beatme.pieces :as pieces]
            [clojure.string :as str]))

(defn create-empty-board []
  (->> (repeat pieces/none)
       (take 64)
       vec))

(defn to-position [notation]
  (if (re-matches #"^[a-hA-H][1-8]$" notation)
    (+ (* 8 (- 7 (-> notation second str Integer/parseInt dec)))
       (case (-> notation first str str/upper-case)
         "A" 0
         "B" 1
         "C" 2
         "D" 3
         "E" 4
         "F" 5
         "G" 6
         "H" 7))
    (throw (ex-info "Get your notation straight!" {:invalid-notation notation}))))

(defn to-positions [& args]
  (set (for [a args]
         (to-position a))))

(defn set-piece-with-notation [board notation piece]
  (assoc board (to-position notation) piece))

(defn is-inside-board? [position]
  (and (>= position 0) (<= position 63)))

(defn square-empty? [board position]
  (= (get board position) pieces/none))

(defn square-empty-or-opponent? [game position]
  (or (square-empty? (:board game) position)
      (if (= (:turn game) :w)
        (> (get-in game [:board position]) pieces/black)
        (< (get-in game [:board position]) pieces/white))))

(defn- init-precomputed-squares []
  (reduce #(merge %1 %2) {}
          (flatten
            (for [file (range 0 8)]
              (for [rank (range 0 8)]
                (hash-map (+ (* rank 8) file) {:north rank
                                               :south (- 7 rank)
                                               :west file
                                               :east (- 7 file)
                                               :north-west (min rank file)
                                               :south-east (min (- 7 rank) (- 7 file))
                                               :north-east (min rank (- 7 file))
                                               :south-west (min (- 7 rank) file)}))))))

(def precomputed-squares
  (memoize init-precomputed-squares))

(defn square-info [pos]
  (get (precomputed-squares) pos))
