import java.util.ArrayList;

// 整个矿区
public class Mining {
    // 车辆信息
    ArrayList<Car> cars;

    int all;

    Road road;

    public void start() {
        while (!isover()) {

        }
    }

    // 判断结束表示
    // 所有车回到会车区
    private boolean isover() {
        return false;
    }

    public Mining(int all, int[] categories, int[] meetingAreaInfo, int[] roadLength) {
        this.cars = initCars(categories);
        this.all = all;
        this.road = initRoad(meetingAreaInfo, roadLength);

        System.out.println("开始初始化");
        System.out.println("==========================");
        System.out.println("车辆信息如下");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(cars.get(i));
        }
        System.out.println("车辆信息如上");
        System.out.println("================");
        System.out.println(road);
    }

    public Mining() {
    }

    private Road initRoad(int[] meetingAreaInfo, int[] roadLength) {
        return new Road(meetingAreaInfo, roadLength, this.cars);
    }


    // 初始化车辆
    private static ArrayList<Car> initCars(int[] categories) {
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < categories.length; i++) {
            Car car = new Car();
            car.setName("car" + (i + 1));
            car.setCategory(0);
            car.setMaxCategory(categories[i]);
            car.setPosition(new ArrayList<>());
            cars.add(car);
        }
        return cars;
    }
}
