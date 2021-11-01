(ns beatme.board)

(def y-axis (clojure.string/split "ABCDEFGH" #""))

(defn create-empty []
  (vec (for [i (range 8)]
         (vec (for [j (range 8)]
                {:x (inc i)
                 :y (get y-axis j)})))))

(defn is-inside-board? [position]
  (let [x (first position)
        y (last position)]
    (and (>= x 0) (< x 8)
         (>= y 0) (< y 8))))

(defn square-occupied? [board position]
  (get-in board (conj position :piece)))

(defn add-piece [board position piece]
  (assoc-in board (conj position :piece) piece))
