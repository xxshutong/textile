package org.opentaps.example.entity.model;

import java.util.Date;


public interface Example {

    public String getExampleId();

    public String getStatusId();

    public void setStatusId(String statusId);

    public String getExampleName();

    public void setExampleName(String exampleName);

    public String getDescription();

    public void setDescription(String description);

    public String getLongDescription();

    public void setLongDescription(String longDescription);

    public String getComments();

    public void setComments(String comments);

    public Long getExampleSize();

    public void setExampleSize(Long exampleSize);

    public Date getExampleDate();

    public void setExampleDate(Date exampleDate);

    public Date getAnotherDate();

    public void setAnotherDate(Date anotherDate);

    public String getAnotherText();

    public void setAnotherText(String anotherText);

    public ExampleType getType();

    public void setType(ExampleType type);
}
