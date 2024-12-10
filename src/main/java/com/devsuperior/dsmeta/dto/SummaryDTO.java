package com.devsuperior.dsmeta.dto;

public class SummaryDTO {
    private String sellerName;
    private Double total;

    public SummaryDTO(){
    }

    public SummaryDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setName(String name) {
        this.sellerName = name;
    }

    public Double getTotal() {
        return total;
    }
}
