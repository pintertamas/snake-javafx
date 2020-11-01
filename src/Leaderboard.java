import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Leaderboard {
    private ArrayList<String> points;

    public Leaderboard() {
        points = new ArrayList<>();
    }

    public void loadLeaderboard() {
        try {
            File file = new File("leaderboard.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                points.add(data);
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void saveLeaderboard() {

    }

    public void show() {

    }
}

