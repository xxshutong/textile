package org.opentaps.example.entity.model;


public interface ExampleItem {

    public String getDescription();

    public void setDescription(String description);

    public Double getAmount();

    public void setAmount(Double amount);

    public String getAmountUomId();

    public void setAmountUomId(String amountUomId);

    public String getExampleItemSeqId();

    public Example getExample();

    public void setExample(Example example);

}
