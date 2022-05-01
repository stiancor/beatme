(ns beatme.pieces.bishop-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.bishop :refer :all]
            [beatme.board :as board]
            [beatme.pieces :as p]
            [beatme.board :as b]))

(deftest find-all-legal-moves-test
  (testing "Bishop roaming free in the middle of the board with nobody to stop it"
    (is (= (b/to-positions "B2" "A1" "D4" "E5" "F6" "G7" "H8" "B4" "A5" "D2" "E1")
           (set (find-all-legal-moves (board/create-empty-board)
                                      (board/to-position "C3"))))))
  (testing "Bishop in the middle next to opponent captures but cannot jump over"
    (is (= (b/to-positions "B2" "A1" "D4" "B4" "A5" "D2" "E1")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "D4" (+ p/black p/pawn)))
                                      (board/to-position "C3"))))))
  (testing "Bishop in the middle next to friendly cannot capture or jump over"
    (is (= (b/to-positions "B2" "A1" "B4" "A5" "D2" "E1")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "C3" (+ p/white p/bishop))
                                          (board/set-piece-with-notation "D4" (+ p/white p/pawn)))
                                      (board/to-position "C3"))))))
  (testing "Bishop at the end of the board"
    (is (= (b/to-positions "B3" "C2" "D1" "B5" "C6" "D7" "E8")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "A4" (+ p/white p/bishop)))
                                      (board/to-position "A4"))))))
  (testing "Partly blocked bishop in the corner only has one move"
    (is (= (b/to-positions "B1")
           (set (find-all-legal-moves (-> (board/create-empty-board)
                                          (board/set-piece-with-notation "A2" (+ p/white p/bishop))
                                          (board/set-piece-with-notation "B3" (+ p/white p/pawn)))
                                      (board/to-position "A2"))))))
  (testing "Completely blocked bishop in the opposite corner has no moves"
    (is (empty?
          (find-all-legal-moves (-> (board/create-empty-board)
                                    (board/set-piece-with-notation "G8" (+ p/white p/bishop))
                                    (board/set-piece-with-notation "F7" (+ p/white p/pawn))
                                    (board/set-piece-with-notation "H7" (+ p/white p/pawn)))
                                (board/to-position "G8"))))))