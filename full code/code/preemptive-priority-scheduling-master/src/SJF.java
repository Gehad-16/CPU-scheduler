
import java.util.*;
import java.util.Arrays;
public class SJF {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.println ("Please enter the number of processes");
        int PNum = sc.nextInt();
        System.out.println ("Please enter the context switching");
        int CS = sc.nextInt();
        String ID[] = new String[PNum];
        int temp[]=new int[PNum];
        int Waiting_Time[]=new int[PNum];
        int Turn_Around_Time[]=new int[PNum];
        int Burst_Time[]=new int[PNum];
        int Arrival_Time[]=new int[PNum];
        String PName[]=new String[PNum];
        int Arrival[] = new int[PNum];
        int Burst[] = new int[PNum];
        int Complete_Time[] = new int[PNum];
        float Avg_Wating_Time=0, Avg_Total_Time=0;
        int T=0;

        for(int i=0;i<PNum;i++)
        {
            System.out.println ("enter process " + (i+1) + " name:");
            ID[i] = sc.next();
            System.out.println ("enter process " + (i+1) + " arrival time:");
            Arrival[i] = sc.nextInt();
            System.out.println ("enter process " + (i+1) + " brust time:");
            Burst[i] = sc.nextInt();
        }


        for(int i=0;i<PNum;i++)
        {
            temp[i]=Burst[i];
        }

        Arrays.sort(temp);

        for (int i=0;i<PNum;i++)
        {
            for(int j=0;j<PNum;j++)
            {
                if(Burst[j]==temp[i])
                {
                    PName[i]=ID[j];
                    Burst_Time[i]=Burst[j];
                    Arrival_Time[i]=Arrival[j];
                }

            }
        }

        for(int i=0;i<PNum;i++)
        {
            if(i==0)
            {
                Turn_Around_Time[i]=Burst_Time[i];
                Waiting_Time[i]=0;
                T+=Burst_Time[i]+CS;
            }
            else
            {
                Complete_Time[i]=T+Burst_Time[i];
                T+=Burst_Time[i]+CS;
                Turn_Around_Time[i]=Complete_Time[i]-Arrival_Time[i];
                Waiting_Time[i]=Turn_Around_Time[i]-Burst_Time[i];
                if(Turn_Around_Time[i]<0)
                    Turn_Around_Time[i]=0;
                if(Waiting_Time[i]<0)
                    Waiting_Time[i]=0;
            }
        }

        System.out.println("order  Waiting_Time   Turn_Around_Time ");

        for(int i=0;i<PNum;i++)
        {
            Avg_Wating_Time+= Waiting_Time[i];
            Avg_Total_Time+= Turn_Around_Time[i];

            System.out.println("  "+PName[i]+"         "+Waiting_Time[i]+"                 "+Turn_Around_Time[i]);
        }
        System.out.println ("Average Turnaround  Time  is "+ (float)(Avg_Total_Time/PNum));
        System.out.println ("Average Waiting Time  is "+ (float)(Avg_Wating_Time/PNum));
        sc.close();
    }
}
