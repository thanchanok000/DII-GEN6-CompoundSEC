package Class;

import java.util.ArrayList;
import   java.util.List;

public class DataManager {
    private static DataManager instance;
    private List<Floor> floors;

    private DataManager() {
        floors = new ArrayList<>();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public List<Floor> getAllFloors() {
        return new ArrayList<>(floors);
    }
}