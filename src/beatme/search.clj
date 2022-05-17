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

(defn can-castle-queenside? [game a-file-index]
  (when
    (and (:castle-queen-side? (get game (:turn game)))
         (= p/none (get-in game [:board (+ 1 a-file-index)]))
         (= p/none (get-in game [:board (+ 2 a-file-index)]))
         (= p/none (get-in game [:board (+ 3 a-file-index)])))
    :queenside-castle))

(defn can-castle-kingside? [game a-file-index]
  (when
    (and (:castle-king-side? (get game (:turn game)))
         (= p/none (get-in game [:board (+ 5 a-file-index)]))
         (= p/none (get-in game [:board (+ 6 a-file-index)])))
    :kingside-castle))

(defn find-castle-moves [game]
  (filterv some?
           [(can-castle-queenside? game (if (= :w (:turn game)) 56 0))
            (can-castle-kingside? game (if (= :w (:turn game)) 56 0))]))

(defn search [game depth max-depth]
  (if (< depth max-depth)
    (map-indexed (fn [idx square]
                   (cond (= square p/none) nil
                         (and (= (:turn game) :w) (not (p/is-black? square))) (find-all-moves game square idx)
                         (and (= (:turn game) :b) (p/is-white? square)) (find-all-moves game square idx)
                         :else nil)) (:board game))
    game))
