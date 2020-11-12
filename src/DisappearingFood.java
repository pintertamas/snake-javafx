import java.util.Random;

public class DisappearingFood extends Food {
    long lifetime;
    long spawnTime;

    public DisappearingFood(int posX, int posY, int r, int g, int b) {
        super(posX, posY, r, g, b);
        this.spawnTime = System.currentTimeMillis();
        Random rand = new Random(1000-500);
        this.lifetime = rand.nextLong();
    }

    public void resetLifetime() {
        this.lifetime = System.currentTimeMillis();
    }

    public void checkAlive() {
        if (System.currentTimeMillis() > spawnTime + lifetime){
            setPosX(-40);
        }
    }
}
