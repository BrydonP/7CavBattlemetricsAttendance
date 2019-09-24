package Parsons.Cav.Tools;

import java.io.*;
import java.util.*;

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
                    time = Utility.toMins(scanner.next()); // Grab Duration

                    //Check for existing members
                    status = Utility.checkExistingMembers(members, name);
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
    }

    private static void printInfo(ArrayList<Member> members) throws FileNotFoundException{
        File output = new File("Final.txt");
        PrintWriter writer = new PrintWriter(output);
        writer.println("Event Roster\n\n");
        writer.println("------------------------------------------------");
        ArrayList<Member> creditList = new ArrayList<>();
        ArrayList<Member> noCreditList = new ArrayList<>();

        for(int i = 0; i < members.size(); i++){
            if((members.get(i).getTime() >= 60) && (!(members.get(i).getName().contains("AR")) && !(members.get(i).getName().contains("RET")))){
                members.get(i).setCredit("YES");
                creditList.add(members.get(i));
            }else{
                members.get(i).setCredit("NO");
                noCreditList.add(members.get(i));
            }
        }

        Utility.printList(creditList, writer);
        writer.println("------------------------------------------------");
        writer.println("NO CREDIT LIST");
        Utility.printList(noCreditList, writer);
        writer.println("------------------------------------------------\n\n");

        writer.println("ANALYSIS");
        writer.println("Total Players: " + members.size());
        writer.println("Valid Players: " + (members.size() - noCreditList.size()));
        writer.println("Not Valid Players: " + noCreditList.size());
        writer.close();
    }

}
