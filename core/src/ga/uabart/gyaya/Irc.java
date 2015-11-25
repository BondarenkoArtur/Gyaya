package ga.uabart.gyaya;

import ga.uabart.gyaya.Screens.PlayScreen;
import org.jibble.pircbot.*;

import java.io.IOException;

public class Irc extends PircBot{

    private Gyaya game;

    public Irc(Gyaya game){
        this.game = game;
    }

    public void init() {

        // Enable debugging output.
        this.setVerbose(true);

        this.setName("uaBArtBot");
        // Connect to the IRC server.
        try {
            this.connect("irc.twitch.tv", 6667, "oauth:9ldmg9z1p0uesld6gpa0cqn3i2btkt");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }

        // Join the #pircbot channel.
        this.joinChannel("#uabart");
    }

    public void onMessage(String channel, String sender,
                          String login, String hostname, String message) {
        if (message.equalsIgnoreCase("time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }

        PlayScreen screen = (PlayScreen) game.getScreen();

        if (message.equalsIgnoreCase("jump")) {
            screen.jump();
        }

        if (message.equalsIgnoreCase("left")) {
            screen.left();
        }

        if (message.equalsIgnoreCase("right")) {
            screen.right();
        }

    }

}