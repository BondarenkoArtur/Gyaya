package ga.uabart.gyaya.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ga.uabart.gyaya.Gyaya;
import ga.uabart.gyaya.Sprites.Player;

/**
 * Created by Arthur on 11/3/2015.
 */
public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static int score;

    Label countdownLabel;
    static Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label playerLabel;
    Label debugLabel;

    private SpriteBatch spriteBatch;
    private TextureRegion face;


    public Hud(SpriteBatch sb, Player player){
        spriteBatch = sb;
        face = player.getFaceTexture();
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(Gyaya.V_WIDTH*2, Gyaya.V_HEIGHT*2, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("World", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerLabel = new Label("Player", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        debugLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(playerLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        table.row();
        table.add(debugLabel).padTop(10);

        stage.addActor(table);
    }

    public void update(float delta){
        timeCount += delta;
        if (timeCount >= 1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
        debugLabel.setText(Gyaya.debugString);
    }

    public void render(float delta){
        spriteBatch.begin();
        spriteBatch.draw(face, 0, Gyaya.V_HEIGHT*2 - face.getRegionHeight() * 2, face.getRegionWidth() * 2, face.getRegionHeight() * 2);
        spriteBatch.end();
        stage.draw();
    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public void setDebugText(String string) {
        debugLabel.setText(string);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
