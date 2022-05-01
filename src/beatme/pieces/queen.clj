(ns beatme.pieces.queen
  (:require [beatme.pieces.sliding-piece :as sp]))

(def directions [{:dir :north-west :offset -9}
                 {:dir :north-east :offset -7}
                 {:dir :south-east :offset 9}
                 {:dir :south-west :offset 7}
                 {:dir :north :offset -8}
                 {:dir :east :offset 1}
                 {:dir :west :offset -1}
                 {:dir :south :offset 8}])

(defn find-all-legal-moves [board pos]
  (sp/find-all-legal-moves board pos directions))




