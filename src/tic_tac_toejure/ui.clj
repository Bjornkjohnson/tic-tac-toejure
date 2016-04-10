(ns tic-tac-toejure.ui)

(def get-player-move-message "Select your next move...")

(def computer-thinking-message "Computer Thinking...")

(def empty-space-view ":___")

(defn print-it [message]
  (println (apply str message)))

(defn prompt [message]
  (print-it message)
  (read-line))

(defn get-player-move []
  (prompt get-player-move-message))

(defn state-to-view-mapper [space-contents]
  (if (clojure.string/blank? (second space-contents))
    (str ":_" (str(first space-contents)) "_")
    (str ":_" (second space-contents) "_")))

(defn build-view [row]
  (map state-to-view-mapper row))

(defn print-board [board]
  (println "\033[2J\u001B[H")
  (dorun
    (map print-it
      (map build-view
        (partition 3
          (map-indexed vector board)
        )
      )
    )
  )
)


(defn announce-game-over [winner]
  (let [winner-name (if winner winner "Nobody")]
    (print-it (str "Game Over!\n" winner-name " wins!" ))))
