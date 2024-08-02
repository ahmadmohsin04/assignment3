import java.util.Scanner;
import java.util.Random;

public class CricketScorecard {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Enter the name of Team 1:");
        String team1 = scanner.nextLine();
        
        System.out.println("Enter the name of Team 2:");
        String team2 = scanner.nextLine();

        System.out.println("Simulating toss...");
        String battingTeam = random.nextBoolean() ? team1 : team2;
        String bowlingTeam = battingTeam.equals(team1) ? team2 : team1;

        System.out.println(battingTeam + " won the toss and will bat first.");

        // 2. Player Data
        String[] team1Players = new String[11];
        String[] team2Players = new String[11];

        System.out.println("Enter player names for " + team1 + ":");
        for (int i = 0; i < 11; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            team1Players[i] = scanner.nextLine();
        }

        System.out.println("Enter player names for " + team2 + ":");
        for (int i = 0; i < 11; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            team2Players[i] = scanner.nextLine();
        }

        int[] team1Scores = new int[11];
        int[] team1Balls = new int[11];
        boolean[] team1OutStatus = new boolean[11];

        int[] team2Scores = new int[11];
        int[] team2Balls = new int[11];
        boolean[] team2OutStatus = new boolean[11];

        int team1Total = playInnings(team1, team1Players, team1Scores, team1Balls, team1OutStatus, scanner, random);
        int team2Total = playInnings(team2, team2Players, team2Scores, team2Balls, team2OutStatus, scanner, random);

        displayScorecard(team1, team1Players, team1Scores, team1Balls, team1OutStatus, team1Total);
        displayScorecard(team2, team2Players, team2Scores, team2Balls, team2OutStatus, team2Total);

        if (team1Total > team2Total) {
            System.out.println(team1 + " wins!");
        } else if (team2Total > team1Total) {
            System.out.println(team2 + " wins!");
        } else {
            System.out.println("It's a tie!");
        }

        scanner.close();
    }

    private static int playInnings(String teamName, String[] players, int[] scores, int[] balls, boolean[] outStatus, Scanner scanner, Random random) {
        System.out.println("Starting innings for " + teamName);
        int totalScore = 0;
        int wickets = 0;

        for (int i = 0; i < 11 && wickets < 10; i++) {
            System.out.println("Batsman: " + players[i]);

            for (int ball = 0; ball < 6; ball++) {
                int run = random.nextInt(7); // Random run between 0 and 6
                scores[i] += run;
                balls[i]++;

                if (run == 0) {
                    if (random.nextDouble() < 0.1) { // 10% chance of getting out
                        System.out.println(players[i] + " is out!");
                        outStatus[i] = true;
                        wickets++;
                        break;
                    }
                }

                totalScore += run;
            }

            System.out.println(players[i] + " scored " + scores[i] + " runs in " + balls[i] + " balls.");
        }

        System.out.println("Innings for " + teamName + " finished with a total score of " + totalScore + "/" + wickets);
        return totalScore;
    }

    private static void displayScorecard(String teamName, String[] players, int[] scores, int[] balls, boolean[] outStatus, int totalScore) {
        System.out.println("Scorecard for " + teamName);
        System.out.println("Player\t\tScore\tBalls\tStrike Rate\tOut/Not Out");

        for (int i = 0; i < players.length; i++) {
            double strikeRate = (balls[i] > 0) ? (scores[i] / (double)balls[i]) * 100 : 0;
            System.out.println(players[i] + "\t\t" + scores[i] + "\t" + balls[i] + "\t" + String.format("%.2f", strikeRate) + "\t" + (outStatus[i] ? "Out" : "Not Out"));
        }

        System.out.println("Total: " + totalScore + "/10\n");
    }
}
