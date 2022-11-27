import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //VkTestingField test = new VkTestingField();
        //test.run();
        VkApiRequests client = new VkApiRequests("C:\\Users\\Vladislav\\Desktop\\AccessCredentials.txt");
        List<Student> fulldata = null;
        try {
            fulldata = client.requestGenders(CSVUtils.read());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }
}