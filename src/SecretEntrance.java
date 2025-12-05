import java.util.List;

public class SecretEntrance {
    public static int find_password() {
        List<String> lines = InputReader.readLines("Datasets/Day1_SecretEntrance.txt");

        int dial = 50;
        int countZeros = 0;

        for (String line : lines) {
            int value = Integer.parseInt(line.substring(1));
            int direction = (line.charAt(0) == 'L') ? -1 : 1;

            dial = (dial + direction * value + 100) % 100;

            if (dial == 0) {
                countZeros++;
            }
        }

        return countZeros;
    }
}
