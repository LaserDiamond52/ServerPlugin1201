package net.laserdiamond.ventureplugin.items.util.tools;

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
