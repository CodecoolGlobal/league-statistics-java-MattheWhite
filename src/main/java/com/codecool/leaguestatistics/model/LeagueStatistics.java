package com.codecool.leaguestatistics.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Provides all necessary statistics of played season.
 */
public class LeagueStatistics {

    /**
     * Gets all teams with highest points order, if points are equal next deciding parameter is sum of goals of the team.
     */
    public static List<Team> getAllTeamsSorted(List<Team> teams) {
        Comparator<Team> teamComparator = new Comparator<Team>() {
            @Override
            public int compare(Team team1, Team team2) {
                return Integer.compare(team1.getCurrentPoints(), team2.getCurrentPoints());
            }
        };
        return teams.stream()
                .sorted(teamComparator.reversed()
                        .thenComparing((Team team1, Team team2) -> Integer.compare(team2.getAllGoals(), team1.getAllGoals())))
                .collect(Collectors.toList());
    }

    /**
     * Gets all players from each team in one collection.
     */
    public static List<Player> getAllPlayers(List<Team> teams) {
        return teams.stream().flatMap(team -> team.getPlayers().stream()).collect(Collectors.toList());
    }

    /**
     * Gets team with the longest name
     */
    public static Team getTeamWithTheLongestName(List<Team> teams) {
        Comparator<Team> nameComparator = new Comparator<Team>() {
            @Override
            public int compare(Team team1, Team team2) {
                return Integer.compare(team1.getName().length(), team2.getName().length());
            }
        };
        return teams.stream()
                .max(nameComparator).orElse(null);
                // instead of .get() use .orElse() for return null if it is not exists
    }

    /**
     * Same method with the same output like the one above, only in other way.
     */
    public static Team getTeamWithTheLongestNameWithMax(List<Team> teams) {
        return teams.stream().min(Comparator.comparing(Team::getName)).orElse(null);
        // will sort in descend order
        // instead of .get() use .orElse() for return null if it is not exists
    }

    /**
     * Will return the longest name of the
     * @param teams
     * @return the name of hte team with the longest name
     */
    public static String getTeamsNameWithTheLongestName(List<Team> teams) {
        return teams.stream()
                .map(Team::getName)
                .max(Comparator.comparing(String::length)).orElse(null);
    }

    /**
     * Gets top teams with least number of lost matches.
     * If the amount of lost matches is equal, next deciding parameter is team's current points value.
     * @param teamsNumber The number of Teams to select.
     * @return Collection of selected Teams.
     */
    public static List<Team> getTopTeamsWithLeastLoses(List<Team> teams, int teamsNumber) {
        return teams.stream()
                .sorted(Comparator.comparing(Team::getLoses)
                        .thenComparing(Comparator.comparing(Team::getCurrentPoints).reversed()))
                .limit(teamsNumber).collect(Collectors.toList());
        // Comparator returns 1, 0, or -1 when compares to two element then based on the return value it sorts the order.
        // 1 -> true, 0 -> equals, -1 -> false, on default it will order asc. order, that's why .reversed() needed.
    }

    /**
     * Gets a player with the biggest goals number from each team.
     */
    public static List<Player> getTopPlayersFromEachTeam(List<Team> teams) {
        return teams.stream()
                .map(Team::getBestPlayer).collect(Collectors.toList());
    }

    /**
     * Gets all teams, where there are players with no scored goals.
     */
    public static List<Team> getTeamsWithPlayersWithoutGoals(List<Team> teams) {
        return teams.stream()
                .filter(team -> team.getPlayers().stream().anyMatch(player -> player.getGoals() == 0))
                .collect(Collectors.toList());
    }

    /**
     * Gets players with given or higher number of goals scored.
     * @param goals The minimal number of goals scored.
     * @return Collection of Players with given or higher number of goals scored.
     */
    public static List<Player> getPlayersWithAtLeastXGoals(List<Team> teams, int goals) {
        Predicate<Player> predicate = player -> player.getGoals() >= goals;
        return teams.stream()
                .map(Team::getPlayers)
                .flatMap(Collection::stream).filter(predicate).collect(Collectors.toList());
                // instead of .flatMap(players -> players.stream())
    }

    /**
     * Gets the player with the highest skill rate for given Division.
     */
    public static Player getMostTalentedPlayerInDivision(List<Team> teams, Division division) {
        return teams.stream()
                .filter(team -> team.getDivision() == division)
                .map(Team::getPlayers)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Player::getSkillRate).reversed()
                        .thenComparing((Player player1, Player player2) -> player1.getGoals() - player2.getGoals()))
                .findAny().orElse(null);
    }

    /**
     * OPTIONAL
     * Returns the division with greatest amount of points.
     * If there is more than one division with the same amount current points, then check the amounts of wins.
     */
    public static Division getStrongestDivision(List<Team> teams) {
        throw new RuntimeException("getStrongestDivision method not implemented");
        // return Division.East; -> cheating for the corresponding unit test
    }
}
