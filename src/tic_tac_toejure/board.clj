(ns tic-tac-toejure.board)

(def build-board
  (vec (repeat 9 "")))

(defn place-marker [board position marker]
  (assoc board position marker))

(defn as-int [input]
  (Integer/parseInt input))

(defn is-valid-position? [user-input]
  (boolean (re-matches #"[0-8]" user-input)))

(defn is-position-available? [board user-input]
  (= "" (get board (as-int user-input))))

(defn input-is-valid? [board user-input]
  (and (is-valid-position? user-input) (is-position-available? board user-input)))
