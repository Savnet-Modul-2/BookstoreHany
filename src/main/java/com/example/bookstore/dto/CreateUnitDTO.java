package com.example.bookstore.dto;

public class CreateUnitDTO {
    private Long bookId;
    private Integer numberOfUnits;
    private String publisher;
    private Integer maximumBookingDuration;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(Integer numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
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
