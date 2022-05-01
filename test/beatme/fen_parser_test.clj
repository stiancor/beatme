(ns beatme.fen-parser-test
  (:require [clojure.test :refer :all])
  (:require [beatme.fen-parser :refer :all]
            [beatme.pieces :as p]
            [beatme.board :as b]))

(deftest define-board-test
  (let [board (:board (init-starting-position))]
    (testing "vector of 64 squares"
      (is (= 64 (count board))))
    (testing "Outside board gives back nil"
      (is (nil? (get board -1)))
      (is (nil? (get board 64))))
    (testing "Checking a subset of the cells"
      (is (= (get board (b/to-position "A8")) (+ p/rook p/black)))
      (is (= (get board (b/to-position "B8")) (+ p/knight p/black)))
      (is (= (get board (b/to-position "C8")) (+ p/bishop p/black)))
      (is (= (get board (b/to-position "D8")) (+ p/queen p/black)))
      (is (= (get board (b/to-position "E8")) (+ p/king p/black)))
      (is (= (get board (b/to-position "F8")) (+ p/bishop p/black)))
      (is (= (get board (b/to-position "G8")) (+ p/knight p/black)))
      (is (= (get board (b/to-position "H8")) (+ p/rook p/black)))
      (is (= (get board (b/to-position "A7")) (+ p/pawn p/black)))
      (is (= (get board (b/to-position "E7")) (+ p/pawn p/black)))
      (is (= (get board (b/to-position "H7")) (+ p/pawn p/black)))

      (is (= (get board (b/to-position "A6")) p/none))
      (is (= (get board (b/to-position "F5")) p/none))
      (is (= (get board (b/to-position "H3")) p/none))

      (is (= (get board (b/to-position "A1")) (+ p/rook p/white)))
      (is (= (get board (b/to-position "B1")) (+ p/knight p/white)))
      (is (= (get board (b/to-position "C1")) (+ p/bishop p/white)))
      (is (= (get board (b/to-position "D1")) (+ p/queen p/white)))
      (is (= (get board (b/to-position "E1")) (+ p/king p/white)))
      (is (= (get board (b/to-position "F1")) (+ p/bishop p/white)))
      (is (= (get board (b/to-position "G1")) (+ p/knight p/white)))
      (is (= (get board (b/to-position "H1")) (+ p/rook p/white)))
      (is (= (get board (b/to-position "A2")) (+ p/pawn p/white)))
      (is (= (get board (b/to-position "E2")) (+ p/pawn p/white)))
      (is (= (get board (b/to-position "H2")) (+ p/pawn p/white))))))

(deftest check-game-state-on-start
  (let [game (init-starting-position)]
    (testing "Turn is white"
      (is (= :w (:turn game))))
    (testing "Move number"
      (is (= 1 (:move-number game))))
    (testing "Possible en-pessant"
      (is (= "-" (:en-passant game))))
    (testing "No moves since last capture"
      (is (= 0 (:moves-since-last-capture game))))
    (testing "Verify that both side are allowed to castle king and queen side"
      (is (-> game :white :castle-king-side?))
      (is (-> game :white :castle-queen-side?))
      (is (-> game :black :castle-king-side?))
      (is (-> game :black :castle-queen-side?)))))