import java.util.Random;
import java.util.Scanner;

public class SolutionNQueen {
    private static int n = 0, variation = 0, currentStateHeuristic, heuristic;
    private static boolean breakloop = false, runAgain = false;
    static double percent = 0.0, failure = 0.0;
    private static int stepsToSolution = 0, avgSuccessSteps = 0, avgFailureSteps = 0;
    private static int avgrandomStart = 0, TotalavgrandomStart = 0;

    public static void main(String[] args) {
        int failed = 0, success = 0;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the number of Queens :");
            n = s.nextInt();
            if (n == 2 || n == 3) {
                System.out.println("Please enter a valid number. No solution possible for less than 4 queens.");
            } else {
                System.out.println("Please specify the variation in algorithm:");
                System.out.println("1) Hill Climbing Search");
                System.out.println("2) Hill climbing search with sideways move");
                System.out.println("3) Random restart hill climbing search without sideway ways");
                System.out.println("4) Random restart hill climbing search with sideway ways");
                variation = s.nextInt();
                if (variation == 2 || variation == 3 || variation == 1 || variation == 4) {
                    break;
                } else
                    System.out.println("Please enter valid option");
            }
        }
        for (int i = 0; i < 100; i++) {
            stepsToSolution = 0;
            avgrandomStart = 0;
            QueenState[] currentState = generateBoard();
            QueenState[] neighbourState = currentState;
            //printState(currentState);
            currentStateHeuristic = findHeuristic(currentState);
            if (variation == 2) {
                for (int l = 0; l < 100; l++) {
                    while (currentStateHeuristic != 0) {
                        neighbourState = nextBoard(neighbourState);
                        //if (runAgain == true) {
                        // runAgain = false;
                        // l = 0;
                        //}
                        if (breakloop == true) {
                            breakloop = false;

                            break;
                        } else {
                            currentStateHeuristic = heuristic;
                        }
                        stepsToSolution++;
                    }
                    if (currentStateHeuristic == 0) {
                        break;

                    }

                    System.out.println("moved " + l + "Queen");
                    currentState = moveOneQueenRandom(neighbourState);
                    heuristic = findHeuristic(currentState);
                }
                if (currentStateHeuristic != 0) {
                    System.out.println("solution not found");
                    stepsToSolution++;
                    System.out.println("Steps Needed : " + stepsToSolution);

                    failed++;
                    avgFailureSteps = avgFailureSteps + stepsToSolution;
                    System.out.println("Total Steps Needed : " + avgFailureSteps);
                    breakloop = false;
                }

            }else {
                while (currentStateHeuristic != 0) {
                    //  Get the next board
                    neighbourState = nextBoard(neighbourState);

                    if (breakloop == true) {

                        if (variation == 1) {

                            System.out.println("solution not found");
                            stepsToSolution++;
                            System.out.println("Steps Needed : " + stepsToSolution);

                            failed++;
                            avgFailureSteps = avgFailureSteps + stepsToSolution;
                            System.out.println("Total Steps Needed : " + avgFailureSteps);
                            breakloop = false;
                            break;
                        }


                    } else {
                        currentStateHeuristic = heuristic;
                    }
                    stepsToSolution++;
                }
            }
            if (currentStateHeuristic == 0) {
                System.out.println("Solution Found :");
                System.out.println("Steps Needed : " + stepsToSolution);

                avgSuccessSteps = avgSuccessSteps + stepsToSolution;
                System.out.println("Total Steps Needed : " + avgSuccessSteps);
                success++;
            }
            printState(neighbourState);
        }
        try {
            percent = (success * 100) / (success + failed);
            failure = (failed * 100) / (success + failed);
            System.out.println("Total Percentage of Success = " + percent + "%");
            System.out.println("Total Percentage of failure = " + failure + "%");
            if (variation == 3) {
                System.out.println("Average no of randomStarts" + TotalavgrandomStart / 100);
                System.out.println("Average no of steps for success results = " + avgSuccessSteps / success);
            } else {
                System.out.println("Average no of steps for success results = " + avgSuccessSteps / success);
                System.out.println("Average no of steps for failed results = " + avgFailureSteps / failed);
            }
        } catch (ArithmeticException e) {

        }

    }

    public static QueenState[] generateBoard() {
        QueenState[] startBoard = new QueenState[n];
        Random rndm = new Random();
        for (int i = 0; i < n; i++) {
            startBoard[i] = new QueenState(rndm.nextInt(n), i);
        }
        /*startBoard[0] = new QueenState(2, 0);
        startBoard[1] = new QueenState(0, 1);
        startBoard[2] = new QueenState(3, 2);
        startBoard[3] = new QueenState(3, 3);*/
        return startBoard;
    }

    private static void printState(QueenState[] state) {
        int[][] tempBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            tempBoard[state[i].getRow()][state[i].getColumn()] = 1;
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static QueenState[] nextBoard(QueenState[] currentState) {
        QueenState[] neighbourState = new QueenState[n];
        QueenState[] tmpState = new QueenState[n];
        int currentHeuristic = findHeuristic(currentState);

        int bestHeuristic = currentHeuristic;
        int tempH;

        for (int i = 0; i < n; i++) {
            neighbourState[i] = new QueenState(currentState[i].getRow(), currentState[i].getColumn());
            tmpState[i] = neighbourState[i];
        }
        //  traverse each column
        for (int i = 0; i < n; i++) {
            if (i > 0)
                tmpState[i - 1] = new QueenState(currentState[i - 1].getRow(), currentState[i - 1].getColumn());
            tmpState[i] = new QueenState(0, tmpState[i].getColumn());
            //  traverse each row
            for (int j = 0; j < n; j++) {
                tempH = findHeuristic(tmpState);
                if (tempH <= bestHeuristic) {
                    bestHeuristic = tempH;
                    for (int k = 0; k < n; k++) {
                        neighbourState[k] = new QueenState(tmpState[k].getRow(), tmpState[k].getColumn());
                    }
                }
                if (tmpState[i].getRow() != n - 1)
                    if (bestHeuristic != 0) {
                        tmpState[i].moveQueen();
                    }

            }
        }
      /*  if (bestHeuristic > currentStateHeuristic && variation == 2) {
            runAgain = true;
            System.out.println("bestHeuristic" + bestHeuristic);
            neighbourState = tmpState;
            heuristic = bestHeuristic;
        } else */
        printState(neighbourState);
        if (heuristic != 0) {
            System.out.println("Heuristic = " + heuristic);
            int count = stepsToSolution + 1;
            System.out.println("Step" + count);
        } else {
            int count = stepsToSolution + 1;
            System.out.println("Step" + count);
        }
        if (bestHeuristic == currentStateHeuristic) {

            neighbourState = tmpState;
            heuristic = bestHeuristic;
            if (variation == 3) {
                System.out.println("Random Start");
                neighbourState = generateBoard();
                avgrandomStart++;
                heuristic = findHeuristic(neighbourState);
                TotalavgrandomStart = avgrandomStart + TotalavgrandomStart;

            } else {
                breakloop = true;
            }
        } else {
            heuristic = bestHeuristic;
        }

        return neighbourState;
    }


    public static int findHeuristic(QueenState[] state) {
        int heuristic = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = i + 1; j < state.length; j++) {
                if (state[i].checkConflict(state[j])) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }

    public static QueenState[] moveOneQueenRandom(QueenState[] currentState) {
        System.out.println("in move");
        Random rndm = new Random();
        int tempn = currentState.length;
        int col = rndm.nextInt(tempn);
        currentState[col] = new QueenState(rndm.nextInt(tempn), col);
        return currentState;
    }


}
