package marvel.android.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import marvel.android.game.FlappyGame;

public class GameoverState extends State {

    private Texture background;
    private Texture gameover;
    private BitmapFont font;

    public GameoverState(GameStateManager gsm) {
        super(gsm);
        if (gsm.score > gsm.max_score) gsm.max_score = gsm.score;

        font = new BitmapFont();

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
        font.draw(sb, "SCORE: "+gsm.score, camera.position.x-50, camera.position.y - 30);
        font.draw(sb, "MAX SCORE: "+gsm.max_score, camera.position.x-70, camera.position.y - 60);
        sb.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        gameover.dispose();
        background.dispose();
        System.out.println("GameoverState disposed!");
    }
}
