(ns beatme.pieces.bishop
  (:require [beatme.pieces.sliding-piece :as sp]))

(def directions [{:dir :north-west :offset -9}
                 {:dir :north-east :offset -7}
                 {:dir :south-east :offset 9}
                 {:dir :south-west :offset 7}])

(defn find-all-legal-moves [board pos]
  (sp/find-all-legal-moves board pos directions))
                                                                            