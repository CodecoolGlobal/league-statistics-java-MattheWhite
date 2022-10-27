package com.codecool.leaguestatistics.controller;

import com.codecool.leaguestatistics.util.Utils;
import com.codecool.leaguestatistics.factory.LeagueFactory;
import com.codecool.leaguestatistics.model.Team;
import com.codecool.leaguestatistics.view.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides all necessary methods for season simulation
 */
public class Season {

    private List<Team> league;

    public Season() {
        league = new ArrayList<>();
    }

    /**
     * Fills league with new teams and simulates all games in season.
     * After all games played calls table to be displayed.
     */
    public void run() {
        this.league = LeagueFactory.createLeague(6);
        playAllGames();
    }

    /**
     * Playing whole round. Everyone with everyone one time. Number of teams in league should be even.
     * Following solution represents the robin-round tournament.
     */
    int COUNT = 0;

    private void playAllGames() {
        league.forEach(team -> league.stream().skip(league.indexOf(team)).forEach(team1 -> {
            if (!team.equals(team1)) {
                playMatch(team, team1);
                ++COUNT;
                System.out.println("Played matches counter -> " + COUNT + "\n");
            }
        }));
    }

    /**
     * Plays single game between two teams and displays result after.
     */
    private void playMatch(Team team1, Team team2) {
        int team1Goals = getScoredGoals(team1);
        int team2Goals = getScoredGoals(team2);
        if (team1Goals > team2Goals) {
            team1.setWins(team1.getWins() + 1);
            team2.setLoses(team2.getLoses() + 1);
        } else if (team2Goals > team1Goals) {
            team2.setWins(team2.getWins() + 1);
            team1.setLoses(team1.getLoses() + 1);
        } else {
            team1.setDraws(team1.getDraws() + 1);
            team2.setDraws(team2.getDraws() + 1);
        }
        Display.displayResult(team1, team2, team1Goals, team2Goals);
    }

    /**
     * Checks for each player of given team chance to score based on skillrate.
     * Adds scored goals to player's and team's statistics.
     * @return All goals scored by the team in current game
     */
    private int getScoredGoals(Team team) {
        AtomicInteger goals = new AtomicInteger();
        team.getPlayers().forEach(player -> {
            if (player.getSkillRate() > Utils.getRandomValue(1, 101)) {
                goals.addAndGet(1);
                player.setGoals(player.getGoals() + 1);
            }
        });
        return goals.get();
    }
}
