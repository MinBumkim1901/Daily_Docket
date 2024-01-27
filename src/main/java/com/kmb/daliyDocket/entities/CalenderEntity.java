package com.kmb.daliyDocket.entities;

import java.util.Date;
import java.util.Objects;

public class CalenderEntity {
    private int index;
    private String email;
    private String scheduleText;
    private Date start_date;
    private Date end_date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalenderEntity)) return false;
        CalenderEntity that = (CalenderEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public int getIndex() {
        return index;
    }

    public CalenderEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CalenderEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getScheduleText() {
        return scheduleText;
    }

    public CalenderEntity setScheduleText(String scheduleText) {
        this.scheduleText = scheduleText;
        return this;
    }

    public Date getStart_date() {
        return start_date;
    }

    public CalenderEntity setStart_date(Date start_date) {
        this.start_date = start_date;
        return this;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public CalenderEntity setEnd_date(Date end_date) {
        this.end_date = end_date;
        return this;
    }
}
