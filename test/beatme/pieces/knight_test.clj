(ns beatme.pieces.knight-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.knight :refer :all]
            [beatme.board :as b]
            [beatme.pieces :as p]))

(deftest knight-movement-on-empty-bord-test
  (testing "Find all legal moves in the middle of the board"
    (let [board (b/create-empty-board)]
      (is (= 8 (count (find-all-legal-moves board (b/to-position "D4")))))
      (is (= (b/to-positions "C6" "E6" "B5" "F5" "B3" "F3" "C2" "E2")
             (set (find-all-legal-moves board (b/to-position "D4")))))))
  (testing "In the corner there are only two legal moves"
    (let [board (b/create-empty-board)]
      (is (= 2 (count (find-all-legal-moves board (b/to-position "A8")))))
      (is (= (b/to-positions "B6" "C7") (set (find-all-legal-moves board (b/to-position "A8"))))))))

(deftest knight-captures
  (testing "Should be able to capture opponent"
    (let [board (-> (b/create-empty-board)
                    (b/set-piece-with-notation "D4" (+ p/white p/knight))
                    (b/set-piece-with-notation "E6" (+ p/black p/knight))
                    (b/set-piece-with-notation "G7" (+ p/black p/knight)))]
      (is (= 8 (count (find-all-legal-moves board (b/to-position "D4")))))
      (is (= (b/to-positions "C6" "E6" "B5" "F5" "B3" "F3" "C2" "E2")
             (set (find-all-legal-moves board (b/to-position "D4")))))
      (testing "But cannot capture friendly piece on E6"
        (is (= (b/to-positions "E8" "F5" "H5") (set (find-all-legal-moves board (b/to-position "G7")))))))))