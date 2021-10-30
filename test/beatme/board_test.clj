(ns beatme.board-test
  (:require [clojure.test :refer :all])
  (:require [beatme.board :refer [define-board]]))

(deftest define-board-test
  (let [board (define-board)]
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
