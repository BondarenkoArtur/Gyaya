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

public class Joystick extends Actor {

    public static float SIZE = 2f;
    public static float RADIUS = 40f;

    Stage stage;

    protected Vector2 offsetPosition = new Vector2();
    protected Vector2 position = new Vector2();

    private TextureRegion joystickCase;
    private TextureRegion joystickStick;
    private boolean touched;
    private float touchX = 0;
    private float touchY = 0;

    public Joystick(Vector2 pos, Stage stage){

        loadTextures();

        this.position = pos;
        this.stage = stage;

        getOffsetPosition().x = 0;
        getOffsetPosition().y = 0;

        setHeight(SIZE * joystickCase.getRegionHeight());
        setWidth(SIZE * joystickCase.getRegionWidth());
        setX(position.x * stage.getWidth());
        setY(position.y * stage.getHeight());

        this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                touched = true;
                update(x, y);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                update(x, y);
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                touched = false;
                getOffsetPosition().x = 0;
                getOffsetPosition().y = 0;
            }
        });
    }

    private void loadTextures(){
        Texture controlsTexture  = new Texture(Gdx.files.internal("controls.png"));
        joystickCase = new TextureRegion(controlsTexture, 1, 1, 51, 51);
        joystickStick = new TextureRegion(controlsTexture, 53, 1, 31, 31);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        update();
        if (Gyaya.isDrawControls()) {
            batch.draw(joystickCase, getX(), getY(), getWidth(), getHeight());
            batch.draw(joystickStick,
                    (getX() + getWidth() / 2 - joystickStick.getRegionWidth() + getOffsetPosition().x),
                    (getY() + getHeight() / 2 - joystickStick.getRegionHeight() + getOffsetPosition().y),
                    joystickStick.getRegionWidth() * SIZE, joystickStick.getRegionHeight() * SIZE);
        }
    }

    private void update() {
        if (touched) {
            withControl(touchX, touchY);
        }
    }

    private void update(float x, float y) {
        touchX = x;
        touchY = y;
        update();
    }

    public Actor hit(float x, float y, boolean touchable) {
        return x > 0 && x < getWidth() && y > 0 && y < getHeight() ? this : null;
    }

    public Vector2 getOffsetPosition() {
        return offsetPosition;
    }

    public void withControl(float x, float y) {

        //get middle of joystick
        float calcX = x - getWidth() / 2;
        float calcY = y - getHeight() / 2;

        double angle = Math.atan(calcY / calcX) * 180 / Math.PI;
        //angle will be in [-90;90]. Changing it to [0;360]
        if (angle > 0 && calcY < 0)
            angle += 180;
        if (angle < 0)
            if (calcX < 0)
                angle = 180 + angle;
            else
                angle += 360;

//        if (angle > 40 && angle < 140)
//            Gdx.app.log("Control", "up");
//
//        if (angle > 220 && angle < 320)
//            Gdx.app.log("Control", "down");
//
//        if (angle > 130 && angle < 230)
//            Gdx.app.log("Control", "left");
//
//        if (angle < 50 || angle > 310)
//            Gdx.app.log("Control", "right");

        angle = (float) (angle * Math.PI / 180);
        getOffsetPosition().x = (float) ((calcX * calcX + calcY * calcY > RADIUS * RADIUS) ? Math.cos(angle) * RADIUS : calcX);
        getOffsetPosition().y = (float) ((calcX * calcX + calcY * calcY > RADIUS * RADIUS) ? Math.sin(angle) * RADIUS : calcY);
    }
}