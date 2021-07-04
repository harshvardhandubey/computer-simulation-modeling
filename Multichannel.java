import java.io.*;
import java.util.*;
class Multichannel
{ public static void main(String a[])throws IOException
{BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
Random r=new Random(0);
int i,j;
int arrivaltime[]={1,2,3,4};
double arrivalprob[]={0.25,0.40,0.20,0.15};
double cumarrival[]=new double[arrivalprob.length];
int arrivalrda[]=new int[arrivalprob.length];
int ableservice[]={2,3,4,5};
double ableprob[]={0.30,0.28,0.25,0.17};
double cumable[]=new double[ableprob.length];
int ablerda[]=new int[arrivalprob.length];
int bakerservice[]={3,4,5,6};
double bakerprob[]={0.35,0.25,0.20,0.20};
double cumbaker[]=new double[bakerprob.length];
int bakerrda[]=new int[arrivalprob.length];
int randomarrival[]=new int[60/ableservice[0]];
int randomservice[]=new int[60/ableservice[0]];
int arrival[]=new int[60/ableservice[0]];
int service[]=new int[60/ableservice[0]];
int interarrival[]=new int[60/ableservice[0]];
int serviceable[]=new int[60/ableservice[0]];
int servicebaker[]=new int[60/ableservice[0]];
int ablestart[]=new int[60/ableservice[0]];
int bakerstart[]=new int[60/ableservice[0]];
int ableend[]=new int[60/ableservice[0]];
int bakerend[]=new int[60/ableservice[0]];
int ableprocess[]=new int [60/ableservice[0]];
int bakerprocess[]=new int[60/ableservice[0]];
int qtime[]=new int[60/ableservice[0]];
int ttime[]=new int[60/ableservice[0]];
int tempable=0,tempbaker=0;
float ableavgser=0,bakeravgser=0,waitingcust=0,ttq=0,tts=0;
for(i=0;i
{ if(i==0)
{ cumarrival[i]=arrivalprob[i];
arrivalrda[i]=(int)(cumarrival[i]*100d);
}
else
{ cumarrival[i]=cumarrival[i-1]+arrivalprob[i];
arrivalrda[i]=(int)(cumarrival[i]*100d);
}
}
for(i=0;i
{ if(i==0)
{ cumable[i]=ableprob[i];
ablerda[i]=(int)(cumable[i]*100d);
}
else
{ cumable[i]=cumable[i-1]+ableprob[i];
ablerda[i]=(int)(cumable[i]*100d);
}
}
for(i=0;i
{ if(i==0)
{ cumbaker[i]=bakerprob[i];
bakerrda[i]=(int)(cumbaker[i]*100d);
}
else
{ cumbaker[i]=cumbaker[i-1]+bakerprob[i];
bakerrda[i]=(int)(cumbaker[i]*100d);
}
}
{ arrival[i]=arrivaltime[j];
break;
}
interarrival[i]=interarrival[i-1]+arrival[i];
}
for(i=0;i<(60/ableservice[0]);i++)
{ for(j=0;j<ablerda.length;j++)
{ if(randomservice[i]==0)
{ serviceable[i]=ableservice[ableservice.length-1];
break;
}
if(randomservice[i]<=ablerda[j])
{ serviceable[i]=ableservice[j];
break;
}
}
for(j=0;j<bakerrda.length;j++)
{ if(randomservice[i]==0)
{ servicebaker[i]=bakerservice[bakerservice.length-1];
break;
}
if(randomservice[i]<=bakerrda[j])
{ servicebaker[i]=bakerservice[j];
break;
}
}
}
for(i=0;i<(60/ableservice[0]);i++)
{ if(tempable<=interarrival[i])
{ ablestart[i]=interarrival[i];
ableend[i]=ablestart[i]+serviceable[i];
ableprocess[i]=serviceable[i];
tempable=ableend[i];
ttime[i]=ableprocess[i]+qtime[i];
}
else if(tempbaker<=interarrival[i])
{ bakerstart[i]=interarrival[i];
bakerend[i]=bakerstart[i]+servicebaker[i];
bakerprocess[i]=servicebaker[i];
tempbaker=bakerend[i];
ttime[i]=bakerprocess[i]+qtime[i];
}
else if((interarrival[i]
{ ablestart[i]=tempable;
ableend[i]=ablestart[i]+serviceable[i];
ableprocess[i]=serviceable[i];
qtime[i]=tempable-interarrival[i];
tempable=ableend[i];
ttime[i]=ableprocess[i]+qtime[i];
waitingcust++;
}
else
{ bakerstart[i]=tempbaker;
bakerend[i]=bakerstart[i]+servicebaker[i];
bakerprocess[i]=servicebaker[i];
qtime[i]=tempbaker-interarrival[i];
tempbaker=bakerend[i];
ttime[i]=bakerprocess[i]+qtime[i];
waitingcust++;
}
}
for(i=0;(interarrival[i]<=60)&&(i<(60/ableservice[0]));i++)
{ ableavgser=ableavgser+ableprocess[i];
bakeravgser=bakeravgser+bakerprocess[i];
ttq=ttq+qtime[i];
tts=tts+ttime[i];
}
float totalcust=i;
System.out.println("No.\tRDA.\tARR.\tIARR.\tRDS.\tASTAT.\tAPRO.\tAEND.\tBSTAT.\tBPRO.\tBEND.
\tTSQ.\tTIS");
System.out.println("-------------------------------------------------------------------------------------------------------");
for(i=0;(interarrival[i]<=60)&&(i<(60/ableservice[0]));i++)
{ System.out.println((i+1)+"\t"+randomarrival[i]+"\t"+arrival[i]+"\t"+interarrival[i]+"\t"+randomservice[i]+
"\t"
+ablestart[i]+"\t"+ableprocess[i]+"\t"+ableend[i]+"\t"+bakerstart[i]+"\t"+bakerprocess[i]+"\t"+bakerend[i]+"\t"
+qtime[i]+"\t"+ttime[i]);
System.out.println("-------------------------------------------------------------------------------------------------------");
}
System.out.println("PERFORMANCE OF THE SYSTEM IS AS FOLLOWS:");
System.out.println("AVERAGE SERVICE TIME OF ABLE:"+(ableavgser/totalcust));
System.out.println("AVERAGE SERVICE TIME OF BAKER:"+(bakeravgser/totalcust));
if(waitingcust>0)
System.out.println("AVERAGE WAITING TIME OF THE CUSTOMER IS:"+(ttq/waitingcust));
System.out.println("PROBABILITY OF WAITING IS:"+(waitingcust/totalcust));
System.out.println("AVERAGE TIME SPENT BY CUSTOMER IN THE SYSTEM IS:"+(tts/totalcust));
}
}