package ru.otus.L051.testData;

import ru.otus.L051.testFramework.annotations.*;

public class AnnotationsTest {
	AnnotationsTest() {
		System.out.println("Call of the constructor");
	}

	@BeforeAll
	static void beforeAll() {
		System.out.println("BeforeAll");
	}

	/*@Contract(" -> fail")
	@BeforeAll
	static void beforeAll2() throws IllegalAccessException {throw new IllegalAccessException();
	}*/

	@BeforeEach
	void beforeEach() {
		System.out.println("BeforeEach");
	}

	@BeforeEach
	void beforeEach2() {
		System.out.println("BeforeEach3");
	}

	/*@Contract(" -> fail")
	@BeforeEach
	void beforeEach3() throws IllegalAccessException {throw new IllegalAccessException();}*/

	@Test
	void testOne() {
		System.out.println("testOne");
	}

	@Test
	void testTwo() {
		System.out.println("testTwo");
	}

	@AfterEach
	void afterEach() {
		System.out.println("AfterEach");
	}

	@AfterEach
	void afterEach2() {
		System.out.println("AfterEach2");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("AfterAll");
	}

	@AfterAll
	static void afterAll2() {
		System.out.println("AfterAll2");
	}
}