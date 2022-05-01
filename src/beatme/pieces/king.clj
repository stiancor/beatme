(ns beatme.pieces.king
  (:require
    [beatme.board :as b]))

(defn find-all-legal-moves [game current-pos]
  (let [square (b/square-info current-pos)]
    (remove nil?
            [(when (and (> (:north-west square) 0) (b/square-empty-or-opponent? game (- current-pos 9))) (- current-pos 9))
             (when (and (> (:north square) 0) (b/square-empty-or-opponent? game (- current-pos 8))) (- current-pos 8))
             (when (and (> (:north-east square) 0) (b/square-empty-or-opponent? game (- current-pos 7))) (- current-pos 7))
             (when (and (> (:west square) 0) (b/square-empty-or-opponent? game (- current-pos 1))) (- current-pos 1))
             (when (and (> (:east square) 0) (b/square-empty-or-opponent? game (+ current-pos 1))) (+ current-pos 1))
             (when (and (> (:south-west square) 0) (b/square-empty-or-opponent? game (+ current-pos 7))) (+ current-pos 7))
             (when (and (> (:south square) 0) (b/square-empty-or-opponent? game (+ current-pos 8))) (+ current-pos 8))
             (when (and (> (:south-east square) 0) (b/square-empty-or-opponent? game (+ current-pos 9))) (+ current-pos 9))])))