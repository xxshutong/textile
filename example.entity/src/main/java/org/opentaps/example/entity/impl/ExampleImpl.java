package org.opentaps.example.entity.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.opentaps.example.entity.model.Example;
import org.opentaps.example.entity.model.ExampleType;


@Entity(name = "Example")
@Table(name = "EXAMPLE")
public class ExampleImpl implements Example {

    @Id
    @Column(name = "EXAMPLE_ID", nullable = false)
    private String exampleId;

    @Column(name = "EXAMPLE_TYPE_ID", nullable = false)
    private String exampleTypeId;

    @Column(name = "STATUS_ID", nullable = false)
    private String statusId;

    @Column(name = "EXAMPLE_NAME")
    private String exampleName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LONG_DESCRIPTION")
    private String longDescription;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "EXAMPLE_SIZE")
    private Long exampleSize;

    @Column(name = "EXAMPLE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exampleDate;

    @Column(name = "ANOTHER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date anotherDate;

    @Column(name = "ANOTHER_TEXT")
    private String anotherText;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAMPLE_TYPE")
    private ExampleTypeImpl type;


    public ExampleImpl() {
        // do nothing
    }

    public String getExampleId() {
        return exampleId;
    }

    public String getExampleTypeId() {
        return exampleTypeId;
    }

    public void setExampleTypeId(String exampleTypeId) {
        this.exampleTypeId = exampleTypeId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getExampleName() {
        return exampleName;
    }

    public void setExampleName(String exampleName) {
        this.exampleName = exampleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getExampleSize() {
        return exampleSize;
    }

    public void setExampleSize(Long exampleSize) {
        this.exampleSize = exampleSize;
    }

    public Date getExampleDate() {
        return exampleDate;
    }

    public void setExampleDate(Date exampleDate) {
        this.exampleDate = exampleDate;
    }

    public Date getAnotherDate() {
        return anotherDate;
    }

    public void setAnotherDate(Date anotherDate) {
        this.anotherDate = anotherDate;
    }

    public String getAnotherText() {
        return anotherText;
    }

    public void setAnotherText(String anotherText) {
        this.anotherText = anotherText;
    }

    public ExampleType getType() {
        return type;
    }

    public void setType(ExampleType type) {
        this.type = (ExampleTypeImpl) type;
    }

}
