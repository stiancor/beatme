(ns beatme.pieces.pawn-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.pawn :as pawn]
            [beatme.board :as b]))

(deftest check-pawn-is-allowed-to-move-straight-ahead
  (let [board (b/create-empty-board)]
    (testing "When nothing is in front, pawn can move one step"
      (is (pawn/allowed-move? board [0 1] [0 2] :white)))
    (testing "When nothing in front, pawn can move two steps from row two but only row two"
      (is (pawn/allowed-move? board [0 1] [0 3] :white))
      (is (not (pawn/allowed-move? board [0 2] [0 4] :white))))
    (testing "Illegal moves are not allowed even on empty boards"
      (is (not (pawn/allowed-move? board [0 7] [0 8] :white)))
      (is (not (pawn/allowed-move? board [0 1] [0 1] :white)))
      (is (not (pawn/allowed-move? board [0 1] [0 4] :white)))
      (is (not (pawn/allowed-move? board [0 2] [0 1] :white)))
      (is (not (pawn/allowed-move? board [0 1] [1 3] :white)))
      (is (not (pawn/allowed-move? board [0 0] [0 -1] :white))))))

(deftest check-pawn-is-allowed-to-capture
  (let [board (-> (b/create-empty-board)
                  (b/set-piece [3 3] {:type :horse :color :white})
                  (b/set-piece [6 6] {:type :queen :color :black})
                  (b/set-piece [5 4] {:type :pawn :color :white})
                  (b/set-piece [5 5] {:type :pawn :color :white})
                  (b/set-piece [5 6] {:type :pawn :color :white})
                  (b/set-piece [5 7] {:type :pawn :color :white})
                  (b/set-piece [5 8] {:type :pawn :color :white})
                  (b/set-piece [0 1] {:type :pawn :color :white}))]
    (testing "Captures can only happen diagonally"
      (is (pawn/capture-move-allowed? board [5 5] [6 6] :white))
      (is (pawn/capture-move-allowed? board [7 5] [6 6] :white))
      (is (not (pawn/capture-move-allowed? board [6 5] [6 6] :white)))
      (is (not (pawn/capture-move-allowed? board [4 5] [6 6] :white)))
      (is (not (pawn/capture-move-allowed? board [8 5] [6 6] :white))))
    (testing "Captures connot happen from random places around the board"
      (is (not (pawn/capture-move-allowed? board [0 1] [6 6] :white)))
      (is (not (pawn/capture-move-allowed? board [7 7] [6 6] :white))))))
