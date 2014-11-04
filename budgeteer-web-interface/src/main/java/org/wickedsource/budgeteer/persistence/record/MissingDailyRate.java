package org.wickedsource.budgeteer.persistence.record;

import java.util.Date;

public class MissingDailyRate {

    private long personId;

    private String personName;

    private Date startDate;

    private Date endDate;

    public MissingDailyRate(long personId, String personName, Date startDate, Date endDate) {
        this.personId = personId;
        this.personName = personName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}