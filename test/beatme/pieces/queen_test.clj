(ns beatme.pieces.queen-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.queen :refer [move-allowed?]]
            [beatme.board :as b]))

(deftest is-move-allowed-test
  (let [board (b/create-empty-board)]
    (testing "All diagonal moves within board are ok"
      (is (move-allowed? board :white [3 3] [0 0]))
      (is (move-allowed? board :white [3 3] [7 7]))
      (is (move-allowed? board :white [3 3] [1 5])))
    (testing "All straight moves within board are ok"
      (is (move-allowed? board :white [3 3] [0 3]))
      (is (move-allowed? board :white [3 3] [3 0])))
    (testing "Queens are not horses"
      (is (not (move-allowed? board :white [3 3] [2 1])))
      (is (not (move-allowed? board :white [3 3] [5 4]))))
    (testing "Queens cannot jump over opponents pieces"
      (is (not (move-allowed? (assoc-in board [1 3 :piece] {:color :black :type :pawn})
                              :white [3 3] [0 3]))))
    (testing "Queens cannot jump over its own pieces"
      (is (not (move-allowed? (assoc-in board [2 2 :piece] {:color :white :type :pawn})
                              :white [3 3] [1 1]))))
    (testing "Queens cannot capture its own pieces"
      (is (not (move-allowed? (assoc-in board [1 1 :piece] {:color :white :type :pawn})
                              :white [3 3] [1 1]))))
    (testing "Queens are allowed to capture opponents piece"
      (is (move-allowed? (assoc-in board [1 1 :piece] {:color :black :type :pawn})
                         :white [3 3] [1 1])))))
