package net.laserdiamond.serverplugin1201.items.management.weapons;

public enum ToolTypes {

    SWORD ("sword"),
    AXE ("axe"),
    PICKAXE ("pickaxe"),
    SHOVEL ("shovel"),
    HOE ("hoe");

    private final String toolName;
    ToolTypes(String toolName) {
        this.toolName = toolName;
    }

    public String getToolName() {
        return toolName;
    }
}
