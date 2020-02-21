package model;

import java.util.Date;

public class Tip {

    private String userId;
    private String businessId;
    private String text;
    private int complementCount;
    private Date date;

    public Tip(String userId, String businessId, String text, int complementCount, Date date) {
        this.userId = userId;
        this.businessId = businessId;
        this.text = text;
        this.complementCount = complementCount;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getComplementCount() {
        return complementCount;
    }

    public void setComplementCount(int complementCount) {
        this.complementCount = complementCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Tip{" +
                "userId='" + userId + '\'' +
                ", businessId='" + businessId + '\'' +
                ", text='" + text + '\'' +
                ", complementCount=" + complementCount +
                ", date=" + date +
                '}';
    }
}
