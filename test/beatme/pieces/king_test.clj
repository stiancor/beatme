(ns beatme.pieces.king-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.king :as k]
            [beatme.board :as b]
            [beatme.pieces :as p]))

(deftest possible-king-moves-in-the-corner
  (let [game {:board (-> (b/create-empty-board)
                         (assoc (b/to-position "H1") (+ p/white p/king)))
              :turn :w}]
    (is (= (b/to-positions "H2" "G1" "G2")
           (set (k/find-all-legal-moves game (b/to-position "H1")))))))

(deftest possible-king-move-when-every-direction-is-free
  (let [game {:board (-> (b/create-empty-board)
                         (assoc (b/to-position "D4") (+ p/white p/king)))
              :turn :w}]
    (is (= (b/to-positions "E3" "E4" "E5" "D3" "D5" "C3" "C4" "C5")
           (set (k/find-all-legal-moves game (b/to-position "D4")))))))

(deftest possible-king-move-when-opponent-can-be-captured
  (let [game {:board (-> (b/create-empty-board)
                         (assoc (b/to-position "D4") (+ p/white p/king))
                         (assoc (b/to-position "D5") (+ p/black p/knight)))
              :turn :w}]
    (is (= (b/to-positions "E3" "E4" "E5" "D3" "D5" "C3" "C4" "C5")
           (set (k/find-all-legal-moves game (b/to-position "D4")))))))

(deftest possible-king-move-when-friendly-on-the-side
  (let [game {:board (-> (b/create-empty-board)
                         (assoc (b/to-position "D4") (+ p/white p/king))
                         (assoc (b/to-position "D5") (+ p/white p/knight)))
              :turn :w}]
    (is (= (b/to-positions "E3" "E4" "E5" "D3" "C3" "C4" "C5")
           (set (k/find-all-legal-moves game (b/to-position "D4")))))))
                              