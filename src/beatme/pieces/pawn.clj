(ns beatme.pieces.pawn
  (:require [beatme.pieces :as p]))

(defn is-black-on-second-row? [start-pos]
  (and (>= start-pos 8) (<= start-pos 15)))

(defn is-white-on-second-row? [start-pos]
  (and (>= start-pos 48) (<= start-pos 55)))

(defn find-forward-moves [game start-pos operator]
  (when (= p/none (get-in game [:board (operator start-pos 8)]))
    (if (and (if (= :w (:turn game)) (is-white-on-second-row? start-pos)
                                     (is-black-on-second-row? start-pos))
             (= p/none (get-in game [:board (operator start-pos 16)])))
      [(operator start-pos 8) (operator start-pos 16)]
      [(operator start-pos 8)])))

(defn can-capture-left?
  [game start-pos operator]
  (or (= (:en-pessant game) (operator start-pos 9))
      (and (not= 0 (mod start-pos 8))
           (not= p/none (get-in game [:board (operator start-pos 9)]))
           (if (> (get-in game [:board start-pos]) p/black)
             (< (get-in game [:board (operator start-pos 9)]) p/black)
             (> (get-in game [:board (operator start-pos 9)]) p/black)))))

(defn can-capture-right?
  [game start-pos operator]
  (or (= (:en-pessant game) (operator start-pos 7))
      (and (not= 7 (mod start-pos 8))
           (not= p/none (get-in game [:board (operator start-pos 7)]))
           (if (> (get-in game [:board start-pos]) p/black)
             (< (get-in game [:board (operator start-pos 7)]) p/black)
             (> (get-in game [:board (operator start-pos 7)]) p/black)))))

(defn find-capture-moves [game start-pos operator]
  (remove nil? [(when (can-capture-left? game start-pos operator) (operator start-pos 9))
                (when (can-capture-right? game start-pos operator) (operator start-pos 7))]))

(defn find-all-legal-moves
  ([game start-pos]
   (concat (find-forward-moves game start-pos (if (= (:turn game) :w) - +))
           (find-capture-moves game start-pos (if (= (:turn game) :w) - +)))))