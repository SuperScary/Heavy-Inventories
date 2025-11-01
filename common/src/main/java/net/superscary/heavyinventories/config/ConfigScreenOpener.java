package net.superscary.heavyinventories.config;

@FunctionalInterface
public interface ConfigScreenOpener {

    void openConfigScreen();

    ConfigScreenOpener NO_OP = () -> {};

}
