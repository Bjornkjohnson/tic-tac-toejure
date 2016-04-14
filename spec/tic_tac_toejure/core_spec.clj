(ns tic-tac-toejure.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.core :refer :all]
            [tic-tac-toejure.ui :refer :all]
            [tic-tac-toejure.ai :refer :all]))

(def test-players (vector human-player (computer-player random-move)))

(describe "Game End Conditions"
  (it "game ends if board is full"
    (should= true
      (.contains (with-out-str
        (play (vec (repeat 9 "X")) test-players)) "Game Over!")))

  (let [board ["O" "O" "O" "" "" "" "" "" ""]]
    (it "ends when a player has won"
      (should= true
        (.contains (with-out-str
          (play board test-players)) "Game Over!")))

    (it "announces winning player"
      (should= true
        (.contains (with-out-str
          (play board test-players)) "O wins!")))))
