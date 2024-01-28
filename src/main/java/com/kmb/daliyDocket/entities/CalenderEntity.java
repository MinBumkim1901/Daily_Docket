package com.kmb.daliyDocket.entities;

import java.util.Date;
import java.util.Objects;

public class CalenderEntity {
    private int index;
    private String email;
    private String content;
    private Date startDate;
    private Date endDate;

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

    public String getContent() {
        return content;
    }

    public CalenderEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public CalenderEntity setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public CalenderEntity setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }
}
