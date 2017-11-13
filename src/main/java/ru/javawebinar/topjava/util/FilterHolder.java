package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class FilterHolder {
    private LocalTime beginTime;
    private LocalTime endTime;
    private LocalDate beginDate;
    private LocalDate endDate;

    public FilterHolder(LocalTime beginTime, LocalTime endTime, LocalDate beginDate, LocalDate endDate) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isClear() {
        return beginDate == null && endDate == null && beginTime == null && endTime == null;
    }
}
