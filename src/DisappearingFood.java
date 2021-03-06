import java.util.Random;

/**
 * This class represents an object that extends the Food objects, but also can disappear after some time.
 */
public class DisappearingFood extends Food {
    private final long spawnTime;
    private final long lifetime;

    /**
     * When created, sets the spawnTime to the currentTime, and its lifetime to 2 secs.
     *
     * @param screen the food's screen.
     */
    public DisappearingFood(Screen screen) {
        super(screen);
        this.spawnTime = System.currentTimeMillis();
        Random random = new Random();
        this.lifetime = random.nextInt(2000)+1000;
    }

    /**
     * Returns whether the DisappearingFood is still alive.
     *
     * @return whether the DisappearingFood is still alive.
     */
    public boolean isAlive() {
        return System.currentTimeMillis() < spawnTime + lifetime;
    }

    /**
     * Updates the food's position if needed (the DisappearingFood died or it's out of the screen)
     *
     * @param difficulty this needs to be passed because of the relocateFood() function
     */
    @Override
    public void update(Main.Difficulty difficulty) {
        if (!isAlive() || getPosX() < 0)
            screen.relocateFood(this, difficulty);
    }
}
