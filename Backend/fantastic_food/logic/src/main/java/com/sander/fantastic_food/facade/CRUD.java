package com.sander.fantastic_food.facade;

import java.util.List;

public interface CRUD<Type, key> {
    Type findById(key id);
    List<Type> findAll();
    Type saveChanges(Type value);
    boolean deleteById(key id);
}
