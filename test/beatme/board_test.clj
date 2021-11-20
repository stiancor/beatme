(ns beatme.board-test
  (:require [clojure.test :refer :all])
  (:require [beatme.board :as board]
            [beatme.board :as b]))

(deftest define-board-test
  (let [board (board/create-empty-board)]
    (testing "is 8 by 8 matrix"
      (is (= 8 (count board)))
      (doall
        (for [row board]
          (is (= 8 (count row))))))
    (testing "Checking a subset of the generated cells"
      (let [posx0y0 (get-in board [0 0])]
        (is (= (:x posx0y0) "A"))
        (is (= (:y posx0y0) 8)))
      (let [posx7y7 (get-in board [7 7])]
        (is (= (:x posx7y7) "H"))
        (is (= (:y posx7y7) 1)))
      (let [posx3y2 (get-in board [3 2])]
        (is (= (:x posx3y2) "C"))
        (is (= (:y posx3y2) 5)))
      (let [posx5y6 (get-in board [5 6])]
        (is (= (:x posx5y6) "G"))
        (is (= (:y posx5y6) 3))))))

(deftest check-if-position-inside-board
  (is (board/is-inside-board? [0 0]))
  (is (board/is-inside-board? [7 7]))
  (is (board/is-inside-board? [0 7]))
  (is (board/is-inside-board? [7 0]))
  (is (board/is-inside-board? [3 3]))
  (is (board/is-inside-board? [5 6]))
  (is (not (board/is-inside-board? [0 8])))
  (is (not (board/is-inside-board? [8 0])))
  (is (not (board/is-inside-board? [-1 0])))
  (is (not (board/is-inside-board? [0 -1])))
  (is (not (board/is-inside-board? [7 15]))))

(deftest check-square-occupied
  (let [board-before (board/set-piece (board/create-empty-board) [0 2] {:type :pawn :color :white})]
    (testing "Check before moving piece"
      (is (board/square-occupied? board-before [0 2]))
      (is (nil? (board/square-occupied? board-before [0 3]))))
    (testing "After moving piece"
      (let [board-after (board/move-piece board-before [0 2] [0 3])]
        (is (nil? (board/square-occupied? board-after [0 2])))
        (is (board/square-occupied? board-after [0 3]))
        (is (= (board/get-piece board-before [0 2])
               (board/get-piece board-after [0 3])))))))

(deftest test-flip-board
  (let [flipped-board (board/flip-board (board/create-empty-board))]
    (is (= (get-in flipped-board [0 0]) {:x "H" :y 1}))
    (is (= (get-in flipped-board [7 7]) {:x "A" :y 8}))
    (is (= (get-in flipped-board [0 7]) {:x "A" :y 1}))
    (is (= (get-in flipped-board [7 0]) {:x "H" :y 8}))
    (is (= (get-in flipped-board [1 1]) {:x "G" :y 2}))
    (is (= (get-in flipped-board [5 3]) {:x "E" :y 6}))
    (is (= (get-in flipped-board [2 4]) {:x "D" :y 3}))))

(deftest test-available-square?
  (let [b (assoc-in (board/create-empty-board) [0 4 :piece] {:color :white :type :pawn})]
    (is (board/available-square? b :black [0 4] [0 4]))
    (is (not (board/available-square? b :white [0 4] [0 4])))
    (is (not (board/available-square? b :black [0 4] [0 5])))
    (is (not (board/available-square? b :white [0 4] [0 5])))))

(deftest find-players-pieces
  (testing "Verify 16 pieces each in opening"
    (is (= 16 (count (b/find-players-pieces (board/starting-position) :white))))
    (is (= 16 (count (b/find-players-pieces (board/starting-position) :black)))))
  (testing "No pieces on empty board"
    (is (empty? (b/find-players-pieces (board/create-empty-board) :white)))
    (is (empty? (b/find-players-pieces (board/create-empty-board) :black))))
  (testing "One piece is one piece"
    (is (= 1 (count (b/find-players-pieces
                      (board/set-piece (board/create-empty-board) [0 2] {:type :pawn :color :white})
                      :white))))))
