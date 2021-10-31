(ns beatme.board)

(def y-axis (clojure.string/split "ABCDEFGH" #""))

(defn is-inside-board? [position board]
  (let [max-x (count board)
        max-y (-> board first count)
        x (first position)
        y (last position)]
    (and (>= x 0) (< x max-x)
         (>= y 0) (< y max-y))))

(defn create-empty []
  (vec (for [i (range 8)]
         (vec (for [j (range 8)]
                {:x (inc i)
                 :y (get y-axis j)})))))
