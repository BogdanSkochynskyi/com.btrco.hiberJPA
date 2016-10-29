package com.btrco.hiberJPA.exceptions;

public class InvalidNumberException extends Exception  {

	public InvalidNumberException(int number) {

		super(number + " is invalid, please use positive numbers bigger than 0");

	}
}
