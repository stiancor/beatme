(ns beatme.board)

(def x-axis-notation (clojure.string/split "ABCDEFGH" #""))

(defn create-empty-board []
  (vec (for [i (range 7 -1 -1)]
         (vec (for [j (range 8)]
                {:x (get x-axis-notation j)
                 :y (inc i) })))))

(defn set-piece [board position piece]
  (assoc-in board (conj position :piece) piece))

(defn- init-rooks [b]
  (-> b
      (assoc-in [0 0 :piece] {:type :rook :color :black})
      (assoc-in [0 7 :piece] {:type :rook :color :black})
      (assoc-in [7 0 :piece] {:type :rook :color :white})
      (assoc-in [7 7 :piece] {:type :rook :color :white})))

(defn- init-knights [b]
  (-> b
      (assoc-in [0 1 :piece] {:type :knight :color :black})
      (assoc-in [0 6 :piece] {:type :knight :color :black})
      (assoc-in [7 1 :piece] {:type :knight :color :white})
      (assoc-in [7 6 :piece] {:type :knight :color :white})))

(defn- init-bishops [b]
  (-> b
      (assoc-in [0 2 :piece] {:type :bishop :color :black})
      (assoc-in [0 5 :piece] {:type :bishop :color :black})
      (assoc-in [7 2 :piece] {:type :bishop :color :white})
      (assoc-in [7 5 :piece] {:type :bishop :color :white})))

(defn- init-queens [b]
  (-> b
      (assoc-in [0 3 :piece] {:type :queen :color :black})
      (assoc-in [7 3 :piece] {:type :queen :color :white})))

(defn- init-kings [b]
  (-> b
      (assoc-in [0 4 :piece] {:type :king :color :black})
      (assoc-in [7 4 :piece] {:type :king :color :white})))

(defn- init-pawns [b]
  (let [b (reduce #(assoc-in %1 [1 %2 :piece] {:type :pawn :color :black}) b (range 8))]
    (reduce #(assoc-in %1 [6 %2 :piece] {:type :pawn :color :white}) b (range 8))))

(defn init-starting-position [b]
  (-> b
      init-rooks
      init-knights
      init-bishops
      init-queens
      init-kings
      init-pawns))

(defn get-starting-position []
  (init-starting-position (create-empty-board)))

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

(defn is-inside-board? [position]
  (let [x (first position)
        y (last position)]
    (when (and (>= x 0) (< x 8)
               (>= y 0) (< y 8))
      position)))

(defn get-piece [board position]
  (get-in board (conj position :piece)))

(defn square-occupied? [board position]
  (get-piece board position))

(defn square-empty? [board position]
  (not (get-piece board position)))

(defn square-occupied-by-opponent? [board position player-color]
  (when-let [piece (square-occupied? board position)]
    (not= player-color (:color piece))))

(defn move-piece [board old-pos new-pos]
  (let [piece (get-in board (conj old-pos :piece))]
    (-> board
        (update-in old-pos dissoc :piece)
        (assoc-in (conj new-pos :piece) piece))))

(defn flip-board [board]
  (vec (reverse (for [row board]
                  (vec (reverse row))))))

(defn available-square? [board current-player pos final-pos]
  (or (square-empty? board pos)
      (when (= pos final-pos) (square-occupied-by-opponent? board pos current-player))))
