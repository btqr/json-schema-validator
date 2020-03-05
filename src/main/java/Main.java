import scanner.Scanner;

import java.nio.file.Files;
import java.nio.file.Path;

class Main {
    public static void main(String[] args) throws Exception {
        String exampleJsonScheme = Files.readString(Path.of("example_json_schema.json"));
        Scanner scanner = new Scanner();

        String tokenizedText = scanner.process(exampleJsonScheme);
        System.out.println(tokenizedText);
    }
}