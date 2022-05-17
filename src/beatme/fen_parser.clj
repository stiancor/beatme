(ns beatme.fen-parser
  (:require [beatme.pieces :as p]
            [clojure.string :as str]))

(def starting-FEN "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")

(defn- resolve-castle-rights [castle-rights]
  {:castle-king-side? (some? (re-find #"[K|k]" castle-rights))
   :castle-queen-side? (some? (re-find #"[Q|q]" castle-rights))})

(defn- init-game-state [fen]
  (let [state (rest (str/split fen #" "))]
    {:turn (-> state first keyword)
     :w (resolve-castle-rights (re-find #"[K|Q]+" (second state)))
     :b (resolve-castle-rights (re-find #"[k|q]+" (second state)))
     :en-passant (->> state (drop 2) first)
     :moves-since-last-capture (->> state (drop 3) first Integer/parseInt)
     :move-number (-> state last Integer/parseInt)}))

(defn init-piece-positions [fen]
  (let [fen-rows (-> (re-find #"\S+" fen)
                     (str/split #"/"))]
    (vec (flatten (for [row fen-rows]
                    (for [n row]
                      (let [n (if (re-find #"\d" (str n))
                                (Integer/parseInt (str n))
                                (str n))]
                        (cond
                          (number? n) (take n (repeat p/none))
                          (= n "p") (+ p/black p/pawn)
                          (= n "k") (+ p/black p/king)
                          (= n "b") (+ p/black p/bishop)
                          (= n "n") (+ p/black p/knight)
                          (= n "r") (+ p/black p/rook)
                          (= n "q") (+ p/black p/queen)
                          (= n "P") (+ p/white p/pawn)
                          (= n "K") (+ p/white p/king)
                          (= n "B") (+ p/white p/bishop)
                          (= n "N") (+ p/white p/knight)
                          (= n "R") (+ p/white p/rook)
                          (= n "Q") (+ p/white p/queen)))))))))

(defn- setup-game-position [fen]
  (merge {:board (init-piece-positions fen)}
         (init-game-state fen)))

(defn init-position [fen]
  (setup-game-position fen))

(defn init-starting-position []
  (setup-game-position starting-FEN))