(ns tic-tac-toejure.ai
  (:require [tic-tac-toejure.board_analysis :refer :all]
            [tic-tac-toejure.ui :refer :all]))


(defn place-marker [board position marker]
  (assoc board position marker))

(defn random-move []
  (str (rand-int 9)))

(defn determine-points-for-winner [winning-marker depth]
  (if (= winning-marker "X")
    (- 10 depth)
    (+ -10 depth)))

(defn calculate-points [board own-marker opponent-marker depth]
  (let [winning-marker (get-winner board (vector own-marker opponent-marker))]
    (if (false? winning-marker)
      0
      (determine-points-for-winner winning-marker depth)
    )
  )
)

(defn extract-optimal-score [marker scores]
  (if (= marker "X")
    (apply max scores)
    (apply min scores)
  )
)

(defn score [board own-marker opponent-marker depth]
  (if (game-over? board (vector own-marker opponent-marker))
    (calculate-points board own-marker opponent-marker depth)
    (let [moves (get-all-spaces-for board "")
          scores (map #(score (place-marker board % own-marker)
                      opponent-marker own-marker (inc depth)) moves)]
          (extract-optimal-score own-marker scores)
    )
  )
)

(defn extract-optimal-move [marker scored-moves]
  ( if (= marker "X")
    (first (first (sort-by val > scored-moves)))
    (first (first (sort-by val < scored-moves)))
  )
)

(defn minimax-move [board own-marker opponent-marker]
  (print-it computer-thinking-message)
  (let [moves (get-all-spaces-for board "")
        scores (map #(score (place-marker board % own-marker)
                      opponent-marker own-marker 1) moves)
        scored-moves (zipmap moves scores)]
        (extract-optimal-move own-marker scored-moves)
  )
)

