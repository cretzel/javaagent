package de.nick.agenttest;

/**
 * Created by cretzel on 07.03.18.
 */
public class Test {

    private String name;

    public Test(String name) {
        this.name = name;
    }

    public void doIt() {
        System.out.println("My name is " + name);
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
