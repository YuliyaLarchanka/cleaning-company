package by.larchanka.tiptopcleaning.entity;

public enum OrderStatus {
    PROCESSING, COMPLETE, ON_HOLD, CANCELLED;

    public String getName() {
        return super.toString();
    }
    }
