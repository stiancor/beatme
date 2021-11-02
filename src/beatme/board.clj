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

(defn get-piece [board position]
  (get-in board (conj position :piece)))

(defn square-occupied? [board position]
  (get-piece board position))

(defn square-occupied-by-opponent? [board position player-color]
  (when-let [piece (square-occupied? board position)]
    (not= player-color (:color piece))))

(defn set-piece [board position piece]
  (assoc-in board (conj position :piece) piece))

(defn move-piece [board old-pos new-pos]
  (let [piece (get-in board (conj old-pos :piece))]
    (-> board
        (update-in old-pos dissoc :piece)
        (assoc-in (conj new-pos :piece) piece))))

(defn flip-board [board]
  (vec (reverse (for [row board]
                  (vec (reverse row))))))
