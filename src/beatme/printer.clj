(ns beatme.printer)

(defn get-square-str [square]
  (let [type (-> square :piece :type)
        color (-> square :piece :color)
        v (cond (= type :pawn) (if (= color :white) "♙" "♟︎")
                (= type :rook) (if (= color :white) "♖" "♜")
                (= type :bishop) (if (= color :white) "♗" "♝")
                (= type :knight) (if (= color :white) "♘" "♞")
                (= type :queen) (if (= color :white) "♕" "♛")
                (= type :king) (if (= color :white) "♔" "♚")
                :else " ")]
    (str " " v " ")))

(defn print-board [board]
  (clojure.pprint/pprint
    (vec (for [row board]
           (reduce #(str %1 (get-square-str %2)) "" row)))))

(defn print-board-with-notation [board]
  (clojure.pprint/pprint
    (conj (vec (for [row board]
                 (str (-> row first :y) (reduce #(str %1 (get-square-str %2)) "" row))))
          (str (reduce #(str %1 " " (-> %2 :x) " ") " " (first board))))))