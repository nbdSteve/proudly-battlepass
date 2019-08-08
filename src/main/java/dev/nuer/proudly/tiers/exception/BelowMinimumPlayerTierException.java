package dev.nuer.proudly.tiers.exception;

import dev.nuer.proudly.BattlePass;

public class BelowMinimumPlayerTierException extends Exception {

    public BelowMinimumPlayerTierException() {
        BattlePass.log.severe("Players must have a positive number of tiers, you cannot set a players tier below 0.");
    }
}