package Parsons.Cav.Tools;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Utility{

    protected static int toMins(String s){
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    protected static int checkExistingMembers(ArrayList<Member> array, String name){
        int index = -1; //-1 new member, otherwise return index
        for(int i = 0; i < array.size(); i++){
            if(array.get(i).getName().equals(name)){
                index = i;
                break;
            }
        }
        return index;
    }

    protected static void printList(ArrayList<Member> list, PrintWriter writer){
        for(int i = 0; i < list.size(); i++){
            writer.println("Name: " + list.get(i).getName());
            writer.println("Total Minutes Played: " + list.get(i).getTime());
            writer.println("Credit: " + list.get(i).getCredit());
            writer.println();
        }
    }
}
