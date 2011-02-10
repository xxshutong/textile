package org.opentaps.example.entity.model;

import java.util.Date;


public interface ExampleStatus {

    public Date getStatusDate();

    public void setStatusDate(Date statusDate);

    public Date getStatusEndDate();

    public void setStatusEndDate(Date statusEndDate);

    public String getStatusId();

    public void setStatusId(String statusId);

    public Example getExample();

    public void setExample(Example example);
}
