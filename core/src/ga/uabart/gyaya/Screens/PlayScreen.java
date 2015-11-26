    package ga.uabart.gyaya.Screens;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Input;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.audio.Music;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.TextureAtlas;
    import com.badlogic.gdx.maps.tiled.TiledMap;
    import com.badlogic.gdx.maps.tiled.TmxMapLoader;
    import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.physics.box2d.*;
    import com.badlogic.gdx.utils.viewport.FitViewport;
    import com.badlogic.gdx.utils.viewport.Viewport;
    import ga.uabart.gyaya.Gyaya;
    import ga.uabart.gyaya.Scenes.Hud;
    import ga.uabart.gyaya.Sprites.Player;
    import ga.uabart.gyaya.Tools.B2WorldCreator;
    import ga.uabart.gyaya.Tools.WorldContactListener;

    public class PlayScreen implements Screen {

        private Gyaya game;
        private TextureAtlas atlas;

        private OrthographicCamera gameCamera;
        private Viewport gameViewport;
        private Hud hud;

        private TmxMapLoader mapLoader;
        private TiledMap map;
        private OrthogonalTiledMapRenderer renderer;

        private Texture background;

        private Music music;

        private World world;
        private Box2DDebugRenderer b2dr;
        private Player player;

        private boolean debugMode = false;

        public PlayScreen(Gyaya gyaya){
            atlas = new TextureAtlas("characters.atlas");
            background = new Texture("background1.png");
            this.game = gyaya;
            gameCamera = new OrthographicCamera();
            gameViewport = new FitViewport(Gyaya.V_WIDTH/Gyaya.PPM, Gyaya.V_HEIGHT/Gyaya.PPM, gameCamera);

            mapLoader = new TmxMapLoader();
            map = mapLoader.load("level1.tmx");
            renderer = new OrthogonalTiledMapRenderer(map, 1 / Gyaya.PPM);

            gameCamera.position.set(gameViewport.getWorldWidth()/2, gameViewport.getWorldHeight()/2, 0);
            world = new World(new Vector2(0, -10), true);
            b2dr = new Box2DDebugRenderer();

            new B2WorldCreator(this);

            player = new Player(this);

            hud = new Hud(gyaya.batch, player);

            world.setContactListener(new WorldContactListener());

            music = Gyaya.manager.get("audio/music/background.ogg", Music.class);
            music.setLooping(true);
            music.play();
        }

        public TextureAtlas getAtlas(){
            return atlas;
        }

        public TiledMap getMap(){
            return map;
        }

        public World getWorld(){
            return world;
        }

        @Override
        public void show() {

        }

        public void jump(){
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }

        public void left() {
            player.b2body.applyLinearImpulse(new Vector2(-1f, 0), player.b2body.getWorldCenter(), true);
        }

        public void right() {
            player.b2body.applyLinearImpulse(new Vector2(1f, 0), player.b2body.getWorldCenter(), true);
        }

        public void handleInput(float delta){
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.justTouched())
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.getAccelerometerY() > 2) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.getAccelerometerY() < -2) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyJustPressed(Input.Keys.D))
                debugMode = !debugMode;
        }

        public void update(float delta){
            handleInput(delta);

            world.step(1/60f, 6, 2);

            player.update(delta);
            hud.update(delta);

            gameCamera.position.x = player.b2body.getPosition().x;
            gameCamera.position.y = player.b2body.getPosition().y;

            if (gameCamera.position.x < 1.2f)
                gameCamera.position.x = 1.2f;
            if (gameCamera.position.y < 0.8f)
                gameCamera.position.y = 0.8f;
            //TODO: Remove hardcode. Find Camera scale and make method for counting this.
            if (gameCamera.position.x > 19.8f)
                gameCamera.position.x = 19.8f;
            if (gameCamera.position.y > 2.14f)
                gameCamera.position.y = 2.14f;

//            Gdx.app.log("Coords", "x: " + gameCamera.position.x + "\ty: " + gameCamera.position.y);

            gameCamera.update();

            renderer.setView(gameCamera);
        }

        @Override
        public void render(float delta) {
            update(delta);

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            if (!debugMode){
                game.batch.begin();
                game.batch.draw(background, 0, 0, 480, 320);
                game.batch.end();

                renderer.setView((OrthographicCamera)gameViewport.getCamera());
                renderer.render();

                game.batch.setProjectionMatrix(gameCamera.combined);
                game.batch.begin();
                player.draw(game.batch);
                game.batch.end();

                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.render(delta);
                hud.update(delta);
            }
            else {
                b2dr.render(world, gameCamera.combined);
            }
        }

        @Override
        public void resize(int width, int height) {
            gameViewport.update(width, height, true);
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {
            map.dispose();
            renderer.dispose();
            world.dispose();
            b2dr.dispose();
            hud.dispose();
        }
    }
