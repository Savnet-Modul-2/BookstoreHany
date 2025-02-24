package com.example.bookstore.dto;

public class UnitDTO {
    private Long id;
    private String publisher;
    private Integer maximumBookingDuration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getMaximumBookingDuration() {
        return maximumBookingDuration;
    }

    public void setMaximumBookingDuration(Integer maximumBookingDuration) {
        this.maximumBookingDuration = maximumBookingDuration;
    }
}
