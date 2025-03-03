package com.example.bookstore.entities;

public enum Status {
    PENDING {
        @Override
        public boolean isNextStatePossible(Status status) {
            return status == Status.IN_PROGRESS  || status == Status.CANCELED;
        }
    }, IN_PROGRESS {
        @Override
        public boolean isNextStatePossible(Status status) {
            return status == Status.FINISHED;
        }
    }, DELAYED {
        @Override
        public boolean isNextStatePossible(Status status) {
            return status == Status.FINISHED;
        }
    }, FINISHED {
        @Override
        public boolean isNextStatePossible(Status status) {
            return false;
        }
    }, CANCELED {
        @Override
        public boolean isNextStatePossible(Status status) {
            return false;
        }
    };

    public abstract boolean isNextStatePossible(Status status);
    }
