(ns typer-tester.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [typer-tester.core-test]))

(doo-tests 'typer-tester.core-test)

