package com.example.hms.util;

public interface ItemClickRecycler<T> {
    void delete(T item);
    void edit(T item);
}
