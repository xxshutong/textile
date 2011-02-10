package org.opentaps.example.entity.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.opentaps.example.entity.model.ExampleType;


@Entity(name = "ExampleType")
@Table(name = "EXAMPLE_TYPE")
public class ExampleTypeImpl implements ExampleType {

    @Id
    @Column(name = "EXAMPLE_TYPE_ID", nullable = false)
    private String exampleTypeId;

    @Column(name = "PARENT_TYPE_ID")
    private String parentTypeId;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_TYPE_ID")
    private ExampleTypeImpl parentType;


    public ExampleTypeImpl() {
        // do nothing
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExampleTypeId() {
        return exampleTypeId;
    }

    public ExampleTypeImpl getParentType() {
        return parentType;
    }

    public void setParentType(ExampleTypeImpl parentType) {
        this.parentType = parentType;
    }

}
