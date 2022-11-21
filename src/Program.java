/*Задана натуральная степень k. Сформировать случайным образом список коэффициентов
        (значения от 0 до 100) многочлена многочлен степени k.
        *Пример: k=2 => 2*x² + 4*x + 5 = 0 или x² + 5 = 0 или 10*x² = 0*/

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class Program {
//    сначала описываем ф-ции

//    получаем степень k с пользовательского ввода
    static int inputNum(String strInput) {
        int numInput = 0;
        Boolean chNum = false;

        while (!chNum) {
            Scanner iScanner = new Scanner(System.in);
            System.out.printf(strInput);
            chNum = iScanner.hasNextInt();
            System.out.println(chNum);
            if (chNum) {
                numInput = iScanner.nextInt();
                System.out.println("Введено: " + numInput);
                iScanner.close();
            }
            else System.out.println("Некорректный ввод");
            }
        return numInput;
        }

//    генерация случайных целочисленных значений в указанном диапазоне:
    static int getRandomNumberInts(int min, int max){
        Random random = new Random();
        return random.ints(min,(max+1)).findFirst().getAsInt();
    }

//Сформировать случайным образом список коэффициентов
    static int[] listKf(int k){
    int[] kfArr = new int[k+1];
        for (int i = 0; i < k; i++) {
            kfArr[i] = getRandomNumberInts(0,100);
        }
//        крайний эл-т - при главном члене не может быть равен 0, поэтому отдельно от 1
        kfArr[k] = getRandomNumberInts(1,100);
        return kfArr;
    }
//ф-ция вывода на печать массива
    static void printArr(int[] arr){
        for (int item : arr) {
            System.out.printf("%d ", item);
        }
        System.out.println();
    }

//    собираем многочлен
    static String createPol(int[] kf) {
        String strPol = null;
//        создаем и собираем строку с многочленом - сначала в массив строк
        String[] pol = new String[kf.length];
        //System.out.println(Arrays.toString(pol));//вывожу, см. что там
//мы идем сверху вниз, а массив формируется в строку с 0 до конца, поэтому нужно перевернуть
        //или формировать сразу от 0 вверх
        for (int i = kf.length-1; i >1 ; i--) {
            if (kf[i] != 0)
                pol[kf.length-1-i]= kf[i]!=1 ? String.format("%d*x^%d",kf[i],i) : String.format("x^%d",i);
        }
        if (kf[1] != 0)
            pol[kf.length-2]= kf[1]!=1 ? String.format("%d*x",kf[1]) : "x";
        if (kf[0] != 0) pol[kf.length-1] = String.format("%d",kf[0]);

        System.out.println(Arrays.toString(pol));//вывожу, см. что там
        //собираем члены в одну строку
        strPol = String.join(" + ", pol);
//        если коэф==0, то значения не изменятся, а останутся null - убираем из строки
        strPol = strPol.replace("null + ", "").replace(" + null", "");
        strPol = strPol + " = 0";
        System.out.println(strPol);

        return strPol;
        }

//        запись в файл
    static void fileWrite(String strPol, String fileName){
        try (FileWriter fw = new FileWriter(fileName, false)) {
            // записываем
            fw.write(strPol);
            fw.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //запускаем программу и вызываем описанные выше ф-ции
    public static void main(String[] args) {
        int k = inputNum("Введите степень k: ");
        System.out.println("k = "+ k);
//        printArr(listKf(k));//смотрим коэфф-ты через самост.написанную ф-цию
        //получили и вывели случайный список коэфф.
        int[] myArr= listKf(k);
        System.out.println("Список коэфф-в: "+ Arrays.toString(myArr));
        String strPol = createPol(myArr);
        //записали в файл выше полученную строку с многочленом
        fileWrite(strPol, "create_pol.txt");

//        формируем 2й файл принудительно для 2й задачи
        int k2 = 3;
        int[] arrK = listKf(k2);
        String strK = createPol(arrK);
        fileWrite(strK, "create_pol2.txt");

    }
}