package org.opentaps.example.entity.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.opentaps.example.entity.model.Example;
import org.opentaps.example.entity.model.ExampleStatus;


@Entity(name = "ExampleStatus")
@Table(name = "EXAMPLE_STATUS")
@IdClass(ExampleStatusImpl.Id.class)
public class ExampleStatusImpl implements ExampleStatus {

    public static class Id {

        private String exampleId;
        private Date statusDate;

        @Override
        public int hashCode() {
            return (exampleId == null ? 0 : exampleId.hashCode()) ^ (statusDate == null ? 0 : statusDate.hashCode());
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
                && (statusDate == i.statusDate || (statusDate != null && statusDate.equals(i.statusDate)));
        }
        @Override
        public String toString() {
            return String.format("%1$s at %2$tY-%2$tm-%2$td %2$tT", exampleId, statusDate);
        }
        
    }

    @Column(name = "STATUS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDate;

    @Column(name = "STATUS_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusEndDate;

    @Column(name = "STATUS_ID")
    private String statusId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAMPLE_ID")
    private ExampleImpl example;


    public ExampleStatusImpl() {
        // do nothing
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getStatusEndDate() {
        return statusEndDate;
    }

    public void setStatusEndDate(Date statusEndDate) {
        this.statusEndDate = statusEndDate;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = (ExampleImpl) example;
    }

}
