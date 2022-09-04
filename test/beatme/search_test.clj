(ns beatme.search-test
  (:require [clojure.test :refer :all]
            [beatme.search :as search]
            [beatme.fen-parser :as fen-p]
            [beatme.board :as b]
            [beatme.pieces :as p]))

(deftest find-all-moves-test
  (testing "All possible moves for white is 20 on first turn"
    (is (= 20 (->> (search/search {:game (fen-p/init-starting-position)
                                   :depth 0
                                   :max-depth 1})
                   (remove empty?)
                   (map count)
                   (apply +))))))

(deftest find-all-moves-test
  (testing "All possible moves for black is 20 on first turn"
    (is (= 20 (->> (search/search {:game (assoc (fen-p/init-starting-position) :turn :b)
                                   :depth 0
                                   :max-depth 1})
                   (remove empty?)
                   (map count)
                   (apply +))))))

(deftest castle-test-white
  (testing "Can not castle in opening position"
    (is (empty? (search/find-castle-moves (fen-p/init-starting-position)))))
  (testing "White can castle king side"
    (is (= [:kingside-castle]
           (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                 (b/set-piece-with-notation "E1" (+ p/white p/king))
                                                 (b/set-piece-with-notation "H1" (+ p/white p/rook)))
                                      :turn :w
                                      :w {:castle-king-side? true}}))))
  (testing "White can castle queen side"
    (is (= [:queenside-castle]
           (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                 (b/set-piece-with-notation "E1" (+ p/white p/king))
                                                 (b/set-piece-with-notation "A1" (+ p/white p/rook)))
                                      :turn :w
                                      :w {:castle-queen-side? true}}))))
  (testing "White can castle both sides"
    (is (= [:queenside-castle :kingside-castle]
           (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                 (b/set-piece-with-notation "H1" (+ p/white p/rook))
                                                 (b/set-piece-with-notation "E1" (+ p/white p/king))
                                                 (b/set-piece-with-notation "A1" (+ p/white p/rook)))
                                      :turn :w
                                      :w {:castle-queen-side? true
                                          :castle-king-side? true}}))))
  (testing "White cannot castle when pieces are in the way"
    (is (empty? (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                      (b/set-piece-with-notation "H1" (+ p/white p/rook))
                                                      (b/set-piece-with-notation "G1" (+ p/white p/knight))
                                                      (b/set-piece-with-notation "E1" (+ p/white p/king))
                                                      (b/set-piece-with-notation "C1" (+ p/black p/bishop))
                                                      (b/set-piece-with-notation "A1" (+ p/white p/rook)))
                                           :turn :w
                                           :w {:castle-queen-side? true
                                               :castle-king-side? true}}))))
  (testing "White cannot castle when pieces have moved"
    (is (empty? (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                      (b/set-piece-with-notation "H1" (+ p/white p/rook))
                                                      (b/set-piece-with-notation "E1" (+ p/white p/king))
                                                      (b/set-piece-with-notation "A1" (+ p/white p/rook)))
                                           :turn :w
                                           :w {:castle-queen-side? false
                                               :castle-king-side? false}})))))

(deftest castle-test-black
  (testing "Black can castle king side"
    (is (= [:kingside-castle]
           (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                 (b/set-piece-with-notation "E8" (+ p/white p/king))
                                                 (b/set-piece-with-notation "H8" (+ p/white p/rook)))
                                      :turn :b
                                      :b {:castle-king-side? true}}))))
  (testing "Black can castle queen side"
    (is (= [:queenside-castle]
           (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                 (b/set-piece-with-notation "E8" (+ p/white p/king))
                                                 (b/set-piece-with-notation "A8" (+ p/white p/rook)))
                                      :turn :b
                                      :b {:castle-queen-side? true}}))))
  (testing "Black can castle both sides"
    (is (= [:queenside-castle :kingside-castle]
           (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                 (b/set-piece-with-notation "H8" (+ p/white p/rook))
                                                 (b/set-piece-with-notation "E8" (+ p/white p/king))
                                                 (b/set-piece-with-notation "A8" (+ p/white p/rook)))
                                      :turn :b
                                      :b {:castle-queen-side? true
                                          :castle-king-side? true}}))))
  (testing "Black cannot castle when pieces are in the way"
    (is (empty? (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                     (b/set-piece-with-notation "H8" (+ p/white p/rook))
                                                     (b/set-piece-with-notation "G8" (+ p/white p/knight))
                                                     (b/set-piece-with-notation "E8" (+ p/white p/king))
                                                     (b/set-piece-with-notation "C8" (+ p/black p/bishop))
                                                     (b/set-piece-with-notation "A8" (+ p/white p/rook)))
                                          :turn :b
                                          :b {:castle-queen-side? true
                                              :castle-king-side? true}}))))
  (testing "Black cannot castle when pieces have moved"
    (is (empty? (search/find-castle-moves {:board (-> (b/create-empty-board)
                                                      (b/set-piece-with-notation "H8" (+ p/white p/rook))
                                                      (b/set-piece-with-notation "E8" (+ p/white p/king))
                                                      (b/set-piece-with-notation "A8" (+ p/white p/rook)))
                                           :turn :w
                                           :w {:castle-queen-side? false
                                               :castle-king-side? false}})))))
