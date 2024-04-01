package net.laserdiamond.ventureplugin.stats.Components;

public enum StatSymbols {

    HEALTH ("❤"),
    DEFENSE ("⛉"),
    TOUGHNESS ("✧"),
    MANA ("\uD83D\uDD89"),
    MELEE_DAMAGE ("\uD83D\uDDE1"),
    MAGIC_DAMAGE ("⚝"),
    RANGE_DAMAGE ("➶"),
    FORTITUDE ("⛊"),
    SPEED ("\uD83D\uDC5F");

    private final String symbol;

    StatSymbols(String symbol)
    {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
