public class DisappearingFood extends Food {
    long spawnTime;
    long lifetime;

    public DisappearingFood(Screen screen) {
        super(screen);
        this.spawnTime = System.currentTimeMillis();
        this.lifetime = 2000;
    }

    public boolean isAlive() {
        return System.currentTimeMillis() < spawnTime + lifetime;
    }

    @Override
    public void update(Main.Difficulty difficulty) {
        if (!isAlive() || getPosX() < 0)
            screen.relocateFood(this, difficulty);
    }
}
