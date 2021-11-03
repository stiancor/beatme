(ns beatme.pieces.rook-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.rook :refer [allowed-move-on-empty-board?]]))

(deftest test-moves-on-empty-board
  (testing "Moves that are ok for a rook"
    (is (allowed-move-on-empty-board? [0 0] [0 7]))
    (is (allowed-move-on-empty-board? [0 7] [0 0]))
    (is (allowed-move-on-empty-board? [0 1] [0 2]))
    (is (allowed-move-on-empty-board? [0 2] [0 5]))
    (is (allowed-move-on-empty-board? [0 0] [7 0]))
    (is (allowed-move-on-empty-board? [7 0] [0 0]))
    (is (allowed-move-on-empty-board? [4 0] [1 0]))
    (is (allowed-move-on-empty-board? [2 0] [6 0])))
  (testing "Cannot move to same square"
    (is (not (allowed-move-on-empty-board? [4 4] [4 4]))))
  (testing "Illegal moves with rook"
    (is (not (allowed-move-on-empty-board? [0 0] [1 1])))
    (is (not (allowed-move-on-empty-board? [7 2] [6 3])))
    (is (not (allowed-move-on-empty-board? [0 0] [0 8])))))
