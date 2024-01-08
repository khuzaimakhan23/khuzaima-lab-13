
package lab12;
import java.util.Scanner;
public class Lab12 {

    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
                try{
            int a[] = new int[10];
            a[0] = 305;
            a[1] = 237;
            a[2] = 384;
            a[3] = 378;
            a[4] = 297;
            a[5] = 037;
            a[6] = 119;
            a[7] = 100;
            a[8] = 200;
            a[9] = 300;
            System.out.println("Enter the index you want to print: ");
            int index=input.nextInt();
            System.out.println(a[index]);
}
        catch(ArrayIndexOutOfBoundsException e)
        { System.out.println ("Array Index is Out Of Bounds");
}
}
}

        
        

    
    

