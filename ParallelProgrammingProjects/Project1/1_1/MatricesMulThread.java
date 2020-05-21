class MatricesMulThread extends Thread
{
    public int [][] table;
    private int[][] m1;
    private int[][] m2;
    private int xFrom;
    private int xTo;
    private int yFrom;
    private int yTo;
    private int totalCells;
    private int n1;
    private int s1;

    //
    public MatricesMulThread(int [][] array1,int [][] array2,
                             int startX,int endX,int startY,int endY, int cellPerThreads,int m,int n,int s)
    {
        xFrom = startX;
        xTo = endX;
        yFrom = startY;
        yTo = endY;

        //set the size of the table
        int sizeX = (xTo <= xFrom) ? m : xTo-xFrom+1;
        int sizeY = (yTo <= yFrom) ? n : yTo-yFrom+1;

        table =new int[sizeX][sizeY];
        m1 = array1;
        m2 = array2;
        totalCells = cellPerThreads;
        n1 = n;
        s1 = s;
    }

    //calculate #cellPerThreas cells
    public void run()
    {
        //multiplying 2 matrices
        int x=0;
        int count=0;
        for(int i=xFrom; i<=xTo; i++){
            int y=0;
            for(int j=yFrom; j<n1; j++){
                if(count==totalCells)
                    return; // all thread's cells calculated
                for(int k=0; k<s1; k++)
                {
                    table[x][y]+=m1[i][k]*m2[k][j];

                }
                y++; // move to next column
                count++; //cell calculated

            }
            x++; //move to next row
        }

    }
}