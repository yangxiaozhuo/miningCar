import java.util.ArrayList;

public class Car {
    private String name;

    // 载荷
    private int category;

    // 最大载荷
    private int maxCategory;

    // 行驶方向:0表示去挖矿、1表示去卸矿
    private int direction;

    private ArrayList<Integer> position;

    public void setPosition(ArrayList<Integer> position) {
        this.position = position;
    }

    public ArrayList<Integer> getPosition() {
        return position;
    }

    @Override
    public String toString() {
        String direct = "";
        if (direction == 0) {
            direct = ",行驶方向:前往矿区 ";
        } else {
            direct = ",行驶方向:前往卸料区 ";
        }
        String po = "当前位置x:" + position.get(position.size() - 1);
        if (category == 0) {
            // car1:当前空载，最大载荷100；
            return name + ":当前空载,最大载荷" + maxCategory + direct + po;
        }
        // car2:当前载荷1，最大载荷1；
        return name + ":当前载荷" + category + ", 最大载荷" + maxCategory + direct + po;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getMaxCategory() {
        return maxCategory;
    }

    public void setMaxCategory(int maxCategory) {
        this.maxCategory = maxCategory;
    }
}
