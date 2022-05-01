(ns beatme.board-test
  (:require [clojure.test :refer :all])
  (:require [beatme.board :as board]
            [beatme.fen-parser :as fen-parser]
            [beatme.pieces :as p]))

(deftest define-board-test
  (let [board (:board (fen-parser/init-starting-position))]
    (testing "vector of 64 squares"
      (is (= 64 (count board))))
    (testing "Checking a subset of the generated cells"
      (is (= (get board (board/to-position "A1")) (+ p/rook p/white)))
      (is (= (get board (board/to-position "H1")) (+ p/rook p/white)))
      (is (= (get board (board/to-position "E1")) (+ p/king p/white)))
      (is (= (get board (board/to-position "E8")) (+ p/king p/black))))))

(deftest check-if-position-inside-board
  (is (board/is-inside-board? 0))
  (is (board/is-inside-board? 32))
  (is (board/is-inside-board? 63))
  (is (not (board/is-inside-board? -1)))
  (is (not (board/is-inside-board? 64))))
