import java.util.List;

public class SecretEntrance {
    public static int find_password(boolean method_0x, boolean test_file) {
        String path = test_file ?
                "Datasets/Day1_SecretEntrance_test.txt" :
                "Datasets/Day1_SecretEntrance.txt";

        List<String> lines = InputReader.readLines(path);

        int dial = 50;
        int countZerosP1 = 0;
        int countZerosP2 = 0;

        for (String line : lines) {
            char dirChar = line.charAt(0);
            int direction = (dirChar == 'L') ? -1 : 1;
            int value = Integer.parseInt(line.substring(1));

            int start = dial;
            int delta = direction * value;
            int end = start + delta;

            int raw = Math.floorMod(-start * direction, 100);
            int first_t = (raw == 0) ? 100 : raw;

            int crosses = 0;
            if (first_t <= value) {
                crosses = 1 + (value - first_t) / 100;
            }
            countZerosP1 += crosses;

            dial = Math.floorMod(end, 100);
            if (dial == 0) {
                countZerosP2++;
            }
        }

        return method_0x ? countZerosP1 : countZerosP2;
    }
}
