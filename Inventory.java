import java.io.*;
class Inventory
{ public static void main(String a[])throws Exception
{ BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
int i,j,no;
System.out.println("ENTER THE NUMBER OF WEEKS.");
no=Integer.parseInt(br.readLine());
int demand[]={0,1,2,3,4};
float demandprob[]={0.10f,0.25f,0.35f,0.21f,0.09f};
float cumdemand[]=new float[demand.length];
int demandrda[]=new int[demand.length];
int lead[]={1,2,3};
float leadprob[]={0.60f,0.30f,0.10f};
float cumlead[]=new float[lead.length];
int leadrda[]=new int[lead.length];
for(i=0;i<demand.length;i++)
{ if(i==0)
{ cumdemand[i]=demandprob[i];
demandrda[i]=(int)(cumdemand[i]*100);
}
else
{ cumdemand[i]=cumdemand[i-1]+demandprob[i];
demandrda[i]=(int)(cumdemand[i]*100);
}
}
for(i=0;i<lead.length;i++)
{ if(i==0)
{ cumlead[i]=leadprob[i];
leadrda[i]=(int)(cumlead[i]*100);
}
else
{ cumlead[i]=cumlead[i-1]+leadprob[i];
leadrda[i]=(int)(cumlead[i]*100);
}
}
int end[]=new int[no*5];
int beg[]=new int[no*5];
int dem[]=new int[no*5];
int lea[]=new int[no];
int short1[]=new int[no*5];
int randomdem[]=new int[no*5];
int randomlead[]=new int[no];
int order[]=new int[no];
for(i=0;i<no*5;i++)
randomdem[i]=(int)(Math.random()*100d);
for(i=0;i<no;i++)
randomlead[i]=(int)(Math.random()*100d);
for(i=0;i<no*5;i++)
for(j=0;j<demandrda.length;j++)
{ if(randomdem[i]==0)
{ dem[i]=demand[demand.length-1];
break;
}
if(randomdem[i]<=demandrda[j])
{ dem[i]=demand[j];
break;
}
}
for(i=0;i<no;i++)
for(j=0;j<leadrda.length;j++)
{ if(randomlead[i]==0)
{ lea[i]=lead[lead.length-1];
break;
}
if(randomlead[i]<=leadrda[j])
{ lea[i]=lead[j];
break;
}
}
beg[0]=11;
int week=0;
int sho=0;
for(i=0;i<no*5;i++)
{ if((dem[i]+sho)<=beg[i])
{ end[i]=beg[i]-dem[i]-sho;
sho=0;
if(i<(no*5-1))
beg[i+1]+=end[i];
}
else
{ end[i]=0;
short1[i]=dem[i]+sho-beg[i];
sho=short1[i];
}
if((i%5)==4&&i<(no*5-1))
{ order[week]=11-end[i]+sho;
beg[i+lea[week]]+=order[week];
week++;
}
}
System.out.println("WEE\tDAY\tBEG\tRDE\tDEM\tEND\tSHO\tORD\tRLE\tLEA");
System.out.println("--------------------------------------------------------------------------");
week=0;
for(i=0;i<no*5;i++)
{ if((i%5)==0&&i>0)
week++;
System.out.println((week+1)+"\t"+((i%5)+1)+"\t"+beg[i]+"\t"+randomdem[i]+"\t"+dem[i]+"\
t"+end[i]+"\t"+short1[i]+"\t"+order[week]+"\t"+randomlead[week]+"\t"+lea[week]);
System.out.println("----------------------------------------------------------------------
--------");
}
}
}
