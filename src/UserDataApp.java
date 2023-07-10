import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            String tryAgain = "\nХотите попробовать ввести данные еще раз? (y/n)";
            System.out.println("Введите данные в следующем формате: Фамилия Имя Отчество ДатаРождения(01.01.2000) Номер телефона(71234567890) Пол(М/Ж)");
            String input = scanner.nextLine();

            String[] data = input.split(" ");
            if (data.length != 6) {
                System.out.println("Ошибка: неверное количество данных! Вы ввели " + data.length + " данных." + tryAgain);
                continue;
            }

            String lastName = data[0];
            String firstName = data[1];
            String patronymic = data[2];
            String dateOfBirthString = data[3];
            String phoneNumberString = data[4];
            String genderString = data[5];

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate dateOfBirth;
            try {
                dateOfBirth = LocalDate.parse(dateOfBirthString, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка: неверный формат даты рождения!" + tryAgain);
                continue;
            }

            final String PHONE_TEMPLATE = "7\\d{10}";
            try {
                        if (!phoneNumberString.matches(PHONE_TEMPLATE)) {
                            throw new IllegalArgumentException();
                        }}
            catch (IllegalArgumentException e) {
                System.out.println("Ошибка: Неверный формат телефона в строке " + phoneNumberString + tryAgain);
                continue;
            }

            if (genderString.length() != 1 || (!genderString.equalsIgnoreCase("ж") && !genderString.equalsIgnoreCase("м") && !genderString.equalsIgnoreCase("m") && !genderString.equalsIgnoreCase("f"))) {
                System.out.println("Ошибка: неверное значение пола! Значение должно быть М или Ж" + tryAgain);
                continue;
            }

            char gender = genderString.toLowerCase().charAt(0);

            String filename = lastName + ".txt";
            try {
                String userData = lastName + " " + firstName + " " + patronymic + " " + dateOfBirthString + " " + phoneNumberString +" " + gender;
                Path path = Paths.get(filename);
                if (Files.exists(path)) {
                    Files.writeString(path, userData + System.lineSeparator(), StandardOpenOption.APPEND);
                } else {
                    Files.writeString(path, userData + System.lineSeparator());
                }
                System.out.println("Данные успешно записаны в файл " + filename);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении/записи файла:");
                e.printStackTrace();
            }

            System.out.println("Хотите ввести еще данные? (y/n)");
        } while (scanner.nextLine().equalsIgnoreCase("y"));

        scanner.close();
    }
}
