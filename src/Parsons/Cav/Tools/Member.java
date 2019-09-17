package Parsons.Cav.Tools;

public class Member{

    private String name;
    private double time;

    public Member(String name, double time){
        this.name = name;
        this.time = time;
    }

    public void addTime(double input){
        this.time += input;
    }

    public String getName(){
        return name;
    }

    public double getTime(){
        return time;
    }

}
