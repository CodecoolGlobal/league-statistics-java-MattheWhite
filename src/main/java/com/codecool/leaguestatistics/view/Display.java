package com.codecool.leaguestatistics.view;

import com.codecool.leaguestatistics.model.Team;

/**
 * Provides console view
 */
public class Display {
    public static void displayResult(Team team1, Team team2, int resultTeam1, int resultTeam2) {
        StringBuilder display = new StringBuilder();
        display.append(team1.getName()).append("\t")
                .append(team2.getName()).append("\n")
                .append(resultTeam1).append("\t")
                .append(resultTeam2);
        System.out.println(display);
    }
}
