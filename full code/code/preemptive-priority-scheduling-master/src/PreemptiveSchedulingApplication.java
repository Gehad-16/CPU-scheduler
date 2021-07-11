//import controller.InputHandler;
import controller.PreemptiveScheduling;
import model.GanttRecord;
import model.Process;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PreemptiveSchedulingApplication {
    private ArrayList<Process> processes;
    private Scanner input;

    public PreemptiveSchedulingApplication(){
        processes = new ArrayList<>();
        input = new Scanner(System.in);
    }
    public ArrayList<Process> getProcesses() {
        int id, priority, AT, BT;
        do{
            System.out.print("Enter process ID: ");
            while(!input.hasNextInt()){
                System.out.print("Error! Enter an integer number: ");
                input.next();
            }
            id = input.nextInt();

            System.out.print("Enter process arrival time: ");
            while(!input.hasNextInt()){
                System.out.print("Error! Enter an integer number: ");
                input.next();
            }
            AT = input.nextInt();
            System.out.print("Enter process burst time: ");
            while(!input.hasNextInt()){
                System.out.print("Error! Enter an integer number: ");
                input.next();
            }
            BT = input.nextInt();
            System.out.print("Enter process priority: ");
            while(!input.hasNextInt()){
                System.out.print("Error! Enter an integer number: ");
                input.next();
            }
            priority = input.nextInt();


            processes.add(new Process(id, priority, AT, BT));
            System.out.print("Do you want to continue? (y/n): ");
        }while(input.next().equals("y"));
        return processes;
    }
    public static ArrayList<Process> cloneInputProcesses(ArrayList<Process> processes){
        ArrayList<Process> processesCpy = new ArrayList<>();
        for(Process p : processes){
            processesCpy.add(new Process(p.getProcessID(),p.getPriority(),p.getArrivingTime(),p.getBurstTime()));
        }
        return  processesCpy;
    }

   // ############Main#############################################################
    public static void main(String[] args){

        System.out.println("***********choose your Schedule**********");

        System.out.println(" 1) FCFS Scheduling ");
        System.out.println(" 2) SJF Scheduling ");
        System.out.println(" 3) RR Scheduling ");
        System.out.println(" 4) Priority  Scheduling ");
        System.out.println(" 5) multilevel Scheduling ");
        int var = 0;

        do {
            Scanner sc = new Scanner(System.in);
            Scanner reader = new Scanner(System.in);
            int num;
            num = sc.nextInt();

            if (num == 1) {
                FCFS fcfs = new FCFS();
                fcfs.start();

            } else if (num == 2) {
                System.out.println("Please enter the number of processes");
                int PNum = sc.nextInt();
                System.out.println("Please enter the context switching");
                int CS = sc.nextInt();
                String ID[] = new String[PNum];
                int temp[] = new int[PNum];
                int Waiting_Time[] = new int[PNum];
                int Turn_Around_Time[] = new int[PNum];
                int Burst_Time[] = new int[PNum];
                int Arrival_Time[] = new int[PNum];
                String PName[] = new String[PNum];
                int Arrival[] = new int[PNum];
                int Burst[] = new int[PNum];
                int Complete_Time[] = new int[PNum];
                float Avg_Wating_Time = 0, Avg_Total_Time = 0;
                int T = 0;

                for (int i = 0; i < PNum; i++) {
                    System.out.println("enter process " + (i + 1) + " name:");
                    ID[i] = sc.next();
                    System.out.println("enter process " + (i + 1) + " arrival time:");
                    Arrival[i] = sc.nextInt();
                    System.out.println("enter process " + (i + 1) + " brust time:");
                    Burst[i] = sc.nextInt();
                }


                for (int i = 0; i < PNum; i++) {
                    temp[i] = Burst[i];
                }

                Arrays.sort(temp);

                for (int i = 0; i < PNum; i++) {
                    for (int j = 0; j < PNum; j++) {
                        if (Burst[j] == temp[i]) {
                            PName[i] = ID[j];
                            Burst_Time[i] = Burst[j];
                            Arrival_Time[i] = Arrival[j];
                        }

                    }
                }

                for (int i = 0; i < PNum; i++) {
                    if (i == 0) {
                        Turn_Around_Time[i] = Burst_Time[i];
                        Waiting_Time[i] = 0;
                        T += Burst_Time[i] + CS;
                    } else {
                        Complete_Time[i] = T + Burst_Time[i];
                        T += Burst_Time[i] + CS;
                        Turn_Around_Time[i] = Complete_Time[i] - Arrival_Time[i];
                        Waiting_Time[i] = Turn_Around_Time[i] - Burst_Time[i];
                        if (Turn_Around_Time[i] < 0)
                            Turn_Around_Time[i] = 0;
                        if (Waiting_Time[i] < 0)
                            Waiting_Time[i] = 0;
                    }
                }

                System.out.println("order  Waiting_Time   Turn_Around_Time ");

                for (int i = 0; i < PNum; i++) {
                    Avg_Wating_Time += Waiting_Time[i];
                    Avg_Total_Time += Turn_Around_Time[i];

                    System.out.println("  " + PName[i] + "         " + Waiting_Time[i] + "                 " + Turn_Around_Time[i]);
                }
                System.out.println("Average Turnaround  Time  is " + (float) (Avg_Total_Time / PNum));
                System.out.println("Average Waiting Time  is " + (float) (Avg_Wating_Time / PNum));
                sc.close();
            } else if (num == 3) {
                RoundRobin rr = new RoundRobin();
                rr.start();
            } else if (num == 4) {
                PreemptiveSchedulingApplication in = new PreemptiveSchedulingApplication();
                float avgWait = 0;
                float avgTurnAround = 0;
                ArrayList<Process> processes = in.getProcesses();

                ArrayList<Process> processesCpy = PreemptiveSchedulingApplication.cloneInputProcesses(processes);
                ArrayList<GanttRecord> gantt = new PreemptiveScheduling().getGantt(processes);
                System.out.println("Turn Around Time");
                for(Process p : processesCpy){
                    int turnAroundTime = PreemptiveScheduling.getTurnAroundTime(p, gantt);
                    System.out.println("P" + p.getProcessID()+": t = " + turnAroundTime);
                    avgTurnAround += turnAroundTime;
                }
                avgTurnAround = avgTurnAround / processesCpy.size();


                System.out.println("Waiting Time");
                for(Process p : processesCpy){
                    int waitingTime = PreemptiveScheduling.getWaitingTime(p, gantt);
                    System.out.println("P" + p.getProcessID()+": t = " + waitingTime);
                    avgWait += waitingTime;
                }
                avgWait = avgWait / processesCpy.size();

                System.out.println("Average Turn Around Time : " + avgTurnAround);
                System.out.println("Average Waiting Time : " + avgWait);



            } else if (num == 5) {
                multi_level_3 ML = new multi_level_3();
                ML.start();
            }
            System.out.println(" \n to continue enter 6  ");
            var = sc.nextInt();

            if(var!=6)
            {
                System.out.println("  ***** (((((end the program ****** thank you))))) ******  ");
            }
        } while (var == 6);


    }



    }


