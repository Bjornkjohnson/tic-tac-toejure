(ns tic-tac-toejure.ai
  (:require [tic-tac-toejure.board_analysis :refer :all]))


(defn place-marker [board position marker]
  (assoc board position marker))

(defn random-move []
  (str (rand-int 9)))

(defn calculate-points-x [winning-marker depth]
  (if (= winning-marker "X")
    (- 10 depth)
    (+ -10 depth)))


(defn calculate-points-o [winning-marker depth]
  (if (= winning-marker "O")
    (+ -10 depth)
    (- 10 depth)))

(defn calculate-points [winning-marker own-marker depth]
  (if (= "O" own-marker)
    (calculate-points-o winning-marker depth)
    (calculate-points-x winning-marker depth)))

(defn score [board own-marker opponent-marker depth]
  (if (game-over? board (vector own-marker opponent-marker))
      (let [winning-marker (get-winner board (vector own-marker opponent-marker))]
        (if (false? winning-marker)
          0
          (calculate-points winning-marker own-marker depth)))
      (let [new-depth (inc depth)
            scores (map #(score (place-marker board % own-marker) opponent-marker own-marker new-depth)
                        (get-all-spaces-for board ""))
            moves (get-all-spaces-for board "")
            scored-moves (zipmap moves scores)]
                        ; (println scored-moves)
                        (if (= own-marker "X")
                          (apply max scores)
                          (apply min scores))
                        )))

(defn minimax-move [board own-marker opponent-marker]
  (let [scores (map #(score (place-marker board % own-marker) opponent-marker own-marker  1)
                                    (get-all-spaces-for board ""))
        moves (get-all-spaces-for board "")
        scored-moves (zipmap moves scores)]
        (println scored-moves)
        (first (first (sort-by val < scored-moves)))))

