package marvel.android.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import marvel.android.game.FlappyGame;
import marvel.android.game.sprites.Bird;
import marvel.android.game.sprites.Tube;

public class PlayState extends State {

    public static final int TUBE_SPACING = 125;
    public static final int TUBE_COUNT = 4;
    public static final int GROUND_Y_OFFSET = -30;

    Bird bird;
    Array<Tube> tubes;

    private Texture background;
    private Texture surface;
    private Vector2 surfacePos1, surfacePos2;
    private BitmapFont font;
    private float backgroundPos1, backgroundPos2;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        gsm.score = 0;
        font = new BitmapFont();
        background = new Texture("bg.png");
        surface = new Texture("ground.png");
        surfacePos1 = new Vector2(camera.position.x - camera.viewportWidth/2, GROUND_Y_OFFSET);
        surfacePos2 = new Vector2(camera.position.x - camera.viewportWidth/2 + surface.getWidth(), GROUND_Y_OFFSET);
        backgroundPos1 = camera.position.x - camera.viewportWidth/2;
        backgroundPos2 = camera.position.x - camera.viewportWidth/2 + background.getWidth();
        bird = new Bird(50, 300);
        tubes = new Array<Tube>();
        while (tubes.size < TUBE_COUNT) {
            tubes.add(new Tube((tubes.size+1)*(TUBE_SPACING + Tube.TUBE_WIDTH), tubes.size+1));
        }

        camera.setToOrtho(false, FlappyGame.WIDTH/2, FlappyGame.HEIGHT/2);
    }

    @Override
    protected void handlInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handlInput();
        updateSurface();
        bird.update(dt);
        updateBackground(dt);
        camera.position.x = bird.getPosition().x + 80;
        for (int i = 0; i < tubes.size; i++) {
            Tube t = tubes.get(i);

            if (!t.behind) {
                if (bird.getPosition().x > t.getPosTubeB().x + t.getTubeB().getWidth()) {
                    t.behind = true;
                    gsm.score++;
                }
                if (t.collides(bird.getBounds())) {
                    gsm.set(new GameoverState(gsm));
                }
            }
            else {
                if (camera.position.x - camera.viewportWidth / 2 > t.getPosTubeT().x + t.getTubeT().getWidth()) {
                    t.reposition(t.getPosTubeT().x + (TUBE_SPACING + Tube.TUBE_WIDTH) * TUBE_COUNT);
                    t.id += 4;
                    t.behind = false;
                }
            }
        }
        if (bird.getPosition().y < surfacePos1.y + surface.getHeight()) {
            gsm.set(new GameoverState(gsm));
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, backgroundPos1, 0);
        sb.draw(background, backgroundPos2, 0);
        sb.draw(surface, surfacePos1.x, surfacePos1.y);
        sb.draw(surface, surfacePos2.x, surfacePos2.y);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        for (Tube t : tubes) {
            if (t.id == gsm.max_score) {
                sb.draw(t.getTubeBG(), t.getPosTubeB().x, t.getPosTubeB().y);
                sb.draw(t.getTubeTG(), t.getPosTubeT().x, t.getPosTubeT().y);
            }
            else {
                sb.draw(t.getTubeB(), t.getPosTubeB().x, t.getPosTubeB().y);
                sb.draw(t.getTubeT(), t.getPosTubeT().x, t.getPosTubeT().y);
            }
        }
        font.draw(sb, "SCORE: "+gsm.score, camera.position.x, camera.viewportHeight-5);
        sb.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        bird.dispose();
        for (Tube t : tubes) {
            t.dispose();
        }
        surface.dispose();
        background.dispose();
        System.out.println("PlayState disposed!");
    }

    public void updateSurface() {
        if (camera.position.x - camera.viewportWidth/2 > surfacePos1.x + surface.getWidth()) {
            surfacePos1.add(surface.getWidth()*2, 0);
        }
        if (camera.position.x - camera.viewportWidth/2 > surfacePos2.x + surface.getWidth()) {
            surfacePos2.add(surface.getWidth()*2, 0);
        }
    }

    private void updateBackground(float dt) {
        backgroundPos1 += Bird.MOVEMENT*dt/2;
        backgroundPos2 = backgroundPos1 - background.getWidth();

        if (camera.position.x - camera.viewportWidth/2 > backgroundPos1 + background.getWidth()) {
            backgroundPos1 += background.getWidth()*2;
        }
        if (camera.position.x - camera.viewportWidth/2 > backgroundPos2 + background.getWidth()) {
            backgroundPos2 += background.getWidth()*2;
        }
    }

}
