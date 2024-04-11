package net.laserdiamond.ventureplugin.util;

public enum StatSymbols {

    HEALTH ("❤"),
    DEFENSE ("⛉"),
    TOUGHNESS ("✧"),
    MANA ("\uD83D\uDD89"),
    MELEE_DAMAGE ("\uD83D\uDDE1"),
    MAGIC_DAMAGE ("⚝"),
    RANGE_DAMAGE ("➶"),
    FORTITUDE ("⛊"),
    SPEED ("\uD83D\uDC5F"),
    LONGEVITY ("\uD83D\uDD53"),
    CAFFEINATION("⚡"),
    MOB_FORTUNE ("\uD83C\uDF40"),
    MINING_FORTUNE ("\uD83C\uDF40"),
    FORAGING_FORTUNE ("\uD83C\uDF40"),
    FARMING_FORTUNE ("\uD83C\uDF40"),
    FISHING_LUCK ("\uD83C\uDF40"),
    COMBAT ("⚔"),
    MINING ("⛏"),
    FORAGING ("\uD83E\uDE93"),
    FARMING ("\uD83E\uDEB4"),
    ENCHANTING ("\uD83D\uDD6E"),
    FISHING ("\uD83C\uDFA3"),
    BREWING ("⚗️");

    private final String symbol;

    StatSymbols(String symbol)
    {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
