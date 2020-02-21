package model;

public class User {

        private String id;
        private String name;
        private int reviewCount;
        private int fans;
        private float avgStars;

    public User(String id, String name, int reviewCount, int fans, float avgStars) {
        this.id = id;
        this.name = name;
        this.reviewCount = reviewCount;
        this.fans = fans;
        this.avgStars = avgStars;
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

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public float getAvgStars() {
        return avgStars;
    }

    public void setAvgStars(float avgStars) {
        this.avgStars = avgStars;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reviewCount=" + reviewCount +
                ", fans=" + fans +
                ", avgStars=" + avgStars +
                '}';
    }
}
