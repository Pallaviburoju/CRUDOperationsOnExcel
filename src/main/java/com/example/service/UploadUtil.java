package com.example.service;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
@Component
public class UploadUtil {
	public Supplier<Stream<Row>> getRowStreamSupplier(Iterable<Row> rows) {
		return () -> getStream(rows);
	}
	
	//spliterator is used for traversing the elements individually
	public <T> Stream<T> getStream(Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}
	
}
