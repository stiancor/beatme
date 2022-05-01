(ns beatme.pieces.pawn-test
  (:require [clojure.test :refer :all])
  (:require [beatme.pieces.pawn :as pawn]
            [beatme.board :as b]
            [beatme.pieces :as p]))

(defn get-operator [turn]
  (if (= :w turn) - +))

(deftest capture-left
  (let [game {:board (-> (b/create-empty-board)
                         (b/set-piece-with-notation "C2" (+ p/white p/pawn))
                         (b/set-piece-with-notation "B4" (+ p/white p/pawn))
                         (b/set-piece-with-notation "A5" (+ p/black p/queen)))
              :turn :w}]
    (testing "Nothing to capture returns no move"
      (is (not (pawn/can-capture-left? game (b/to-position "C2") (get-operator (:turn game))))))
    (testing "Opponents piece can be captured"
      (is (pawn/can-capture-left? game (b/to-position "B4") (get-operator (:turn game)))))))

(deftest capture-right
  (let [game {:board (-> (b/create-empty-board)
                         (b/set-piece-with-notation "F4" (+ p/white p/pawn))
                         (b/set-piece-with-notation "G5" (+ p/black p/queen)))
              :turn :w}]
    (testing "Nothing to capture returns no move"
      (is (not (pawn/can-capture-right? game (b/to-position "B3") (get-operator (:turn game))))))
    (testing "Opponents piece can be captured"
      (is (pawn/can-capture-right? game (b/to-position "F4") (get-operator (:turn game)))))))

(deftest find-all-legal-moves-for-pawn
  (let [game {:board (-> (b/create-empty-board)
                         (b/set-piece-with-notation "A2" (+ p/white p/pawn))
                         (b/set-piece-with-notation "G2" (+ p/white p/pawn))
                         (b/set-piece-with-notation "G5" (+ p/black p/queen))
                         (b/set-piece-with-notation "F3" (+ p/black p/pawn))
                         (b/set-piece-with-notation "H3" (+ p/black p/rook))
                         (b/set-piece-with-notation "A8" (+ p/white p/queen)))
              :turn :w}]
    (testing "When pawn on second row with no captures it should have two legal moves"
      (is (= (b/to-positions "A3" "A4") (set (pawn/find-all-legal-moves game (b/to-position "A2"))))))
    (testing "Pawn not on second row with no capture opportunities should have only one legal move"
      (is (= [(b/to-position "B4")] (pawn/find-all-legal-moves game (b/to-position "B3"))))
      (is (= [(b/to-position "C5")] (pawn/find-all-legal-moves game (b/to-position "C4"))))
      (is (= [(b/to-position "D6")] (pawn/find-all-legal-moves game (b/to-position "D5"))))
      (is (= [(b/to-position "E7")] (pawn/find-all-legal-moves game (b/to-position "E6")))))
    (testing "Pawn with one advance move and one capture move to the left returns both moves"
      (is (= [(b/to-position "F2") (b/to-position "G2")] (pawn/find-all-legal-moves (assoc game :turn :b) (b/to-position "F3")))))
    (testing "Piece with all options returns all possible moves"
      (is (= (set [(b/to-position "G3") (b/to-position "G4") (b/to-position "F3") (b/to-position "H3")])
             (set (pawn/find-all-legal-moves game (b/to-position "G2"))))))
    (testing "Piece that cannot advance and with no captures has no moves"
      (is (empty? (pawn/find-all-legal-moves game (b/to-position "A7")))))))

(deftest verifying-more-pawn-moves-with-black
  (let [game {:board (-> (b/create-empty-board)
                         (b/set-piece-with-notation "B7" (+ p/black p/pawn))
                         (b/set-piece-with-notation "D7" (+ p/black p/pawn))
                         (b/set-piece-with-notation "H6" (+ p/black p/pawn))
                         (b/set-piece-with-notation "A6" (+ p/white p/pawn))
                         (b/set-piece-with-notation "C6" (+ p/white p/pawn)))
              :turn :b}]
    (testing "Find all captures and advances"
      (is (= (set [(b/to-position "A6") (b/to-position "C6") (b/to-position "B6") (b/to-position "B5")])
             (set (pawn/find-all-legal-moves game (b/to-position "B7"))))))
    (testing "Capture only to the right and normal advances"
      (is (= (set [(b/to-position "C6") (b/to-position "D6") (b/to-position "D5")])
             (set (pawn/find-all-legal-moves game (b/to-position "D7"))))))
    (testing "Only one move available"
      (is (= [(b/to-position "H5")]
             (pawn/find-all-legal-moves game (b/to-position "H6")))))))

(deftest en-pessant-works
  (testing "En-pessant should work"
    (let [game {:board (-> (b/create-empty-board)
                           (b/set-piece-with-notation "B5" (+ p/white p/pawn))
                           (b/set-piece-with-notation "A5" (+ p/black p/pawn))
                           (b/set-piece-with-notation "C5" (+ p/black p/pawn)))
                :turn :w
                :en-pessant (b/to-position "C6")}]
      (is (= (set [(b/to-position "C6") (b/to-position "B6")])
             (set (pawn/find-all-legal-moves game (b/to-position "B5"))))))))
