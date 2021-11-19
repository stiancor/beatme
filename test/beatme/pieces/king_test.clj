(ns beatme.pieces.king-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.king :as k]
            [beatme.board :as b]))

(deftest possible-king-moves-test
  (let [board (-> (b/create-empty-board)
                  (assoc-in [2 2 :piece] {:color :white :type :pawn})
                  (assoc-in [6 7 :piece] {:color :black :type :pawn}))]
    (is (= 8 (count (k/possible-king-moves [4 4]))))
    (is (k/is-possible-move? [4 4] [3 3]))
    (is (k/is-possible-move? [4 4] [3 4]))
    (is (k/is-possible-move? [4 4] [3 5]))
    (is (k/is-possible-move? [4 4] [4 3]))
    (is (k/is-possible-move? [4 4] [4 5]))
    (is (k/is-possible-move? [4 4] [5 3]))
    (is (k/is-possible-move? [4 4] [5 4]))
    (is (k/is-possible-move? [4 4] [5 5]))
    (is (not (k/is-possible-move? [4 4] [5 6])))
    (is (not (k/is-possible-move? [4 4] [6 6])))
    (testing "Capturing opponent is allowed"
      (is (k/allowed-move? board :white [6 6] [6 7])))
    (testing "Capture your own pieces is not allowed"
      (is (not (k/allowed-move? board :white [3 3] [2 2]))))))
