package ga.uabart.gyaya.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import ga.uabart.gyaya.Gyaya;
import ga.uabart.gyaya.Screens.PlayScreen;
import ga.uabart.gyaya.Sprites.Brick;
import ga.uabart.gyaya.Sprites.Coin;
import ga.uabart.gyaya.Sprites.Slime;

public class B2WorldCreator {

    private Array<Slime> slimes;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2)/ Gyaya.PPM,(rect.getY() + rect.getHeight()/2)/Gyaya.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox((rect.getWidth()/2)/Gyaya.PPM,(rect.getHeight()/2)/Gyaya.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(PolygonMapObject.class)){
            Polygon poly = ((PolygonMapObject) object).getPolygon();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(poly.getX() / Gyaya.PPM, poly.getY() / Gyaya.PPM);
            poly.setScale(1 / Gyaya.PPM, 1 / Gyaya.PPM);
            poly.setPosition(0, 0);

            body = world.createBody(bodyDef);

            shape.set(poly.getTransformedVertices());
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2)/ Gyaya.PPM,(rect.getY() + rect.getHeight()/2)/Gyaya.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox((rect.getWidth()/2)/Gyaya.PPM,(rect.getHeight()/2)/Gyaya.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Brick(screen, rect);
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(screen, rect);
        }

        //create all slimes
        slimes = new Array<Slime>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            slimes.add(new Slime(screen, rect.getX() / Gyaya.PPM, rect.getY() / Gyaya.PPM));
        }
    }

    public Array<Slime> getSlimes() {
        return slimes;
    }

}
