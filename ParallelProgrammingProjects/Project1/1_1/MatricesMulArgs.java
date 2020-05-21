class MatricesMulArgs {



    public static void main(String[] args) {

        boolean debug = false;
		/*
			C=AB
			   -------- s ---------
			A=|a0.0 a0.1 .... a0.s| |
			  |a1.0 a1.1 .... a1.s| m
			  |...................| |
			  |am.0 am.1 .... am.s| |


			  -------- n ---------
			A=|a0.0 a0.1 .... a0.n| |
			  |a1.0 a1.1 .... a1.n| s
			  |...................| |
			  |as.0 as.1 .... as.n| |


			   -------- s ---------
			c=|a0.0 a0.1 .... a0.n| |
			  |a1.0 a1.1 .... a1.n| m
			  |...................| |
			  |am.0 am.1 .... am.n| |

		*/
        int m = 0;
        int n = 0;
        int s = 0;
        int numberOfThreads = 0;
        if (args.length != 4) {
            System.out.println("Usage: java ParSqrtArgsRandTime <m> <n> <s> <number of threads>");
            System.exit(1);
        }

        ExitThread exitThread = new ExitThread();
        exitThread.start();

        try {
            m = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
            s = Integer.parseInt(args[2]);
            numberOfThreads = Integer.parseInt(args[3]);

        } catch (NumberFormatException nfe) {
            System.out.println("Integer argument expected");
            System.exit(1);
        }

        if (numberOfThreads <= 0 || m <= 0 || n <= 0 || s <= 0) {
            System.out.println("m,n,s and number of threads should be positive integer");
            System.exit(1);
        }
        if ((m *n)% numberOfThreads != 0) {
            System.out.println("(m*n) % number of threads must be equal to zero");
            System.exit(1);
        }

        int cellsPerThread = (m*n) / numberOfThreads;

        int[][] matrix1 = new int[m][s];
        int[][] matrix2 = new int[s][n];

        //initialize two tables
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < s; j++) {
                matrix1[i][j] = i;
            }
        }
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < n; j++) {
                matrix2[i][j] =  i+j;
            }
        }

        // get current time
        long start = System.currentTimeMillis();

       

        MatricesMulThread threads[] = new MatricesMulThread[numberOfThreads];
        int startX=0;
        int startY=0;
        int cellCount=0;
        int threadCounter=0;
        for (int x = 0; x < m; x++) {
            for(int y=0; y< n; y++) {
                cellCount++;
                if (cellCount == (m * n) / numberOfThreads) {
                    threads[threadCounter] = new MatricesMulThread(matrix1, matrix2,
                            startX, x, startY, y, cellsPerThread, m, n, s);
                    threads[threadCounter].start();
                    threadCounter++;
                    cellCount = 0;
                    startX = x < m ? x : x + 1;
                    startY = y < n ? y : y + 1;
                }
            }
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Exception:" + e);
            }
        }

	long elapsedTimeMillis = System.currentTimeMillis() - start;
	System.out.println("time in ms = " + elapsedTimeMillis);


	if(debug)
        {
            //Print output table
            System.out.println("Results:\n");

            int cells=0;
            threadCounter=0;
            int x=0;
            for(int i=0; i<m; i++)
            {
                int y=0;
                for(int j=0; j<n; j++)
                {
                    if(cells==cellsPerThread)
                    {
                        //move to next thread
                        threadCounter++;
                        cells=0;
                    }
                    System.out.print(threads[threadCounter].table[x][y]+ " ");
                    cells++;
                }
                System.out.println();
            }
        }
    }
}