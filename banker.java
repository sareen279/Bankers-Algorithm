import java.util.*;

public class banker
{
	public static void main(String[] args)
	{
		int p,r,pos = 0;
		boolean flag = false,safe = true;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the number of processes:- ");
		p = s.nextInt();
		System.out.println("Enter the number of resources:- ");
		r = s.nextInt();
		int al[][] = new int[p][r];
		int mx[][] = new int[p][r];
		int nd[][] = new int[p][r];
		int rs[] = new int[r];
		int ss[] = new int[p];
		int ss1[] = new int[p];
		int rq[] = new int[r];
		boolean chk[] = new boolean[p];
		boolean chk1[] = new boolean[p];
		for(int i = 0;i < r;i++)
		{
			System.out.println("Enter the instances of resources at the beginning - " + (i + 1));
			rs[i] = s.nextInt();
		}
		for(int i = 0;i < p;i++)
		{
			for(int j = 0;j < r;j++)
			{
				System.out.println("Enter 'ALLOCATED' resources for resource -" + (j + 1) + "for process -" + (i +1));
				al[i][j] = s.nextInt();
				rs[j] = rs[j] - al[i][j];
			}
		}
		for(int i = 0;i < p;i++)
		{
			for(int j = 0;j < r;j++)
			{
				System.out.println("Enter 'MAXIMUM' resources for resource -" + (j + 1) + "for process -" + (i +1));
				mx[i][j] = s.nextInt();
				nd[i][j] = mx[i][j] - al[i][j];
			}
		}
		exec(al,mx,nd,rs,ss,chk,p,r,pos,flag,safe);
		if(safe)
		{
			System.out.println("Safe sequence is - ");
			for(int i = 0;i < p;i++)
			{
				System.out.println(ss[i]);
			}
		}
		else
			System.out.println("DeadLock , unsafe");
		System.out.println("Do you want to add any request? (Y/N)");
		if(s.next().charAt(0) == 'Y')
		{
			System.out.println("Enter the process number -");
			int p1 = s.nextInt();
			for(int j = 0;j < r;j++)
			{
				System.out.println("Enter new resource requests for resource - " + (j+1));
				rq[j] = s.nextInt();
			}
			boolean flag1 = false;
			for(int j = 0;j < r;j++)
			{
				if((rq[j] < nd[p1][j]) && (rq[j] < rs[j]))
					flag1 = true;
			}
			if(flag1)
			{
				for(int j = 0;j < r;j++)
				{
					rs[j] = rs[j] - rq[j];
					al[p1][j] = al[p1][j] + rq[j];
					nd[p1][j] = nd[p1][j] - rq[j];
				}
				System.out.println("Doing allocation...");
				int pos1 = 0;
				flag1 = false;
				boolean safe1 = true;
				exec(al,mx,nd,rs,ss1,chk1,p,r,pos1,flag1,safe1);
				if(safe)
				{
					System.out.println("Safe sequence is - ");
					for(int i = 0;i < p;i++)
					{
						System.out.println(ss1[i]);
					}
				}
			}
			else
				System.out.println("Resource Allocation not possible");
		}
	}
	public static void exec(int al[][],int mx[][],int nd[][],int rs[],int ss[],boolean chk[],int p,int r,int pos,boolean flag,boolean safe)
	{
		while((pos < p) && safe)
		{
			for(int i = 0;i < p;i++)
			{
				if(!chk[i])
				{
					flag = true;
					for(int j = 0;j < r;j++)
					{
						if(nd[i][j] > rs[j])
							flag = false;
					}
					if(flag)
					{
						for(int j = 0;j < r;j++)
							rs[j] = rs[j] + al[i][j];
						ss[pos] = (i+1);
						pos++;
						chk[i] = true;
						break;
					}
					else if(i == (p-1))
					{
						System.out.println("Deadlock!");
						safe = false;
					}
				}
			}
		}
	}
}
