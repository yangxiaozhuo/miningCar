public class Main {
    public static void main(String[] args) {
        // 车辆的容量
        int[] categories = {30, 30, 30, 30, 30};
        // 2表示十字路口，1表示会车区
        int[] meetingAreaInfo = {1, 2, 1, 1, 2};
        // 每段路的长度
        int[] roadLength = {3, 2, 4, 5, 6, 7};
        // 需求
        int all = 500;
        Mining mining = new Mining(all, categories, meetingAreaInfo, roadLength);
        System.out.println("开始规划");
        mining.start();
        for (int i = 0; i < mining.cars.size(); i++) {
            mining.cars.get(i).getPosition();
        }
        System.out.println(mining.all);
        System.out.println("规划结束");
    }
}