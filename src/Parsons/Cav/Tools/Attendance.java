package Parsons.Cav.Tools;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Attendance{

    public static void main(String[] args) throws IOException, InterruptedException{
        File file = new File("Results.txt");
        ArrayList<Member> members = new ArrayList<>();
        String name = "";
        double time = 0;
        int status;

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            Scanner scanner = new Scanner(file);

            String input = "";
            while(scanner.hasNextLine()){
                input = scanner.next();

                if(input.contains("7Cav")){
                    name = input; // Grab Name
                    time = toMins(scanner.next()); // Grab Duration

                    //Check for existing members
                    status = checkExistingMembers(members, name);
                    if(status == -1){
                        members.add(new Member(name, time)); //Add Member to List
                    }else{
                        //Update existing member
                        members.get(status).addTime(time);
                    }
                }else{
                    System.out.println("Unknown data");
                }

            }
        }catch(FileNotFoundException ex){
            System.out.println("File not found");
        }catch(NoSuchElementException e){
        }

        printInfo(members);
//        File raw = new File("data.txt");
//        cleanFile(raw);
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
        File output = new File("Final.txt");
        PrintWriter writer = new PrintWriter(output);
        writer.println("Event Roster");
        ArrayList<Member> CreditList = new ArrayList<>();
        ArrayList<Member> noCreditList = new ArrayList<>();

        for(int i = 0; i < members.size(); i++){
            if((members.get(i).getTime() >= 60) && (!(members.get(i).getName().contains("AR")) && !(members.get(i).getName().contains("RET")))){
                CreditList.add(members.get(i));
            }else{
                noCreditList.add(members.get(i));
            }
        }

        for(int i = 0; i < CreditList.size(); i++){//Get Credit
            writer.println("Name: " + CreditList.get(i).getName());
            writer.println("Total Minutes Played: " + CreditList.get(i).getTime());
            writer.println("Credit: YES");
            writer.println();
        }

        writer.println("------------------------------------------------");
        writer.println("NO CREDIT LIST");
        for(int i = 0; i < noCreditList.size(); i++){//Don't Get Credit
            writer.println("Name: " + noCreditList.get(i).getName());
            writer.println("Total Minutes Played: " + noCreditList.get(i).getTime());
            writer.println("Credit: NO");
            writer.println();
        }
        writer.println("------------------------------------------------\n\n");

        writer.println("ANALYSIS");
        writer.println("Total Players: " + members.size());
        writer.println("Valid Players: " + (members.size() - noCreditList.size()));
        writer.println("Not Valid Players: " + noCreditList.size());
        writer.close();
    }
}
