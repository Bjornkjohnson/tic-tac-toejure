(ns tic-tac-toejure.core
  (:require [tic-tac-toejure.ui :refer :all]
            [tic-tac-toejure.ai :refer :all]
            [tic-tac-toejure.board_analysis :refer :all]))

(def build-board
  (vec (repeat 9 "")))

(def human-player
  {:marker "X" :move-getter get-player-move :name "Human Player"})

(defn computer-player [strategy]
  {:marker "O" :move-getter strategy :name "Computer Player"})

(defn player [marker strategy name]
  {:marker marker :move-getter strategy :name name})

(defn as-int [input]
  (Integer/parseInt input))

(defn is-valid-position? [user-input]
  (boolean (re-matches #"[0-8]" user-input)))

(defn is-position-available? [board user-input]
  (= "" (get board (as-int user-input))))

(defn input-is-valid? [board user-input]
  (and (is-valid-position? user-input) (is-position-available? board user-input)))

(defn get-valid-move
  ([board move-getter]
    (get-valid-move board move-getter (move-getter)))
  ([board move-getter move]
    (if (input-is-valid? board move)
      (as-int move)
      (recur board move-getter (move-getter)))))

(defn take-turn [board players]
  (let [current-player (first players)
        next-player (second players)
        move
        (if(= (current-player :move-getter) get-player-move)
            (get-valid-move board (current-player :move-getter) )
            ( (current-player :move-getter) board (current-player :marker) (next-player :marker) ) ) ]
        (place-marker board move (current-player :marker))))

(defn play [board players]
  (print-board board)
  (let [game-state (analyze-game-state board (map :marker players))]
    (if (game-state :game-over)
      (announce-game-over (game-state :winner))
      (let [next-board (take-turn board players)]
        (recur next-board (reverse players))))))

(defn -main [& args]
  (let [players (vector (player "X" get-player-move "Hugh Man") (player "O" minimax-move "Computer Guy"))]
    (play build-board players)))
