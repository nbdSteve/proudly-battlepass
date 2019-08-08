package dev.nuer.proudly.tiers.exception;

import dev.nuer.proudly.BattlePass;

public class ExceedMaxPlayerTierException extends Exception {

    public ExceedMaxPlayerTierException() {
        BattlePass.log.severe("You cannot set a players tier higher than the max tier.");
    }
}