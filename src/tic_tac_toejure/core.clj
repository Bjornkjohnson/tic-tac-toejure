(ns tic-tac-toejure.core
  (:require [tic-tac-toejure.ui :refer :all]
            [tic-tac-toejure.ai :refer :all]
            [tic-tac-toejure.board :refer :all]
            [tic-tac-toejure.board_analysis :refer :all]))

(def human-player
  {:marker "X" :move-getter get-player-move :name "Human Player"})

(defn computer-player [strategy]
  {:marker "O" :move-getter strategy :name "Computer Player"})

(defn player [marker strategy name]
  {:marker marker :move-getter strategy :name name})

(defn get-valid-move
  ([board move-getter]
    (get-valid-move board move-getter (move-getter)))
  ([board move-getter move]
    (if (input-is-valid? board move)
      (as-int move)
      (recur board move-getter (move-getter)))))

(defn player-is-human? [player]
    (= (player :move-getter) get-player-move)
)

(defn get-human-move [board current-player]
  (get-valid-move board (current-player :move-getter))
)

(defn get-computer-move [board current-player next-player]
  ( (current-player :move-getter) board (current-player :marker) (next-player :marker) )
)

(defn get-move-from-current-player [board current-player next-player]
  (if(player-is-human? current-player)
      (get-human-move board current-player)
      (get-computer-move board current-player next-player))
)

(defn take-turn [board players]
  (let [move (get-move-from-current-player board (first players) (second players))]
        (place-marker board move ((first players) :marker))))

(defn play [board players]
  (print-board board)
  (let [game-state (analyze-game-state board (map :marker players))]
    (if (game-state :game-over)
      (announce-game-over (game-state :winner))
      (let [next-board (take-turn board players)]
        (recur next-board (reverse players))))))

(defn -main [& args]
  (let [players (vector (player "X" minimax-move "Hugh Man") (player "O" minimax-move "Computer Guy"))]
    (play build-board players)
    (shutdown-agents)))

