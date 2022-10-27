package com.codecool.leaguestatistics.view;

import com.codecool.leaguestatistics.model.Team;
import com.codecool.leaguestatistics.util.TableList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides console view
 */
public class Display {
    public static void displayResult(Team team1, Team team2, int resultGoalsTeam1, int resultGoalsTeam2) {
        TableList table = new TableList(7, "Team", "Points", "Goals", "Wins", "Draws", "Loses", "Current Match Result")
                .sortBy(1).withUnicode(true);
        List<Team> teams = Arrays.asList(team1, team2);
        List<Integer> currentMatchResult = Arrays.asList(resultGoalsTeam1, resultGoalsTeam2);
        teams.forEach(team -> {
            AtomicInteger teamGoals = new AtomicInteger();
            team.getPlayers().forEach(player -> teamGoals.addAndGet(player.getGoals()));
            table.addRow(team.getName(),
                    String.format("%s", team.getCurrentPoints()),
                    String.format("%s", teamGoals),
                    String.format("%s", team.getWins()),
                    String.format("%s", team.getDraws()),
                    String.format("%s", team.getLoses()),
                    String.format("%s", currentMatchResult.get(teams.indexOf(team))));
        });
        table.print();
    }
}
