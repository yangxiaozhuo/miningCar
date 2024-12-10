public class Main {
    public static void main(String[] args) {
        int[] categories = {10, 10, 10};
        // 2表示十字路口，1表示会车区
        int[] meetingAreaInfo = {1, 2, 1};
        // 每段路的长度
        int[] roadLength = {7, 12, 4, 6};
        int all = 1000;
        Mining mining = new Mining(all, categories, meetingAreaInfo, roadLength);
        System.out.println("开始规划");
        mining.start();
        for (int i = 0; i < mining.cars.size(); i++) {
            System.out.println(mining.cars.get(i).getPosition());
        }
        System.out.println("规划结束");
    }
}