package inga;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var mapper = new ObjectMapper();

        try (Parser parser = new Parser()) {
            while (scanner.hasNextLine()) {
                String path = scanner.nextLine();
                var element = parser.parse(Path.of(path));
                String json = mapper.writeValueAsString(element);
                System.out.println(json);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
