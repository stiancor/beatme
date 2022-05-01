(ns beatme.pieces.rook
  (:require [beatme.pieces.sliding-piece :as sp]))

(def directions [{:dir :north :offset -8}
                 {:dir :west :offset -1}
                 {:dir :east :offset 1}
                 {:dir :south :offset 8}])

(defn find-all-legal-moves [board pos]
  (sp/find-all-legal-moves board pos directions))