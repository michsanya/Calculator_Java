import java.util.Scanner;

public class Main {
    /*
    Описание:
Создай консольное приложение “Калькулятор”. Приложение должно читать из консоли введенные пользователем строки, числа, арифметические операции проводимые между ними и выводить в консоль результат их выполнения.
Реализуй класс Main с методом public static String calc(String input). Метод должен принимать строку с арифметическим выражением между двумя числами и возвращать строку с результатом их выполнения. Ты можешь добавлять свои импорты, классы и методы. Добавленные классы не должны иметь модификаторы доступа (public или другие)
Требования:
Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b. Данные передаются в одну строку (смотри пример)! Решения, в которых каждое число и арифмитеческая операция передаются с новой строки считаются неверными.
Калькулятор умеет работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами.
Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по величине и могут быть любыми.
Калькулятор умеет работать только с целыми числами.
Калькулятор умеет работать только с арабскими или римскими цифрами одновременно, при вводе пользователем строки вроде 3 + II калькулятор должен выбросить исключение и прекратить свою работу.
При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими.
При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.
При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций, приложение выбрасывает исключение и завершает свою работу.
Результатом операции деления является целое число, остаток отбрасывается.
Результатом работы калькулятора с арабскими числами могут быть отрицательные числа и ноль. Результатом работы калькулятора с римскими числами могут быть только положительные числа, если результат работы меньше единицы, выбрасывается исключение
Пример работы программы:
Input:
1 + 2
Output:
3
Input:
VI / III
Output:
II
Input:
I - II
Output:
throws Exception //т.к. в римской системе нет отрицательных чисел
Input:
I + 1
Output:
throws Exception //т.к. используются одновременно разные системы счисления
Input:
1
Output:
throws Exception //т.к. строка не является математической операцией
Input:
1 + 2 + 3
Output:
throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)
    */
    public static void main(String[] args) throws Exception {
        //0. Приглашение на ввод данных.
        System.out.print("Введите выражение в формате число знак число: ");
//        1. Чтение строки
        String inputString = new Scanner(System.in).nextLine();
//        2-9
        String out_String = calc(inputString);
//        9. Вывод результата
        System.out.println("Результат: " + out_String);
    }
    public static String calc(String input) throws Exception {
        int operand_1;
        int operand_2;
        int result;
        String outputString;
//        2. Разбитие строки по пробелам
        String[] words = input.split(" ");
//        3. Проверка на количество слов. В случае не три - ошибка
        if (words.length != 3){
            throw new Exception("Количество слов должно быть равно 3, а не " + words.length);
        }
//        4. Проверка 1го и 3го слова. Должны быть или римские или арабские. Если смешано - ошибка
        if (isRoman(words[0]) != isRoman(words[2])){
            throw new Exception("Операнды должны быть либо арабскими, либо римскими. Не смешанные: " + words[0]+", "+words[2]);
        }

//        6. Если римские - преобразование в арабские
        if (isRoman(words[0])){
            operand_1 = romanToArab(words[0]);
            operand_2 = romanToArab(words[2]);
        }
        else{
            operand_1 = Integer.parseInt(words[0]);
            operand_2 = Integer.parseInt(words[2]);
        }

//        7. Выполнение операции. Если не валидный результат - ошибка
        result = switch (words[1]) {
            case ("+") -> operand_1 + operand_2;
            case ("-") -> operand_1 - operand_2;
            case ("*") -> operand_1 * operand_2;
            case ("/") -> operand_1 / operand_2;
            default -> throw new IllegalStateException("Недопустимый оператор " + words[1]);
        };
//        8. Если входные - римские преобразование результата в римские
        if (isRoman(words[0])){
            outputString = arabToRoman(result);
        }
        else{
            outputString = Integer.toString(result) ;
        }

        return outputString;
    }

    private static String arabToRoman(int arabNumero) {
        StringBuilder romanNumeroNoCorrect = new StringBuilder();
        String romanNumero;
        while (arabNumero != 0){
            if (arabNumero >= 100) {
                romanNumeroNoCorrect.append("C");
                arabNumero = arabNumero - 100;
            }
            else if (arabNumero >= 50) {
                romanNumeroNoCorrect.append("L");
                arabNumero = arabNumero - 50;
            }
            else if (arabNumero >= 10) {
                romanNumeroNoCorrect.append("X");
                arabNumero = arabNumero - 10;
            }
            else if (arabNumero >= 5) {
                romanNumeroNoCorrect.append("V");
                arabNumero = arabNumero - 5;
            }
            else if (arabNumero >= 1) {
                romanNumeroNoCorrect.append("I");
                arabNumero = arabNumero - 1;
            }
        }
        romanNumero = romanNumeroNoCorrect.toString();
        romanNumero = romanNumero.replace("LXXXX", "XC");
        romanNumero = romanNumero.replace("XXXX", "XL");
        romanNumero = romanNumero.replace("VIIII", "IX");
        romanNumero = romanNumero.replace("IIII", "IV");

        return romanNumero;
    }

    private static int romanToArab(String romanNumero) {
        int arabNumero = 0;
        romanNumero = romanNumero.replace("XC","LXXXX");
        romanNumero = romanNumero.replace("XL","XXXX");
        romanNumero = romanNumero.replace("IX","VIIII");
        romanNumero = romanNumero.replace("IV","IIII");
        for (char num : romanNumero.toCharArray()){
            if (num == 'C') arabNumero = arabNumero + 100;
            if (num == 'L') arabNumero = arabNumero + 50;
            if (num == 'X') arabNumero = arabNumero + 10;
            if (num == 'V') arabNumero = arabNumero + 5;
            if (num == 'I') arabNumero = arabNumero + 1;
        }
        return arabNumero;
    }

    private static boolean isRoman(String numero) throws Exception {
//      Возвращает false - если число арабское, true - если число римское
        String modifingNumero = numero;
        for (char romNum : "IVXLC".toCharArray()){
            modifingNumero = modifingNumero.replace(String.valueOf(romNum), "");
        }
        if (modifingNumero.length()==0) {
            return true;
        }
        for (char araNum : "0123456789".toCharArray()) {
            modifingNumero = modifingNumero.replace(String.valueOf(araNum), "");
        }
            if (modifingNumero.length() == 0) {
                return false;
            }
            throw new Exception("Формат числа не римский и не арабский: " + numero);
    }
}