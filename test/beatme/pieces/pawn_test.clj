(ns beatme.pieces.pawn-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.pawn :as pawn]
            [beatme.board :as b]))

(deftest check-pawn-is-allowed-to-move-straight-ahead
  (let [board (b/create-empty)]
    (testing "When nothing is in front, pawn can move one step"
      (is (pawn/allowed-move? board [0 1] [0 2])))
    (testing "When nothing in front, pawn can move two steps from row two but only row two"
      (is (pawn/allowed-move? board [0 1] [0 3]))
      (is (not (pawn/allowed-move? board [0 2] [0 4]))))
    (testing "Illegal moves are not allowed even on empty boards"
      (is (not (pawn/allowed-move? board [0 7] [0 8])))
      (is (not (pawn/allowed-move? board [0 1] [0 1])))
      (is (not (pawn/allowed-move? board [0 1] [0 4])))
      (is (not (pawn/allowed-move? board [0 2] [0 1])))
      (is (not (pawn/allowed-move? board [0 1] [1 3])))
      (is (not (pawn/allowed-move? board [0 0] [0 -1]))))))
