package ohtu;

import java.lang.Math;

public class TennisGame {
    
    private int player1Score = 0;
    private int player2Score = 0;
    private String player1Name;
    private String player2Name;

    private final String[] scores = {
        "Love",
        "Fifteen",
        "Thirty",
        "Forty"
    };

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName == "player1") {
            player1Score += 1;
            return;
        }
        
        player2Score += 1;
    }

    public String getScore() {
        if (player1Score == player2Score) {
            return getEvenScore();
        }

        if (player1Score > 3 || player2Score > 3) {
            return getBeyondDeuceScore();
        }

        return valueOf(player1Score) + "-" + valueOf(player2Score);
    }

    private String getEvenScore() {
        if (player1Score > 3) {
            return "Deuce";
        }

        return valueOf(player1Score) + "-All";
    }

    private String getBeyondDeuceScore() {
        String returnable = "";
        int difference = player1Score - player2Score;

        if (Math.abs(difference) > 1) {
            returnable += "Win for ";
        } else {
            returnable += "Advantage ";
        }

        if (difference > 0) {
            return returnable += "player1";
        }
        return returnable += "player2";
    }

    private String valueOf(int score) {
        return scores[score];
    }
}