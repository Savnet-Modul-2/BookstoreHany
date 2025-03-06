package com.example.bookstore.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({BasicInfo.class, AdvancedInfo.class})
public interface ValidationOrder {
}
