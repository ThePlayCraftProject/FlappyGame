package marvel.android.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import marvel.android.game.states.PlayState;

public class Bird implements Disposable {

    public static final int MOVEMENT = 100;
    public static final int G = -15;

    private Vector3 position;
    private Vector3 velocity;

    private Texture bird;
    private Rectangle bounds;
    private Animation birdAnim;

    private Sound flap;

    public Rectangle getBounds() {
        return bounds;
    }

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        bird = new Texture("birdanimation.png");
        birdAnim = new Animation(new TextureRegion(bird), 3, 0.5f);
        bounds = new Rectangle(x, y, bird.getWidth()/3, bird.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt) {
        birdAnim.update(dt);
        velocity.add(0, G, 0);
        velocity.scl(dt);
        position.add(MOVEMENT*dt, velocity.y, 0);

        velocity.scl(1/dt);

        if (position.y < 0) {
            position.y = 0;
        }

        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnim.getFrame();
    }

    public void jump() {
        velocity.y = 250;
        flap.play();
    }

    @Override
    public void dispose() {
        flap.dispose();
        bird.dispose();
    }
}
