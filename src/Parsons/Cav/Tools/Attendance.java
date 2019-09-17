package Parsons.Cav.Tools;

import java.io.*;
import java.util.*;

public class Attendance{

    public static void main(String[] args) throws IOException{
        File file = new File("Results.txt");
        ArrayList<Member> members = new ArrayList<>();
        String name = "";
        double time = 0;

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            Scanner scanner = new Scanner(file);

            String input = "";
            while(scanner.hasNextLine()){
                input = scanner.next();

                if(input.contains("7Cav")){
                    name = input;
                }else{
                    time = toMins(input);
                }
                int status = checkExistingMembers(members, name);
                if(status == -1){
                    members.add(new Member(name, time)); //Add Member to List
                }else{
                    //Update existing member
                    members.get(status).addTime(time);
                }

            }
        }catch(FileNotFoundException ex){
            System.out.println("File not found");
        }catch(NoSuchElementException e){
        }

        printInfo(members);
    }

    private static int toMins(String s){
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    private static int checkExistingMembers(ArrayList<Member> array, String name){
        int index = -1; //-1 new member, otherwise return index
        for(int i = 0; i < array.size(); i++){
            if(array.get(i).getName().equals(name)){
                index = i;
                break;
            }
        }
        return index;
    }

    private static void printInfo(ArrayList<Member> members) throws FileNotFoundException{

        String credit;

        File output = new File("Final.txt");
        PrintWriter writer = new PrintWriter(output);
        writer.println("Event Roster:\n\n");
        System.out.println("Total Players: " + members.size());
        for(int i = 0; i < members.size(); i++){
            if(members.get(i).getTime() >= 60){
                credit = "YES";
            }else{
                credit = "NO";
            }
            writer.println("Name: " + members.get(i).getName());
            writer.println("Total Minutes Played: " + members.get(i).getTime());
            writer.println("Credit: " + credit);
            writer.println("");
        }
        writer.close();
    }
}
