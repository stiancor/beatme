(ns beatme.pieces.rook-test
  (:require [clojure.test :refer :all]
            [beatme.pieces.rook :refer [is-straight-line? is-path-open? allowed-move?]]
            [beatme.board :as board]))

(deftest test-moves-in-straigh-line
  (testing "Moves that are ok for a rook"
    (is (is-straight-line? [0 0] [0 7]))
    (is (is-straight-line? [0 7] [0 0]))
    (is (is-straight-line? [0 1] [0 2]))
    (is (is-straight-line? [0 2] [0 5]))
    (is (is-straight-line? [0 0] [7 0]))
    (is (is-straight-line? [7 0] [0 0]))
    (is (is-straight-line? [4 0] [1 0]))
    (is (is-straight-line? [2 0] [6 0])))
  (testing "Cannot move to same square"
    (is (not (is-straight-line? [4 4] [4 4]))))
  (testing "Illegal moves with rook"
    (is (not (is-straight-line? [0 0] [1 1])))
    (is (not (is-straight-line? [7 2] [6 3])))))

(deftest test-is-path-open
  (let [board (board/create-empty-board)]
    (testing "All squares are empty"
      (is (is-path-open? board :white [0 0] [0 7]))
      (is (is-path-open? board :black [0 0] [0 7]))
      (is (is-path-open? board :white [6 6] [1 6]))
      (is (is-path-open? board :black [6 6] [1 6])))
    (testing "Piece on the board but not on the travelling path"
      (let [b (assoc-in board [4 4 :piece] {:color :white :type :pawn})]
        (is (is-path-open? b :white [0 3] [0 0]))
        (is (is-path-open? b :white [5 0] [5 7]))
        (is (is-path-open? b :white [1 6] [6 6]))))
    (testing "Players own piece is in the way"
      (let [b (assoc-in board [4 4 :piece] {:color :white :type :pawn})]
        (is (not (is-path-open? b :white [4 0] [4 7])))
        (is (not (is-path-open? b :white [7 4] [0 4])))))
    (testing "Opponents piece is in the way"
      (let [b (assoc-in board [4 4 :piece] {:color :black :type :pawn})]
        (is (not (is-path-open? b :white [4 0] [4 7])))
        (is (not (is-path-open? b :white [7 4] [0 4])))))
    (testing "Players own piece at the end position fails"
      (is (not (is-path-open? (assoc-in board [4 4 :piece] {:color :white :type :pawn}) :white [4 0] [4 4]))))
    (testing "Opponents piece at the end location is a capture"
      (is (is-path-open? (assoc-in board [4 4 :piece] {:color :black :type :pawn}) :white [4 0] [4 4])))))

(deftest testing-all-rook-move-rules
  (let [board (assoc-in (board/create-empty-board) [2 2 :piece] {:color :white :type :queen})]
    (testing "Cannot move to the same square"
      (is (not (allowed-move? board :white [4 4] [4 4]))))
    (testing "Must move inside board"
      (is (not (allowed-move? board :white [0 0] [0 8])))
      (is (not (allowed-move? board :white [0 0] [0 -1]))))
    (testing "Can only move in a straight line"
      (is (not (allowed-move? board :white [0 0] [1 7])))
    (testing "Can move when path is not blocked"
      (is (allowed-move? board :white [0 7] [0 1])))
    (testing "Can not move when path is blocked"
      (is (not (allowed-move? board :white [2 0] [2 7]))))
    (testing "Can move when last square has opponent on it"
      (is (allowed-move? board :black [2 0] [2 2])))
    (testing "But not when last square has your own piece on it"
      (is (not (allowed-move? board :white [2 0] [2 2])))))))