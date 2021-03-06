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
  (around [it]
    (with-out-str (it)))

  (describe "calculate points"
    (it "returns 0 for tie board"
      (should= 0
        (calculate-points ["X" "X" "O" "O" "O" "X" "X" "X" "O"] "O" "X" 1)))

    (it "returns 9 for boards where X is the winner and depth is 1"
      (should= 9
        (calculate-points ["X" "X" "X" "O" "O" "X" "X" "X" "O"] "O" "X" 1)))

    (it "returns -9 for boards where O is the winner and depth is 1"
      (should= -9
        (calculate-points ["X" "X" "O" "O" "O" "O" "X" "X" "O"] "O" "X" 1)))
  )
  (describe "calculate points"
    (it "returns -9 if x's turn and x wins"
      (should= 9
        (determine-points-for-winner "X" 1)))

    (it "returns 10 if x's turn and x loses"
      (should= -9
        (determine-points-for-winner "O" 1)))

    (it "returns 10 if o's turn and o wins"
      (should= -9
        (determine-points-for-winner "O" 1)))

    (it "returns -10 if o's turn and o loses"
      (should= 9
        (determine-points-for-winner "X" 1)))
  )
  (describe "minimax-move"
    (it "1 returns blocking move if no win is possible X on top"
      (should= 2
        (minimax-move [ "X" "X" ""
                        "" "" ""
                        "" "" "O"] "O" "X")))

    (it "2 returns blocking move if no win is possible O on top"
      (should= 6
        (minimax-move [ "O" "" ""
                        "" "" ""
                        "" "X" "X"] "O" "X")))

    (it "3 returns win rather than block when possible X on top"
      (should= 6
        (minimax-move [ "X" "X" ""
                        "" "" ""
                        "" "O" "O"] "O" "X")))

    (it "4 returns win rather than block when possible O on top"
      (should= 1
        (minimax-move [ "O" "" "O"
                        "" "" ""
                        "X" "" "X"] "O" "X")))

    (it "5 returns blocking move if no win is possible"
      (should= 8
        (minimax-move [ "X" "O" "O"
                        "" "X" ""
                        "" "" ""] "O" "X")))

    (it "6 returns blocking move if no win is possible"
      (should= 6
        (minimax-move [ "O" "O" "X"
                        "" "X" ""
                        "" "" ""] "O" "X")))

    (it "7 returns blocking move if no win is possible"
      (should= 6
        (minimax-move [ "O" "" "X"
                        "" "X" ""
                        "" "" ""] "O" "X")))

    (it "8 returns blocking move if no win is possible"
      (should= 0
        (minimax-move [ "" "" ""
                        "" "X" "O"
                        "" "" "X"] "O" "X")))

    (it "9 returns blocking move"
      (should= 2
        (minimax-move [ "O" "O" ""
                        "" "" ""
                        "" "" "X"] "X" "O")))

    (it "10 returns blocking move"
      (should= 6
        (minimax-move [ "X" "" ""
                        "" "" ""
                        "" "O" "O"] "X" "O")))

    (it "11 returns winning move"
      (should= 6
        (minimax-move [ "O" "O" ""
                        "" "" ""
                        "" "X" "X"] "X" "O")))

    (it "12 returns winning move"
      (should= 1
        (minimax-move [ "X" "" "X"
                        "" "" ""
                        "O" "" "O"] "X" "O")))

    (it "13 returns blocking move"
      (should= 8
        (minimax-move [ "O" "X" "X"
                        "" "O" ""
                        "" "" ""] "X" "O")))

    (it "14 returns blocking move"
      (should= 6
        (minimax-move [ "X" "X" "O"
                        "" "O" ""
                        "" "" ""] "X" "O")))

    (it "15 returns blocking move"
      (should= 6
        (minimax-move [ "X" "" "O"
                        "" "O" ""
                        "" "" ""] "X" "O")))

    (it "16 returns blocking move"
      (should= 0
        (minimax-move [ "" "" ""
                        "" "O" "X"
                        "" "" "O"] "X" "O")))
  )
)
