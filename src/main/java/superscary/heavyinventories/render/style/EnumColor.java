package superscary.heavyinventories.render.style;

public enum EnumColor {
    BLACK("#"),
    WHITE("#"),
    RED("#"),
    GREEN("#"),
    BLUE("#"),
    YELLOW("#"),
    ORANGE("#"),
    PURPLE("#");

    String color;

    EnumColor (String color) {
        this.color = color;
    }

    public String getHex () {
        return color;
    }

}
