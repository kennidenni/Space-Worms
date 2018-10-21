package main;

public class Player {
    private String name;
    private int position;
    public Player(String name) {
        this.name = name;
        position = 0;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}
