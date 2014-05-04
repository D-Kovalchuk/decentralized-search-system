package com.fly.house.service.converter;

/**
 * Created by dimon on 04.05.14.
 */
public interface Converter<T, E> {

    T convert(E entity);

}