(ns beatme.printer
  (:require [beatme.pieces :as pieces]))

(defn get-square-str [square]
  (let [is-white? (< square 16)
        piece-value (- square (if is-white? 8 16))
        v (cond (= pieces/pawn piece-value) (if is-white? "♟︎" "♙")
                (= pieces/rook piece-value) (if is-white? "♜" "♖")
                (= pieces/bishop piece-value) (if is-white? "♝" "♗")
                (= pieces/knight piece-value) (if is-white? "♞" "♘")
                (= pieces/queen piece-value) (if is-white? "♛" "♕")
                (= pieces/king piece-value) (if is-white? "♚" "♔")
                :else "_")]
    (str " " v " ")))

(defn- prep-board [board]
  (let [rows (partition 8 board)]
    (vec (for [row rows]
           (reduce #(str %1 (get-square-str %2)) "" row)))))

(defn print-board [game]
  (doall (map #(println %)
              (prep-board (:board game)))))

(defn print-board-with-notation [game]
  (let [p-board (prep-board (:board game))
        board-with-index (->> (interleave (range 8 0 -1) p-board)
                              (partition 2)
                              (map #(apply str (str (first %) " " (second %)))))]
    (doall (map #(println %) (concat board-with-index ["   A  B  C  D  E  F  G  H "])))))

(defn print-game-state
  ([game] (print-game-state game false))
  ([game without-board-notation?]
   (if without-board-notation?
     (print-board game)
     (print-board-with-notation game))
   (clojure.pprint/pprint (dissoc game :board))))