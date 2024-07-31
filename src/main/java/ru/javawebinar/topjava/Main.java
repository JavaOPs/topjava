package ru.javawebinar.topjava;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        TestGetText.getText("Right now, im last time testing Git!!");
        TestGetText testTest = new TestGetText("Its just a messege", 100);
        testTest.getSomethingMore();
    }
}
