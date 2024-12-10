import java.util.ArrayList;
import java.util.HashSet;

public class Road {
    MeetingArea head;

    @Override
    public String toString() {
        return "矿道信息如下:\n" + head + "矿道信息如上。";
    }

    public int getCarInMeetingArea() {
        int res = 0;
        Node cur = head;
        while (cur != null) {
            if (cur instanceof MeetingArea && cur.left != null && cur.right != null) {
                res += ((MeetingArea) cur).getCars().size();
            }
            cur = cur.right;
        }
        return res;
    }

    public Road() {
    }

    public Road(int[] meetingAreaInfo, int[] roadLength, ArrayList<Car> cars) {
        MeetingArea head = new MeetingArea(2);
        this.head = head;
        MeetingArea left = head;
        int len = 0;
        for (int i = 0; i < meetingAreaInfo.length; i++) {
            MeetingArea right = new MeetingArea(meetingAreaInfo[i]);
            Section section = new Section(roadLength[i], left, right);
            left.right = section;
            section.left = left;
            section.right = right;
            right.left = section;
            left = right;

            // x值
            len += roadLength[i];
            cars.get(i).getPosition().add(len);
            left.getCars().add(cars.get(i));
        }
        // 最后大车厂
        MeetingArea right = new MeetingArea(100);
        Section section = new Section(roadLength[roadLength.length - 1], left, right);
        left.right = section;
        section.left = left;
        section.right = right;
        right.left = section;
    }

    //  路段
    class Section extends Node {
        int len;

        // 方向0表示左，1表示右.-1表示无车
        int direct;

        public Section() {
        }

        public Section(int len, MeetingArea left, MeetingArea right) {
            this.len = len;
            this.left = left;
            this.right = right;
            this.direct = -1;
        }

        @Override
        public String toString() {
            return "道路长度" + len + "\n" + right;
        }
    }

    // 会车区class
    public class MeetingArea extends Node {
        // 会车区大小
        int size;

        // 会车区车辆
        HashSet<Car> cars;


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.left == null) {
                sb.append("采矿区,可停车 ");
            } else if (this.right == null) {
                sb.append("卸矿区,可停车 ");
            } else {
                sb.append("道路会车区,可停车 ");
            }
            sb.append(size).append(" 辆。已停车 ").append(cars.size()).append(" 辆。");
            if (cars.size() != 0) {
                for (Car c : cars) {
                    sb.append("\n     ");
                    sb.append(c);
                }
            }
            sb.append("\n");
            if (right != null) {
                sb.append(right);
            }
            return sb.toString();
        }

        public MeetingArea(int size) {
            this.size = size;
            cars = new HashSet<>();
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public HashSet<Car> getCars() {
            return cars;
        }

        public void setCars(HashSet<Car> cars) {
            this.cars = cars;
        }
    }

    class Node {
        // 左侧道路
        Node left;
        Node right;

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}
