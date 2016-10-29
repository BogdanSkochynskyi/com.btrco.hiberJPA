package com.btrco.hiberJPA.exceptions;

public class RowsAmountException extends Exception  {

	public RowsAmountException(int number) {

		super("Amount of rows in the table less than " + number);

	}
}
