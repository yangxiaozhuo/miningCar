import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Road {
    MeetingArea head;

    HashMap<Integer, Node> roadMap;

    @Override
    public String toString() {
        return "矿道信息如下:\n" + head + "矿道信息如上。";
    }

    public int getCarInMeetingArea() {
        int res = 0;
        Node cur = head;
        while (cur != null) {
            if (cur instanceof MeetingArea && cur.left != null && cur.right != null) {
                res += ((MeetingArea) cur).getCarsNum();
            }
            cur = cur.right;
        }
        return res;
    }

    public Road() {
    }

    public Road(int[] meetingAreaInfo, int[] roadLength, ArrayList<Car> cars) {
        roadMap = new HashMap<>();
        MeetingArea head = new MeetingArea(2, 0);
        roadMap.put(0, head);
        this.head = head;
        MeetingArea left = head;
        int len = 1;
        for (int i = 0; i < meetingAreaInfo.length; i++) {
            MeetingArea right = new MeetingArea(meetingAreaInfo[i], len + roadLength[i]);
            Section section = new Section(roadLength[i], left, right, len, len + roadLength[i] - 1);
            left.right = section;
            section.left = left;
            section.right = right;
            right.left = section;
            left = right;

            for (int j = len; j < len + roadLength[i]; j++) {
                roadMap.put(j, section);
            }
            // x值
            len += roadLength[i];
            roadMap.put(len, right);
            cars.get(i).addPosition(len);
            len++;
            left.addCar(cars.get(i));
        }
        // 最后大车厂
        MeetingArea right = new MeetingArea(100, len + roadLength[roadLength.length - 1]);
        Section section = new Section(roadLength[roadLength.length - 1], left, right, len, len + roadLength[roadLength.length - 1] - 1);
        left.right = section;
        section.left = left;
        section.right = right;
        right.left = section;

        for (int j = len; j < len + roadLength[roadLength.length - 1]; j++) {
            roadMap.put(j, section);
        }
        // x值
        len += roadLength[roadLength.length - 1];
        roadMap.put(len, right);

    }

    //  路段
    class Section extends Node {
        private int len;

        // 方向0表示左，1表示右.-1表示无车
        private int direct;

        //
        private int carNum;

        // 开始结束标志
        private int start;
        private int end;

        public Section() {
        }

        public Section(int len, MeetingArea left, MeetingArea right, int start, int end) {
            this.len = len;
            this.left = left;
            this.right = right;
            this.direct = -1;
            this.start = start;
            this.end = end;
            this.carNum = 0;
        }

        @Override
        public String toString() {
            return "道路长度:" + len + "范围区间[" + start + ", " + end + "]" + "\n" + right;
        }

        public int getDirect() {
            return direct;
        }

        public void setDirect(int direct) {
            this.direct = direct;
        }

        public int getCarNum() {
            return carNum;
        }

        public void addCar() {
            this.carNum++;
        }

        public void removeCar() {
            this.carNum--;
            if (carNum == 0) {
                this.direct = -1;
            }
        }

        public int getLen() {
            return len;
        }

        public void setLen(int len) {
            this.len = len;
        }


        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }

    // 会车区class
    public class MeetingArea extends Node {
        // 会车区大小
        private int size;

        // 会车区车辆
        private HashSet<Car> cars;

        private int pos;


        public int getCarsNum() {
            return cars.size();
        }

        public void removeCar(Car c) {
            cars.remove(c);
        }

        public void addCar(Car c) {
            cars.add(c);
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.left == null) {
                sb.append("采矿区,");
            } else if (this.right == null) {
                sb.append("卸矿区,");
            } else {
                sb.append("道路会车区,");
            }
            sb.append("所在位置:").append(pos);
            sb.append("  可停车 ").append(size).append(" 辆。已停车 ").append(cars.size()).append(" 辆。");
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

        public MeetingArea(int size, int pos) {
            this.size = size;
            cars = new HashSet<>();
            this.pos = pos;
        }

        public int getSize() {
            return size;
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
