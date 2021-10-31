(ns beatme.board)

(def y-axis (clojure.string/split "ABCDEFGH" #""))

(defn is-inside-board? [board position]
  (let [max-x (count board)
        max-y (-> board first count)
        x (first position)
        y (last position)]
    (and (>= x 0) (< x max-x)
         (>= y 0) (< y max-y))))

(defn square-occupied? [board position]
  (get-in board (conj position :piece)))

(defn create-empty []
  (vec (for [i (range 8)]
         (vec (for [j (range 8)]
                {:x (inc i)
                 :y (get y-axis j)})))))

(defn add-piece [board position piece]
  (assoc-in board (conj position :piece) piece))
