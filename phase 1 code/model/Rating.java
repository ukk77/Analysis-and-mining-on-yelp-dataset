package model;

public class Rating {
    private String businessId;
    private float star;
    private int reviewCount;

    public Rating(String businessId, float star, int reviewCount) {
        this.businessId = businessId;
        this.star = star;
        this.reviewCount = reviewCount;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "businessId='" + businessId + '\'' +
                ", star=" + star +
                ", reviewCount=" + reviewCount +
                '}';
    }
}
