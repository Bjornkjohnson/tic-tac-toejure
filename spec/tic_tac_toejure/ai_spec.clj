(ns tic-tac-toejure.ai-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.ai :refer :all])
  (:use [tic-tac-toejure.board_analysis :only [get-winner, game-over?]]))

(def empty-board (vec (repeat 9 "")))

(describe "Random Ai"
  (describe "random-move"
    (it "returns a string"
      (should= java.lang.String (type (random-move))))

    (it "returns a string that can be converted to an integer"
      (should-not-throw
        (Integer/parseInt (random-move))))))

(describe "Minimax Ai"
  ; (desbribe "calculate points"
  ;   (it "returns ")
  ; )
  (describe "minimax-move"
    (it "returns blocking move if no win is possible"
      (should= 2
        (minimax-move ["X" "X" "" "" "" "" "" "" "O"] "O" "X")))

    (it "returns 0 for boards where no winner"
      (should= 0
        (score ["X" "X" "O" "O" "O" "X" "X" "X" "O"] "O" "X")
      )
    )
    (it "returns win rather than block when possible"
      (should= 6
        (minimax-move ["X" "X" "" "" "" "" "" "O" "O"] "O" "X")
      )
    )
  )
)
