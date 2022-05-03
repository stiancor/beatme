(ns beatme.search-test
  (:require [clojure.test :refer :all]
            [beatme.search :refer [search]]
            [beatme.fen-parser :as fen-p]))

(deftest find-all-moves-test
  (testing "All possible moves for white is 20 after first turn"
    (is (= 20 (->> (search (fen-p/init-starting-position) 0 1)
                   (remove empty?)
                   (map count)
                   (apply +))))))
