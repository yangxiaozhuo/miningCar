import java.util.ArrayList;

// 整个矿区
public class Mining {
    // 车辆信息
    ArrayList<Car> cars;

    int all;

    Road road;

    public void start() {
        int time = 1;
        while (!isover()) {
            System.out.println("第" + time++ + "s");
        }
    }

    // 判断结束表示
    private boolean isover() {
        // 所有车回到会车区 && all == 0 && 车都没有负载
        if (road.getCarInMeetingArea() != cars.size()) {
            return false;
        }
        if (all != 0) {
            return false;
        }
        for (Car car : cars) {
            if (car.getCategory() != 0) {
                return false;
            }
        }
        return true;
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
