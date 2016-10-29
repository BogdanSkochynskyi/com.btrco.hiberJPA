package com.btrco.hiberJPA.utils;

public interface Validator<T> {

	boolean isValid(T t);
}
