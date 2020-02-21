package model;

public class Business {

    private String id;
    private String name;
    private boolean isOpen;
    private boolean isTakeOut;

    public Business(String id, String name, boolean isOpen, boolean isTakeOut) {
        this.id = id;
        this.name = name;
        this.isOpen = isOpen;
        this.isTakeOut = isTakeOut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isTakeOut() {
        return isTakeOut;
    }

    public void setTakeOut(boolean takeOut) {
        isTakeOut = takeOut;
    }


    @Override
    public String toString() {
        return "Business{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isOpen=" + isOpen +
                ", isTakeOut=" + isTakeOut +
                '}';
    }
}
