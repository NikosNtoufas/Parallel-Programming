/*
 * HelloEvent.java
 *
 * Event driven thread creation and termination
 * Use of interrupts
 *
 */
import javax.sound.midi.SysexMessage;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HelloEvent {

    public static boolean flag = true;
    public static int numThreads=1;
    public final static int maxNumOfThreads = 100;
    public static Thread[] threads = new Thread[maxNumOfThreads];

    public static void main(String[] args) {

        int i = 0;
        Thread threadExit = new Thread(new Exit());
       // threadExit.start();
        Thread incDecThread = new Thread(new IncDecThreads());
        incDecThread.start();
        System.out.println("Press + to add thread , - to remove a thread and stop to stop execution!");
        try
        {
            TimeUnit.SECONDS.sleep(2);
        }
        catch (Exception e){

        }

        while(flag)
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(200);
            }
            catch (Exception e){

            }
        }
        System.out.println("Execution stopped!");
        System.out.println("Done starting threads");

        /* stop threads */
        try {
            //threadExit.join();
            incDecThread.join();
        }
        catch (InterruptedException e) {
            System.out.println("This should not happen");
        }

        System.out.println("Number of threads used: "+ numThreads);

    }


    private static void pressAnyKeyToContinue() {
        System.out.println("Press Enter to start/stop a thread");
        try {
            System.in.read();
        }  catch(Exception e) {}  
    }
    /* inner class containing code for each thread to execute */

    private static class Inner implements Runnable {

        private int myID;

        public Inner(int myID) {
            this.myID = myID;
        }

        public void run() {
            while (flag) {
            	System.out.println("**** hello from " + myID);
            	try {
                      Thread.sleep(100);
            	} catch (InterruptedException e) {
                      System.out.println("**** good bye from " +myID );
                      break;
                }
            }
       }

    }

    private static class Exit implements Runnable{

        public Exit(){

        }
        public void run() {
            Scanner s = new Scanner(System.in);
            System.out.println("Press enter any time to stop execution.....");
            s.nextLine();

            flag = false;

        }
    }

    private static class IncDecThreads implements  Runnable{
        public IncDecThreads()
        {

        }
        public void run()
        {

            while(flag)
            {
                Scanner s = new Scanner(System.in);
                char x = s.next().charAt(0);

                if(x=='+' && numThreads<maxNumOfThreads) {
                    System.out.println("Starting thread " + numThreads);
                    threads[numThreads] = new Thread(new Inner(numThreads));
                    threads[numThreads].start();
                    numThreads++;
                }
                if(x=='-'&& numThreads>0)
                {
                    try {
                        threads[numThreads].join();
                    }
                    catch (InterruptedException e) {
                        System.out.println("This should not happen");
                        flag = false;
                    }

                    numThreads--;
                }
                if(x=='q')
                    flag=false;

            }
            
        }


    }
}
