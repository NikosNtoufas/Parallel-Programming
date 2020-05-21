import java.util.Scanner;

class ExitThread extends Thread
{

    public ExitThread()
    {

    }


    public void run()
    {
        Scanner s=new Scanner(System.in);

        System.out.println("Press enter to exit.....");

        s.nextLine();
        System.exit(0);
    }
}