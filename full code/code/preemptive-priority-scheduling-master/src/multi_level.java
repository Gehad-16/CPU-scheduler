
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

public class multi_level {

    public static Queue<Integer> Ready_Queue=new LinkedList<>();
    public static Vector<String> execution_order=new Vector<String>();

    public  void start()
    {
        int context_switching;
        int quantum;
        int num_processes;
        String processes[];
        int brust_time[];
        int arrival_time[];
        int turnaround_time[];
        int completion_time[];
        int waiting_time[];
        int queue_num[];
        int queue_RR[];
        int queue_FCFS[];

        try (Scanner input = new Scanner(System.in))
        {
            System.out.println("How much is the quantum?");
            quantum=Integer.parseInt(input.nextLine());

            System.out.println("How much is the context switching?");
            context_switching=Integer.parseInt(input.nextLine());

            System.out.println("How many processes you want to add?");
            num_processes = Integer.parseInt(input.nextLine());
            processes=new String [num_processes];
            brust_time=new int[num_processes];
            arrival_time=new int[num_processes];
            turnaround_time=new int[num_processes];
            completion_time=new int[num_processes];
            waiting_time=new int[num_processes];
            queue_num=new int[num_processes];
            queue_RR=new int[num_processes];
            queue_FCFS=new int[num_processes];

            for(int i=0;i<num_processes;i++)
            {
                System.out.println("Enter process name");
                processes[i]=input.nextLine();

                System.out.println("Enter process arrival_time");
                arrival_time[i]=Integer.parseInt(input.nextLine());

                System.out.println("Enter process brust_time");
                brust_time[i]=Integer.parseInt(input.nextLine());

                System.out.println("Enter process queue_num");
                queue_num[i]=Integer.parseInt(input.nextLine());
            }


        }
    }


    public static void calc_waiting_time_RR(String process[],int wt_time[],int n ,int brusttime[],int context_switching,int quantum,int completion_time[],int arrival_time[] , int queue_num[]){
        Queue<Integer> FCFS_queue=new LinkedList<>();
        int remainding_time[] = new int[n];

        for(int i=0;i<wt_time.length;i++){
            remainding_time[i]= brusttime[i];
        }


        int t=0;
        int element=0;
        Queue<Integer>arriving=new LinkedList<>();
        for(int i=0;i<arrival_time.length;i++)
        {
            arriving.add(arrival_time[i]);
        }
        while(true)

        {
            //p1 p2 p3 t=0

            //0  1  2
            //3  2  4
            //2  1  2
            boolean done = true;
            while(!arriving.isEmpty())
            {
                if(arriving.peek()<=t)
                {
                    Ready_Queue.add(element);
                    element++;
                    arriving.remove();
                }
                else
                {
                    break;
                }
            }

            if(Ready_Queue.size()==0&& arriving.isEmpty())
            {
                t++;
                break;
            }
            int index=0;
            while(true)
            {
                index = Ready_Queue.remove();
                if (remainding_time[index] > 0) {
                    done = false;
                    if (queue_num[index] == 1) {
                        if (remainding_time[index] > quantum) {
                            t += quantum + context_switching;
                            remainding_time[index] -= quantum;
                            execution_order.add(process[index]);

                            ////////////////////////////////////////////////
                            while (!arriving.isEmpty()) {
                                if (arriving.peek() <= t) {
                                    Ready_Queue.add(element);
                                    element++;
                                    arriving.remove();

                                } else {
                                    break;
                                }
                            }
                            ////////////////////////////////////////////////
                            Ready_Queue.add(index);
                        } else {
                            t += remainding_time[index];
                            remainding_time[index] = 0;
                            execution_order.add(process[index]);
                            completion_time[index] = t;
                        }

                    } else if (queue_num[index]==2)
                    {
                        if(!FCFS_queue.isEmpty())
                        {
                            Ready_Queue.add(index);
                            index=FCFS_queue.remove();
                        }


                        if (remainding_time[index] > 0) {
                            done = false;

                            t += 1;//1
                            remainding_time[index] -= 1;//2

                            if(remainding_time[index]==0)
                            {
                                completion_time[index] = t;
                            }

                            ////////////////////////////////////////////////
                            while(!arriving.isEmpty())
                            {
                                if(arriving.peek()<=t)
                                {
                                    Ready_Queue.add(element);
                                    if(queue_num[Ready_Queue.peek()]==1)
                                    {
                                        FCFS_queue.add(index);
                                        t+=context_switching;
                                        element++;
                                        arriving.remove();
                                    }
                                    else if (queue_num[Ready_Queue.peek()]==2) {

                                        element++;
                                        arriving.remove();

                                    }


                                }
                                else
                                {
                                    break;
                                }
                            }
                            ////////////////////////////////////////////////



                        }


                    }
                }
                if (done == true)
                {
                    break;
                }

            }

        }
    }

}
