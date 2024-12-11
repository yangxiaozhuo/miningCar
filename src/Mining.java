import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

// 整个矿区
public class Mining {
    // 车辆信息
    ArrayList<Car> cars;

    int all;

    Road road;

    public void start() {
        int time = 1;
        int maxTime = 500;
        // 只测算十秒
        while (!isover() && time < maxTime) {
            if (time == 400) {
                System.out.println("===");
            }
            System.out.println("第" + time++ + "s");
            System.out.println("==========================");
            System.out.println("车辆信息如下");
            for (int i = 0; i < cars.size(); i++) {
                System.out.println(cars.get(i));
            }
            System.out.println("车辆信息如上");
            System.out.println("================");
            System.out.println(road);

            // 对有负荷的车进行规划
            ArrayList<Car> toRightCar = new ArrayList<>();
            ArrayList<Car> toLeftCar = new ArrayList<>();
            for (Car c : cars) {
                // 往右走
                if (c.getDirection() == 1) {
                    toRightCar.add(c);
                } else {
                    toLeftCar.add(c);
                }
            }
            toRightCar.sort((o1, o2) -> o2.getCurPosition() - o1.getCurPosition());
            toLeftCar.sort((o1, o2) -> o1.getCurPosition() - o2.getCurPosition());

            for (Car c : toRightCar) {
                doRight(c);
            }
            // 对没负荷的车进行规划
            for (Car c : toLeftCar) {
                doLeft(c);
            }
        }

    }

    private void doLeft(Car c) {
        // 规划c的下一步，往左走
        int curAt = c.getCurPosition();
        // 当前所在的点
        Road.Node node = road.roadMap.get(curAt);
        // if是会车区
        if (node instanceof Road.MeetingArea) {
            // 两种情况,起点了，或者下一段路
            if (node.left == null) {
                // todo 到起点了.这里可以做按照时间装货。每秒钟装多少，装完了再掉头
                int maxC = Math.min(all, c.getMaxCategory() - c.getCategory());
                c.setCategory(c.getCategory() + maxC);
                all -= maxC;
                // 能否掉头需要判断
                Road.Section rightRoad = (Road.Section) node.right;
                if (rightRoad.getDirect() != 0) {
                    rightRoad.setDirect(1);
//                    rightRoad.addCar();
                    c.setDirection(1);
                }
                c.addPosition(curAt);
            } else {
                // 左侧是路
                // 什么情况下可以进入，1.没有负载车2.这条路+前面会车区的车 <= 前面会车区大小
                Road.Section section = (Road.Section) node.left;
                Road.MeetingArea meetingArea = (Road.MeetingArea) section.getLeft();
                if (section.getDirect() != 1 && section.getCarNum() + meetingArea.getCarsNum() < meetingArea.getSize()) {
                    section.addCar();
                    section.setDirect(0);
                    ((Road.MeetingArea) node).removeCar(c);
                    c.addPosition(curAt - 1);
                } else {
                    c.addPosition(curAt);
                }
            }
        } else {
            // 是一条路
            Road.Section curRoad = (Road.Section) node;
            if (curRoad.getStart() == curAt) {
                // todo 要进入停车区了
                curRoad.removeCar();
                Road.MeetingArea meetingArea = (Road.MeetingArea) curRoad.getLeft();
                meetingArea.addCar(c);
            }
            c.addPosition(c.getCurPosition() - 1);
        }
    }

    private void doRight(Car c) {
        // 规划c的下一步，往右走
        int curAt = c.getCurPosition();
        // 当前所在的点
        Road.Node node = road.roadMap.get(curAt);
        // if是会车区
        if (node instanceof Road.MeetingArea) {
            // 两种情况,到终点了，或者下一段路
            if (node.right == null) {
                // todo 到终点了.这里可以做按照时间卸货。每秒钟卸多少，卸完了再掉头
                c.setCategory(0);
                c.setDirection(0);
                c.addPosition(curAt);
            } else {
                // 下一段路
                // 向右走
                if (!canRemove(curAt + 1)) {
                    c.addPosition(c.getCurPosition());
                } else {
                    c.addPosition(c.getCurPosition() + 1);
//                c.addPosition(curAt + 1);
                    int nextAt = c.getCurPosition();
                    // 下一段路
                    Road.Section nextRoad = (Road.Section) road.roadMap.get(nextAt);
                    nextRoad.setDirect(1);
                    nextRoad.addCar();
                }
            }
            ((Road.MeetingArea) node).removeCar(c);
        } else {
            // 是一条路
            Road.Section curRoad = (Road.Section) node;
            if (curRoad.getEnd() == curAt) {
                // 要进入停车区了
                Road.MeetingArea right = (Road.MeetingArea) curRoad.getRight();
                if (right.right == null) {
                    // 到卸货区了
                    // 直接走
                    c.addPosition(curAt + 1);
                    curRoad.removeCar();
                    right.addCar(c);
                } else {
                    // 要判断会车区下一段路的情况
                    Road.Section section = (Road.Section) right.getRight();
                    // 什么时候能进去，1.下一段路方向向右 或没方向
                    if (section.getDirect() != 0) {
                        section.setDirect(1);
                        c.addPosition(curAt + 1);
                        curRoad.removeCar();
                    } else {
                        c.addPosition(curAt);
                    }
                }
            } else {
                // 往前走
                // 1.判断前面有没有车
                // 有车  停车
                if (!canRemove(curAt + 1)) {
                    c.addPosition(c.getCurPosition());
                } else {
                    c.addPosition(c.getCurPosition() + 1);
                }
            }
        }
    }

    private boolean canRemove(int nextPos) {
        for (Car cc : cars) {
            if (cc.getCurPosition() == nextPos) {
                // 前面有车
                return false;
            }
        }
        return true;
    }

    // 判断结束表示
    private boolean isover() {
        // 所有车回到会车区 && all == 0 && 车都没有负载
//        if (road.getCarInMeetingArea() != cars.size()) {
//            return false;
//        }
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
