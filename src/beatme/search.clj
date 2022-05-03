(ns beatme.search
  (:require [beatme.pieces :as p]
            [beatme.pieces.pawn :as pawn]
            [beatme.pieces.bishop :as bishop]
            [beatme.pieces.knight :as knight]
            [beatme.pieces.rook :as rook]
            [beatme.pieces.queen :as queen]
            [beatme.pieces.king :as king]))

(defn find-all-moves [game square pos]
  (let [color (if (= :w (:turn game)) p/white p/black)
        piece (- square color)]
    (cond
      (= p/pawn piece) (pawn/find-all-legal-moves game pos)
      (= p/bishop piece) (bishop/find-all-legal-moves (:board game) pos)
      (= p/knight piece) (knight/find-all-legal-moves (:board game) pos)
      (= p/rook piece) (rook/find-all-legal-moves (:board game) pos)
      (= p/queen piece) (queen/find-all-legal-moves (:board game) pos)
      (= p/king piece) (king/find-all-legal-moves game pos))))

(defn search [game depth max-depth]
  (if (< depth max-depth)
    (map-indexed (fn [idx square]
                   (cond (= square p/none) nil
                         (and (= (:turn game) :w) (not (p/is-black? square))) (find-all-moves game square idx)
                         (and (= (:turn game) :b) (p/is-white? square)) (find-all-moves game square idx)
                         :else nil)) (:board game))
    game))
