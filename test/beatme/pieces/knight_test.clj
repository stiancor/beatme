(ns beatme.pieces.knight-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.knight :refer :all]
            [beatme.board :as b]))

(deftest knight-movement-on-empty-bord-test
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

(deftest knight-captures
  (testing "Should be able to capture opponent"
    (let [board (-> (b/create-empty-board)
                    (assoc-in [2 1 :piece] {:color :white :type :pawn})
                    (assoc-in [5 5 :piece] {:color :white :type :queen})
                    (assoc-in [4 1 :piece] {:color :black :type :pawn}))]
      (is (allowed-move? board :black [3 3] [2 1]))
      (testing "Now within the grasp of the knight"
        (is (not (allowed-move? board :black [3 3] [5 5]))))
      (testing "Cannot capture your own piece"
        (is (allowed-move? board :white [3 3] [4 1]))))))