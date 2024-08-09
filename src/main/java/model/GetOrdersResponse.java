package model;

import java.util.List;

public class GetOrdersResponse {
    private boolean success;
    private List<ShortOrderInfo> orders;
    private Integer total;
    private Integer totalToday;
    private String message;

    public GetOrdersResponse() {
    }

    public GetOrdersResponse(boolean success, List<ShortOrderInfo> orders, Integer total, Integer totalToday, String message) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ShortOrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<ShortOrderInfo> orders) {
        this.orders = orders;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(Integer totalToday) {
        this.totalToday = totalToday;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
