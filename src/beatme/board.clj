(ns beatme.board)

(def y-axis (clojure.string/split "ABCDEFGH" #""))

(defn define-board []
  (vec (for [i (range 8)]
         (vec (for [j (range 8)]
                {:x (inc i)
                 :y (get y-axis j)})))))
