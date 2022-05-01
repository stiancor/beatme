(ns beatme.pieces.rook-test
  (:require [clojure.test :refer :all]
            [beatme.pieces.rook :refer [find-all-legal-moves]]
            [beatme.board :as board]
            [beatme.pieces :as p]
            [beatme.board :as b]))

(deftest find-all-legal-moves-test
  (testing "Rook roaming free in the middle of the board with nobody to stop it"
    (is (= (b/to-positions "B3" "A3" "D3" "E3" "F3" "G3" "H3" "C2" "C1" "C4" "C5" "C6" "C7" "C8")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "C3" (+ p/white p/rook)))
                                      (board/to-position "C3"))))))
  (testing "Rook in the middle next to opponent captures it but cannot jump over"
    (is (= (b/to-positions "B3" "A3" "D3" "E3" "F3" "G3" "H3" "C2" "C1" "C4")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "C3" (+ p/white p/rook))
                                          (board/set-piece-with-notation "C4" (+ p/black p/pawn)))
                                      (board/to-position "C3"))))))
  (testing "Rook in the middle next to friendly cannot capture or jump over"
    (is (= (b/to-positions "B3" "A3" "D3" "E3" "F3" "G3" "H3" "C2" "C1")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "C3" (+ p/white p/rook))
                                          (board/set-piece-with-notation "C4" (+ p/white p/pawn)))
                                      (board/to-position "C3"))))))
  (testing "Partly blocked rook in the corner only has one move"
    (is (= (b/to-positions "A1")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "A2" (+ p/white p/rook))
                                          (board/set-piece-with-notation "B2" (+ p/white p/pawn))
                                          (board/set-piece-with-notation "A3" (+ p/white p/pawn)))
                                      (board/to-position "A2"))))))
  (testing "Completely blocked rook in the opposite corner has no moves"
    (is (empty?
          (find-all-legal-moves (-> (board/create-empty-board)
                                    (board/set-piece-with-notation "H8" (+ p/white p/rook))
                                    (board/set-piece-with-notation "H7" (+ p/white p/pawn))
                                    (board/set-piece-with-notation "G8" (+ p/white p/pawn)))
                                (board/to-position "H8"))))))