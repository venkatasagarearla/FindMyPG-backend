package com.example.bookMyPg.exception;

public class ResourceNotFoundException extends RuntimeException{
  public ResourceNotFoundException(String message) {
	  super(message);
  }
}
