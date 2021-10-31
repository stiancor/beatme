(ns beatme.board-test
  (:require [clojure.test :refer :all])
  (:require [beatme.board :as board]))

(deftest define-board-test
  (let [board (board/create-empty)]
    (testing "is 8 by 8 matrix"
      (is (= 8 (count board)))
      (doall
        (for [row board]
          (is (= 8 (count row))))))
    (testing "Checking a subset of the generated cells"
      (let [posx0y0 (get-in board [0 0])]
        (is (= (:x posx0y0) 1))
        (is (= (:y posx0y0) "A")))
      (let [posx7y7 (get-in board [7 7])]
        (is (= (:x posx7y7) 8))
        (is (= (:y posx7y7) "H")))
      (let [posx3y2 (get-in board [3 2])]
        (is (= (:x posx3y2) 4))
        (is (= (:y posx3y2) "C")))
      (let [posx5y6 (get-in board [5 6])]
        (is (= (:x posx5y6) 6))
        (is (= (:y posx5y6) "G"))))))

(deftest check-if-position-inside-board
  (let [board (board/create-empty)]
    (is (board/is-inside-board? [0 0] board))
    (is (board/is-inside-board? [7 7] board))
    (is (board/is-inside-board? [0 7] board))
    (is (board/is-inside-board? [7 0] board))
    (is (board/is-inside-board? [3 3] board))
    (is (board/is-inside-board? [5 6] board))
    (is (not (board/is-inside-board? [0 8] board)))
    (is (not (board/is-inside-board? [8 0] board)))
    (is (not (board/is-inside-board? [-1 0] board)))
    (is (not (board/is-inside-board? [0 -1] board)))
    (is (not (board/is-inside-board? [7 15] board)))))
