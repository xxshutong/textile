package org.opentaps.example.entity.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.opentaps.example.entity.model.Example;
import org.opentaps.example.entity.model.ExampleItem;


@Entity(name = "ExampleItem")
@Table(name = "EXAMPLE_ITEM")
@IdClass(ExampleItemImpl.Id.class)
public class ExampleItemImpl implements ExampleItem {

    public static class Id {

        public String exampleId;
        public String exampleItemSeqId;

        @Override
        public int hashCode() {
            return (exampleId == null ? 0 : exampleId.hashCode()) ^ (exampleItemSeqId == null ? 0 : exampleItemSeqId.hashCode());
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Id)) {
                return false;
            }
            Id i = (Id) obj;
            return (exampleId == i.exampleId || (exampleId != null && exampleId.equals(i.exampleId)))
                && (exampleItemSeqId == i.exampleItemSeqId || (exampleItemSeqId != null && exampleItemSeqId.equals(i.exampleItemSeqId)));
        }
        @Override
        public String toString() {
            return String.format("%1$s:%2$s", exampleId, exampleItemSeqId);
        }

    }

    @Column(name = "EXAMPLE_ITEM_SEQ_ID", nullable = false)
    private String exampleItemSeqId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "AMOUNT_UOM_ID")
    private String amountUomId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAMPLE_ID")
    private ExampleImpl example;


    public ExampleItemImpl() {
        // do noting
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAmountUomId() {
        return amountUomId;
    }

    public void setAmountUomId(String amountUomId) {
        this.amountUomId = amountUomId;
    }

    public String getExampleItemSeqId() {
        return exampleItemSeqId;
    }

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = (ExampleImpl) example;
    }

}
