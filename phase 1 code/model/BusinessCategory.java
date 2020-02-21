package model;

public class BusinessCategory {
    private String BusinessId;
    private int CategoryId;

    public BusinessCategory(String businessId, int categoryId) {
        BusinessId = businessId;
        CategoryId = categoryId;
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    @Override
    public String toString() {
        return "BusinessCategory{" +
                "BusinessId='" + BusinessId + '\'' +
                ", CategoryId=" + CategoryId +
                '}';
    }
}
