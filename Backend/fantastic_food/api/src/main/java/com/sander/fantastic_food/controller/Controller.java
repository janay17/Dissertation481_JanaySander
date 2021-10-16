package com.sander.fantastic_food.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface Controller<Type, Key> {
    ResponseEntity<Type> getByID(@PathVariable("id") Key id);
    ResponseEntity<Type> saveNew(@RequestBody Type value);
    ResponseEntity<Type> updateExisting(@RequestBody Type value);
    ResponseEntity<Boolean> delete(@PathVariable("id") Key id);
}
