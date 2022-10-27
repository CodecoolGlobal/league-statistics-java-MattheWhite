package com.codecool.leaguestatistics.factory;

import com.codecool.leaguestatistics.util.Utils;
import com.codecool.leaguestatistics.model.Division;
import com.codecool.leaguestatistics.model.Player;
import com.codecool.leaguestatistics.model.Team;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Provides full set of teams with players
 */
public class LeagueFactory {

    /**
     * For each division, creates given amount of teams. Each team gets a newly created collection of players.
     * The amount of players should be taken from Utils.TEAM_SIZE
     * @param teamsInDivision Indicates number of teams are in division
     * @return Full set of teams with players
     */
    public static List<Team> createLeague(int teamsInDivision) {
        List<Team> teams = new ArrayList<>();
        Arrays.asList(Division.values())
                .forEach(division -> teams.addAll(IntStream.range(0, teamsInDivision)
                .mapToObj(i -> new Team(division, getPlayers(Utils.TEAM_SIZE)))
                .collect(Collectors.toList())));
        return teams;
    }

    /**
     * Returns a collection with a given amount of newly created players
     */
    private static List<Player> getPlayers(int amount) {
        // IntStream.range(0, amount).mapToObj(i -> new Player(getPlayerSkillRate())).collect(Collectors.toList());
        return Collections.nCopies(amount, new Player(getPlayerSkillRate())).stream().collect(Collectors.toList());
    }

    private static int getPlayerSkillRate() {
        return Utils.getRandomValue(5, 21);
    }
}
