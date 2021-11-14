(ns beatme.pieces.knight-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.knight :refer :all]))

(deftest knight-movement-test
  (testing "Test that the knight can move in any direction from the middle of the board."
    (is (= 8 (count (possible-moves [3 3]))))
    (is (is-possible-move? [3 3] [2 1]))
    (is (is-possible-move? [3 3] [2 5]))
    (is (is-possible-move? [3 3] [1 2]))
    (is (is-possible-move? [3 3] [1 4]))
    (is (is-possible-move? [3 3] [4 1]))
    (is (is-possible-move? [3 3] [4 5]))
    (is (is-possible-move? [3 3] [5 2]))
    (is (is-possible-move? [3 3] [5 4]))
    (is (not (is-possible-move? [3 3] [4 4])))
    (is (not (is-possible-move? [3 3] [6 2]))))
  (testing "In the corner there are only two legal moves"
    (is (= 2 (count (possible-moves [0 0]))))
    (is (is-possible-move? [0 0] [1 2]))
    (is (is-possible-move? [0 0] [2 1]))
    (is (not (is-possible-move? [0 0] [3 1])))))