(ns beatme.pieces.queen-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.queen :refer [find-all-legal-moves]]
            [beatme.board :as board]
            [beatme.pieces :as p]
            [beatme.board :as b]))

(deftest find-all-legal-moves-test
  (testing "Queen roaming free in the middle of the board with nobody to stop it"
    (is (= (b/to-positions "A1" "B2" "D4" "E5" "F6" "G7" "H8" "D2" "E1" "B4" "A5" "C2" "C1" "C4" "C5" "C6" "C7" "C8" "B3" "A3" "D3" "E3" "F3" "G3" "H3")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "C3" (+ p/white p/queen)))
                                      (board/to-position "C3"))))))
  (testing "queen in the middle next to opponent captures but cannot jump over"
    (is (= (b/to-positions "A1" "B2" "C4" "D4" "D2" "E1" "B4" "A5" "C2" "C1" "B3" "A3" "D3" "E3" "F3" "G3" "H3")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "C3" (+ p/white p/queen))
                                          (board/set-piece-with-notation "D4" (+ p/black p/pawn))
                                          (board/set-piece-with-notation "C4" (+ p/black p/pawn)))
                                      (board/to-position "C3"))))))
  (testing "Queen in the middle next to friendly cannot capture or jump over"
    (is (= (b/to-positions "A1" "B2" "D2" "E1" "B4" "A5" "C2" "C1" "B3" "A3" "D3" "E3" "F3" "G3" "H3")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "C3" (+ p/white p/queen))
                                          (board/set-piece-with-notation "D4" (+ p/white p/pawn))
                                          (board/set-piece-with-notation "C4" (+ p/white p/pawn)))
                                      (board/to-position "C3"))))))
  (testing "Queen at the end of the board"
    (is (= (b/to-positions "A1" "A2" "A3" "A5" "A6" "A7" "A8" "B4" "C4" "D4" "E4" "F4" "G4" "H4" "B3" "C2" "D1" "B5" "C6" "D7" "E8")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "A4" (+ p/white p/queen)))
                                      (board/to-position "A4"))))))
  (testing "Partly blocked queen in the corner only has two moves"
    (is (= (b/to-positions "A1" "B1")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "A2" (+ p/white p/queen))
                                          (board/set-piece-with-notation "B3" (+ p/white p/pawn))
                                          (board/set-piece-with-notation "A3" (+ p/white p/pawn))
                                          (board/set-piece-with-notation "B2" (+ p/white p/pawn)))
                                      (board/to-position "A2"))))))
  (testing "Completely blocked queen in the opposite corner has no moves"
    (is (empty?
          (find-all-legal-moves (-> (board/create-empty-board)
                                    (board/set-piece-with-notation "H8" (+ p/white p/queen))
                                    (board/set-piece-with-notation "G8" (+ p/white p/pawn))
                                    (board/set-piece-with-notation "G7" (+ p/white p/pawn))
                                    (board/set-piece-with-notation "H7" (+ p/white p/pawn)))
                                (board/to-position "H8"))))))
