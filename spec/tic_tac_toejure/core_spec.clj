(ns tic-tac-toejure.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.core :refer :all]
            [tic-tac-toejure.ui :refer :all]
            [tic-tac-toejure.ai :refer :all]))

(def test-human (player "X" get-player-move "Hugh Man"))

(def test-computer (player "O" minimax-move "Com Putor"))

(def test-players (vector test-human test-computer))

(describe "player-is-human?"
  (it "returns true if player is human"
    (should= true
      (player-is-human? test-human)))

  (it "returns false if player is not human"
    (should= false
      (player-is-human? test-computer))))

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
