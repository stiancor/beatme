(ns beatme.pieces.sliding-piece
  (:require [beatme.pieces :as p]
            [beatme.board :as b]))

(defn at-end-of-board? [direction position]
  (= (direction (b/square-info position)) 0))

(defn is-none-or-capture? [board piece check-pos]
  (or (= (get board check-pos) p/none)
      (not (p/is-friendly? piece (get board check-pos)))))

(defn is-capture? [board piece check-pos]
  (and (> (get board check-pos) p/none)
       (not (p/is-friendly? piece (get board check-pos)))))

(defn blocked-by-friendly? [board check-pos]
  (> (get board check-pos) p/none))

(defn find-all-legal-moves [board pos directions]
  (->> (for [d directions]
         (when-not (at-end-of-board? (:dir d) pos)
           (loop [check-pos (+ pos (:offset d))
                  moves []]
             (cond
               (at-end-of-board? (:dir d) check-pos)
               (if (is-none-or-capture? board (get board pos) check-pos)
                 (conj moves check-pos)
                 moves)
               (is-capture? board (get board pos) check-pos)
               (conj moves check-pos)
               (blocked-by-friendly? board check-pos)
               moves
               :else
               (recur (+ check-pos (:offset d))
                      (conj moves check-pos))))))
       (filter not-empty)
       flatten))
