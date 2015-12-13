package ga.uabart.gyaya.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ga.uabart.gyaya.Gyaya;

public class JumpButton extends Actor {

    private static final float SIZE = 2f;
    private TextureRegion joystickJump;
    private boolean isPressed;

    public JumpButton(Vector2 position, Stage stage) {

        Texture controlsTexture  = new Texture(Gdx.files.internal("controls.png"));
        joystickJump = new TextureRegion(controlsTexture, 1, 53, 51, 51);

        setHeight(SIZE * joystickJump.getRegionHeight());
        setWidth(SIZE * joystickJump.getRegionWidth());
        setX(position.x * stage.getWidth());
        setY(position.y * stage.getHeight());

        InputListener inputListener = new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                return true;
            }
        };
        this.addListener(inputListener);
    }

    public boolean isPressed() {
        if (isPressed){
            isPressed = false;
            return true;
        } else return false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (Gyaya.isDrawControls())
            batch.draw(joystickJump, getX(), getY(), getWidth(), getHeight());
    }
}
