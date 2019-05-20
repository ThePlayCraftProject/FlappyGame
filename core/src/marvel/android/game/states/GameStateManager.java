package marvel.android.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

import marvel.android.game.FlappyGame;

public class GameStateManager {
    private Stack<State> states;
    private FlappyGame fg;

    public int score, max_score;

    public GameStateManager(FlappyGame fg) {
        max_score = 0;
        score = 0;
        states = new Stack<State>();
        this.fg = fg;
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop(State state) {
        states.pop().dispose();
    }

    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    public FlappyGame getFlappyGame() {
        return fg;
    }

    public void update(float dt) {
        states.peek().update(dt);
    }
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}