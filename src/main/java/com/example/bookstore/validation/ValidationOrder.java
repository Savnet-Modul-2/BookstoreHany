package com.example.bookstore.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({BasicValidation.class, AdvancedValidation.class})
public interface ValidationOrder {
}
