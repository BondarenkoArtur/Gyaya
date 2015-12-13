package ga.uabart.gyaya.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import ga.uabart.gyaya.Gyaya;
import ga.uabart.gyaya.Scenes.Hud;
import ga.uabart.gyaya.Scenes.Joystick;
import ga.uabart.gyaya.Scenes.JumpButton;
import ga.uabart.gyaya.Sprites.Player;

public class Controller {

    private Player player;
    private Joystick joystick;
    private JumpButton jumpButton;

    public Controller(Player player, Hud hud) {
        this.player = player;
        joystick = new Joystick(new Vector2(0.04f,0.06f), hud.stage);
        hud.stage.addActor(joystick);
        jumpButton = new JumpButton(new Vector2(0.74f, 0.06f), hud.stage);
        hud.stage.addActor(jumpButton);
    }

    public void handleInput(float delta){
        if (player.currentState != Player.State.DEAD) {
            if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || jumpButton.isPressed())
                    && player.ableToJump)
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                    && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
                    && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

            if ((joystick.getOffsetPosition().x > 0) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(joystick.getOffsetPosition().x / 400, 0), player.b2body.getWorldCenter(), true);
            if ((joystick.getOffsetPosition().x < 0) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(joystick.getOffsetPosition().x / 400, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D))
            Gyaya.switchDebug();
        if (Gdx.input.isKeyJustPressed(Input.Keys.H))
            Gyaya.switchDrawControls();
    }
}
