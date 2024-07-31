package ru.javawebinar.topjava;

public class TestGetText {

    private final String message;
    private final int number;


    public static void getText (String text) {
        System.out.println(text);
    }

    TestGetText(String message, int number) {
        this.message = message;
        this.number = number;
    }
    public void getSomethingMore() {
        System.out.println("Your message is: " + message);
        System.out.println("Your number is: " + number);
    }
}
