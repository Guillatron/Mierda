package circulararrayweigh;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
public class CircularArrayWeigh {
 /**
* This function uses a window algorithm to calculate the weights in a
* circular array of antennas pointing in the x-axis.
*
* dx,dy:element spacings
* radius, of the circle
* Numx, numbers of rows in x
* Numy[], number of elements in y for each x row

* A and B, parameters of window function
*/

static public void writeFile(String filePath, double [] module, double [] phase){
	String EOL = "\r\n";
	String separator ="\t";
	try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath)));
		if(module != null && phase != null && module.length == phase.length){
			bw.write(module.length+EOL);
			for(int i = 0; i < module.length; i++){
				bw.write(module[i] + separator + phase[i] + EOL);
			}
		}
		bw.close();
	} catch(Exception e){
		//System.err.println("Cannot write file " + path + EOL);
	}
} 

public static String Window_circleEON(double dx, double dy,double radius,double A, double B)
{
	int Numx,N;
	int[] Numy = {4,6,8,10,10,10,10,8,6,4};
	Numx = Numy.length;
	System.out.println("Number of rows in x Numx = "+Numy.length);

	//double A=0.0;
	//double B=1.0;
	double Lx,Ly;
	String notice = "Error";
	N=0;
	for (int i=0; i<Numy.length; i++) {
		N=N+Numy[i];
	}
	System.out.println("Number of elements N = "+N);
	double[] a = new double[N]; //vector of weigth of the N elements, double type
	double[] fase = new double[N]; //vector of the phase of the N elements, double type
	if (Numx != 1) { //if the numer of rows in x of the array is different than one
		Lx = dx*(Numx-1);
	} else {
		System.out.println("error, the numer of rows in x of the array is one ");
		return notice;
	}
	int in=0;
	int jn;
	double dyj2;
	double dis;
	if (Numx % 2 ==0) { //if the number of rows in x in the array is even
		for (int i=1; i<=(Numx/2); i++){
			double dxi2=(dx*(i-.5))*(dx*(i-.5));
			for (int j=1; j<=Numy[(Numx/2)+i-1]; j++){
				jn=(Numy[(Numx/2)+i-1]);
				if (Numy[(Numx/2)+i-1]% 2 ==0){ //if the number of elements
					in y in column Num[(Numx/2)+i-1] is even
					dyj2=(dy*(j-(jn/2+.5)))*(dy*(j-(jn/2+.5)));
					dis=Math.pow((dxi2+dyj2),0.5)/radius;
					int aux=(N/2)+in+j-1;
					a[aux]=A+B*Math.cos(dis*(Math.PI/2));
					//apply the formula of the algorithm to calculate the
					weigths
					fase[aux]=.0;
				}
				if (Numy[(Numx/2)+i-1]% 2 !=0) {
					dyj2=(dy*(j-(jn/2+1)))*(dy*(j-(jn/2+1)));
					dis=Math.pow((dxi2+dyj2),0.5)/radius;
					Universidad de Alcalá 4
					int aux=(N/2)+in+j-1;
					a[aux]=A+B*Math.cos(dis*(Math.PI/2));
					//apply the formula of the algorithm to calculate the
					weigths
					fase[aux]=.0;
				}
			}
			in=in+Numy[(Numx/2)+i-1];
		}
	}
	int cont=N-1; //initialize cont to N-1
	for (int j=0; j<N/2; j++) {
		a[j]=a[cont]; //equate the j element in the array a to the cont element in that same array
		cont=cont-1; //decrease cont
	}
	if (Numx % 2 !=0) { //if the number of rows in x in the array is odd
		for (int i=1; i<=(Numx/2)+1; i++) {
			double dxi2=(dx*(i-1))*(dx*(i-1));
			jn=Numy[(Numx/2)+i-1];
			for (int j=1; j<=Numy[(Numx/2)+i-1]; j++) {
				if (Numy[(Numx/2)+i-1]% 2 ==0){ //if the number of elements
					in y in column Num[(Numx/2)+i-1] is even
					dyj2=(dy*(j-(jn/2+.5)))*(dy*(j-(jn/2+.5)));
					dis=Math.pow((dxi2+dyj2),0.5)/radius;
					int aux=(N-Numy[Numx/2])/2+in+j-1;
					a[aux]=A+B*Math.cos(dis*(Math.PI/2));
					//apply the formula of the algorithm to calculate the
					weigths
					fase[aux]=.0;
				}
				if (Numy[(Numx/2)+i-1]% 2 !=0) { //if the number of elements
					in y in column Num[(Numx/2)+i-1] is odd
					dyj2=(dy*(j-(jn/2+1)))*(dy*(j-(jn/2+1)));
					dis=Math.pow((dxi2+dyj2),0.5)/radius;
					int aux=((N-Numy[Numx/2])/2)+in+j-1;
					a[aux]=A+B*Math.cos(dis*(Math.PI/2));
					//apply the formula of the algorithm to calculate the
					weigths
					fase[aux]=.0;
				}
			}
			in=in+Numy[(Numx/2)+i-1];
		}
	}
	cont=N-1; //initialize cont to N-1
	for (int j=0; j<(N-Numy[Numx/2])/2; j++) {
		a[j]=a[cont]; //equate the j element in the array a to the cont element in that
		same array
		cont=cont-1; //decrease cont
	}
	//writeFile("./mydatafiles/window2.txt", a, fase); //write the weigths and phases in a text file
	 writeFile("./window2.txt", a, fase); //write the weigths and phases in a text file
	notice="OK";
	return notice;
}

 /**
 * @param args the command line arguments
 */
 public static void main(String[] args) {
	// TODO code application logic here
	String mensaje;
	mensaje=Window_circleEON(11, 12.17, 70,0., 1.);
 }

}
