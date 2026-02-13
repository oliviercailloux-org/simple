package io.github.oliviercailloux.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		sayHello();
	}

	public static void sayHello() {
		System.out.println("Hello World!");
	}
}
