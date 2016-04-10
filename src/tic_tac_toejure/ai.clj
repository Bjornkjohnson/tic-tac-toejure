(ns tic-tac-toejure.ai
  (:require [tic-tac-toejure.board_analysis :refer :all]
            [tic-tac-toejure.ui :refer :all]))


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
    (- 10 depth)
  )
)

(defn calculate-points [board own-marker opponent-marker depth]
  (let [winning-marker (get-winner board (vector own-marker opponent-marker))]
    (if (false? winning-marker)
      0
      (if (= "O" own-marker)
        (calculate-points-o winning-marker depth)
        (calculate-points-x winning-marker depth)
      )
    )
  )
)

(defn score [board own-marker opponent-marker depth]
  (if (game-over? board (vector own-marker opponent-marker))
    (calculate-points board own-marker opponent-marker depth)
    (let [moves (get-all-spaces-for board "")
          scores (map #(score (place-marker board % own-marker)
                      opponent-marker own-marker (inc depth)) moves)]
                      (if (= own-marker "X")
                        (apply max scores)
                        (apply min scores)
                      )
    )
  )
)

(defn minimax-move [board own-marker opponent-marker]
  (print-it computer-thinking-message)
  (let [moves (get-all-spaces-for board "")
        scores (map #(score (place-marker board % own-marker)
                      opponent-marker own-marker 1) moves)
        scored-moves (zipmap moves scores)]
        ( if (= own-marker "X")
          (first (first (sort-by val > scored-moves)))
          (first (first (sort-by val < scored-moves)))
        )
  )
)

