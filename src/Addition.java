/*Даны два файла, в каждом из которых находится запись многочлена.
Сформировать файл содержащий сумму многочленов.*/

import java.io.*;
import java.util.Arrays;

public class Addition {

//    ф-ция чтения строки из файла
    static String readFile(String fileName) throws Exception  {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String str = br.readLine();
        br.close();
    return str;
}
//    ф-ция нахождения степени многочлена
    static int findDegree(String pol){
//        находим индексы первого вхождения знака степени - за ним до + или до = будет степень многочлена
        int ind1 = pol.indexOf("^");
        int ind2 = pol.indexOf("+");
        int ind3 = pol.indexOf("=");

//        если найден +, берем подстроку от ^ до +, иначе до =, когда многочлен содержит один член
        String degree = (ind2!=-1)? pol.substring(ind1+1,ind2-1) : pol.substring(ind1+1,ind3-1);
        int d = Integer.parseInt(degree);
        System.out.println(degree);
        return d;
    }

//    получаем список строк членов
    static String[] strToArr(String pol){
        pol = pol.replace(" = 0", "").replace(" ", "");
        String[] arrPol = pol.split("[+]");
        System.out.println(Arrays.toString(arrPol));//вывожу, см. что там
        return arrPol;
    }

    //    собираем коэфф. при членах на свои места в массив
    static int[] returnKf(String[] pol, int maxDegree) {
        int[] arrKf = new int[maxDegree + 1];
//        ищем коэф.при степенях от макс. до 1, не включая
        for (int i = maxDegree; i > 1; i--) {
            for (String el : pol) {
                if (el.indexOf(String.format("^%d", i)) != -1) {
                    if (el.length() == String.format("%d", i).length() + 2) {
                        arrKf[i] = 1;
                    } else {
                        arrKf[i] = Integer.parseInt(el.substring(0,el.indexOf("x")-1));;
                    }
                }
            }
        }
        //идем заполнять 1 и 0 степень отдельно
        for (String el : pol) {
//            проверка условия для свободного члена, т.е. 0 степени
            if (el.indexOf("x")==-1) arrKf[0] = Integer.parseInt(el);
//            проверка условия для x, т.е. 1 степени
            if ((el.length() == 1)&&(el.equals("x"))) arrKf[1] = 1;
            if ((el.indexOf("x")==el.length()-1) && (el.length()>1)) arrKf[1] =
                    Integer.parseInt(el.substring(0,el.indexOf("*")));
        }
        return arrKf;
    }

//    складываем массивы коэф.
    static int[] sumArr(int[] arr1, int[] arr2){
        int[] sumArr = new int[arr1.length];
        for(int i =0;i<sumArr.length;i++){
            sumArr[i]= arr1[i]+arr2[i];
        }
        return sumArr;
    }

//    главный метод, выполнение программы
    public static void main(String[] args) throws Exception   {
    String polA = readFile("create_pol.txt");
    String polB = readFile("create_pol2.txt");
    System.out.println("polA: "+polA);
    System.out.println("polB: "+polB);

    int dA= findDegree(polA);
    int dB= findDegree(polB);
//нашли макс. степень
    int dMax = (dA>dB)? dA : dB;
    System.out.println("dMax степень: "+dMax);

//     убираем =0, пробелы и делим на одночлены
    String[] arrPolA = strToArr(polA);
    String[] arrPolB = strToArr(polB);
//    собираем коэфф.
    int[] kfPolA = returnKf(arrPolA, dMax);
    System.out.println(Arrays.toString(kfPolA));//вывожу, см. что там

        int[] kfPolB = returnKf(arrPolB, dMax);
        System.out.println(Arrays.toString(kfPolB));//вывожу, см. что там
        int[] sumArr = sumArr(kfPolA,kfPolB);
        System.out.println("Сумма коэф.многочленов "+Arrays.toString(sumArr));//вывожу, см. что там
        //формирую строку с многочленом, используя метод, описанный в файле/классе Program
        String strPol = Program.createPol(sumArr);
        System.out.println(strPol);
        Program.fileWrite(strPol, "sum_polynomials.txt");

    }
}
