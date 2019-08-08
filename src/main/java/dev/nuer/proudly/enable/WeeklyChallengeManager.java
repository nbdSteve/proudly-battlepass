package dev.nuer.proudly.enable;

import dev.nuer.pp.challenges.Challenge;
import dev.nuer.pp.challenges.ChallengeWeek;

import java.io.File;
import java.util.HashMap;

public class WeeklyChallengeManager {

    public static HashMap<Integer, ChallengeWeek> weeks;

    public static void load(FileManager fileManager) {
        weeks = new HashMap<>();
        //Load the week configuration for the challenges.
        for (int i = 1; i <= FileManager.get("challenge_config").getInt("number-of-weeks"); i++) {
            fileManager.add("challenges_week_" + i, "challenges" + File.separator + "week-" + i + ".yml");
            weeks.put(i, new ChallengeWeek(String.valueOf(i)));
        }
    }

    public static Challenge getChallenge(String challengeId) {
        for (ChallengeWeek weeks : weeks.values()) {
            for (Challenge challenge : weeks.challenges) {
                if (challenge.getId().equalsIgnoreCase(challengeId)) return challenge;
            }
        }
        return null;
    }

    public static ChallengeWeek getWeek(int week) {
        return weeks.get(week);
    }
}
