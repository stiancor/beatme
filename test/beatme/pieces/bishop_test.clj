(ns beatme.pieces.bishop-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.bishop :refer :all]
            [beatme.board :as board]))

(deftest only-allow-diagonal-movement
  (testing "All diagonal movements should work"
    (is (is-diagonal-line? [7 0] [0 7]))
    (is (is-diagonal-line? [0 7] [7 0]))
    (is (is-diagonal-line? [0 0] [7 7]))
    (is (is-diagonal-line? [7 7] [0 0]))
    (is (is-diagonal-line? [3 2] [2 1]))
    (is (is-diagonal-line? [6 2] [2 6])))
  (testing "Non diagonal movements are not allowed"
    (is (not (is-diagonal-line? [6 2] [2 7])))
    (is (not (is-diagonal-line? [0 0] [0 7])))
    (is (not (is-diagonal-line? [3 3] [5 6])))))

(deftest test-is-path-open
  (let [board (board/create-empty-board)]
    (testing "All squares are empty"
      (is (is-path-open? board :white [0 0] [7 7]))
      (is (is-path-open? board :black [0 0] [7 7])))
    (testing "Piece on the board but not in travelling path"
      (let [b (assoc-in board [4 3 :piece] {:color :white :type :pawn})]
        (is (is-path-open? b :white [0 0] [7 7]))
        (is (is-path-open? b :black [0 0] [7 7]))))
    (testing "Bishop cannot jump over pieces"
      (let [b (assoc-in board [4 4 :piece] {:color :white :type :pawn})]
        (is (not (is-path-open? b :white [0 0] [7 7])))
        (is (not (is-path-open? b :black [0 0] [7 7])))))
    (testing "When opponents piece is at new position, bishop can capture it"
      (let [b (assoc-in board [4 4 :piece] {:color :white :type :pawn})]
        (is (is-path-open? b :black [0 0] [4 4]))))
    (testing "When players own piece is at new position, bishop cannot move there"
      (let [b (assoc-in board [4 4 :piece] {:color :white :type :pawn})]
        (is (not (is-path-open? b :white [0 0] [4 4])))))))

(deftest testing-all-bishop-move-rules
  (let [board (assoc-in (board/create-empty-board) [2 2 :piece] {:color :white :type :queen})]
    (testing "Cannot move to the same square"
      (is (not (allowed-move? board :white [4 4] [4 4]))))
    (testing "Must move inside board"
      (is (not (allowed-move? board :white [0 0] [8 8])))
      (is (not (allowed-move? board :white [0 0] [-1 -1]))))
    (testing "Can only move diagonally"
      (is (not (allowed-move? board :white [0 0] [7 7]))))
    (testing "Can move when path is not blocked"
      (is (allowed-move? board :white [6 6] [3 3])))
    (testing "Can not move when path is blocked"
      (is (not (allowed-move? board :white [7 7] [0 0]))))
    (testing "Can move when last square has opponent on it"
      (is (allowed-move? board :black [0 0] [2 2])))
    (testing "But not when last square has your own piece on it"
      (is (not (allowed-move? board :white [0 0] [2 2]))))))