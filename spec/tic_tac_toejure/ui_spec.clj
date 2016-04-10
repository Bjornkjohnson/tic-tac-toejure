(ns tic-tac-toejure.ui-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.ui :refer :all]
            [tic-tac-toejure.core :refer :all]))

(def empty-board  (vec (repeat 9 "")))

(def empty-row (apply str (repeat 3 empty-space-view)))

(def empty-board-output-str "\033[2J\u001B[H\n:_0_:_1_:_2_\n:_3_:_4_:_5_\n:_6_:_7_:_8_\n")

(describe "Ui"
  (around [it]
    (with-out-str (it)))

  (describe "test prompt"
    (it "returns the input string it receives"
      (should= "X"
        (with-in-str "X"
          (prompt "X or O?"))))

    (it "outputs its args to stdout"
      (should= "X or O?\n"
       (with-out-str (with-in-str "O"
        (prompt "X or O?"))))))

  (describe "test print-board"
    (it "prints board to stdout"
      (should= empty-board-output-str
        (with-out-str (print-board empty-board)))))

  (describe "prompt user for position choice"
    (it "test gets user input"
      (with-redefs [prompt (fn [& _] "0")]
        (should= "0"
          (get-player-move)))))

  (describe "build-board-view"
    (it "builds board representation for empty board"
      (should= [":_0_" ":_1_" ":_2_"]
        (build-view [[0 ""] [1 ""] [2 ""]])
      )
    )
  )

  (describe "Game over messaging"
    (it "reports Winner's name if winner"
      (let [winner "X"]
        (should= "Game Over!\nX wins!\n"
          (with-out-str
            (announce-game-over winner)))))

    (it "reports that nobody won if winner is false"
      (let [winner false]
        (should= "Game Over!\nNobody wins!\n"
          (with-out-str
            (announce-game-over winner))))))
)
