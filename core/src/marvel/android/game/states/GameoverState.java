package marvel.android.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import marvel.android.game.FlappyGame;

public class GameoverState extends State {

    private Texture background;
    private Texture gameover;

    public GameoverState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FlappyGame.WIDTH/2, FlappyGame.HEIGHT/2);
        background = new Texture("bg.png");
        gameover = new Texture("gameover.png");
    }

    @Override
    protected void handlInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handlInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, FlappyGame.WIDTH, FlappyGame.HEIGHT);
        sb.draw(gameover, camera.position.x - gameover.getWidth()/2, camera.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        gameover.dispose();
        background.dispose();
        System.out.println("GameoverState disposed!");
    }
}
