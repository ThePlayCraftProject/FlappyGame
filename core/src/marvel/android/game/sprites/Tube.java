package marvel.android.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

public class Tube implements Disposable {
    public static final int TUBE_WIDTH = 130;
    public static final int FLUCTUATION = 130;
    public static final int TUBE_GAP = 130;
    public static final int LOWEST_OPENNING = 130;

    private Texture tubeT, tubeB;
    private Vector2 posTubeT, posTubeB;
    private Rectangle boundsT, boundsB;

    private Random rand;

    public Tube(float x) {
        tubeT = new Texture("toptube.png");
        tubeB = new Texture("bottomtube.png");

        rand = new Random();

        posTubeT = new Vector2(x, rand.nextInt(FLUCTUATION)+TUBE_GAP+LOWEST_OPENNING);
        posTubeB = new Vector2(x, posTubeT.y - TUBE_GAP - tubeB.getHeight());

        boundsT = new Rectangle(posTubeT.x, posTubeT.y, tubeT.getWidth(), tubeT.getHeight());
        boundsB = new Rectangle(posTubeB.x, posTubeB.y, tubeB.getWidth(), tubeB.getHeight());
    }

    public void reposition(float x) {
        posTubeT.set(x, rand.nextInt(FLUCTUATION)+TUBE_GAP+LOWEST_OPENNING);
        posTubeB.set(x, posTubeT.y - TUBE_GAP - tubeB.getHeight());

        boundsT.setPosition(posTubeT.x, posTubeT.y);
        boundsB.setPosition(posTubeB.x, posTubeB.y);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsT) || player.overlaps(boundsB);
    }



    public Texture getTubeT() {
        return tubeT;
    }

    public Texture getTubeB() {
        return tubeB;
    }

    public Vector2 getPosTubeT() {
        return posTubeT;
    }

    public Vector2 getPosTubeB() {
        return posTubeB;
    }

    @Override
    public void dispose() {
        tubeT.dispose();
        tubeB.dispose();
    }
}
