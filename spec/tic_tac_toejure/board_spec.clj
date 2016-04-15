(ns tic-tac-toejure.board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toejure.board :refer :all]))


(describe "place-marker"
  (it "adds marker to space"
    (should= ["X" "" "" "" "" "" "" "" ""]
      (place-marker build-board 0 "X"))))

(describe "User Input Validations"

  (describe "is-valid-position"
    (it "does not match non-number strings"
      (should= false
        (is-valid-position? "beep")))

    (it "does not match if number not on board"
      (should= false
        (is-valid-position? "9")))

    (it "does not match floats"
      (should= false
        (is-valid-position? "2.3")))

    (it "returns true if int between 0 and 8"
      (should= true
        (is-valid-position? "4"))))

  (describe "is-position-available?"
    (it "returns true if position is available"
      (should= true
        (is-position-available? build-board "0")))

    (it "returns false if position is taken"
      (should= false
        (is-position-available? (vec (repeat 9 "x")) "0")))

    (it "returns false if position is not on board"
      (should= false
        (is-position-available? build-board "10"))))

  (describe "input-is-valid?"

    (it "returns true for if valid and available position"
      (with-redefs [is-valid-position? (fn [_] true)]
        (with-redefs [is-position-available? (fn [& _] true)]
          (should= true
            (input-is-valid? build-board "something valid like 8")))))

    (it "return false for invalid but available position "
      (with-redefs [is-valid-position? (fn [_] false)]
        (with-redefs [is-position-available? (fn [& _] true)]
          (should= false
            (input-is-valid? build-board "random words like tune yards")))))

    (it "returns false for invalid and unavailable position"
     (with-redefs [is-valid-position? (fn [_] false)]
       (with-redefs [is-position-available? (fn [& _] false)]
          (should= false
            (input-is-valid? build-board "soumething out of bounds like 9")))))

    (it "returns false for positions that are taken"
      (with-redefs [is-valid-position? (fn [_] true)]
        (with-redefs [is-position-available? (fn [& _] false)]
          (should= false
            (input-is-valid? build-board "valid index like 0 but for a full board")))))))


