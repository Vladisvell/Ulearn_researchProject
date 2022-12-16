package Vkontakte;

import DefaultPackage.Person;
import DefaultPackage.Student;
import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.SearchResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.lang.Thread;

public class VkApiRequests {
    private final VkApiClient vk;
    private final UserActor actor;

    public VkApiRequests(String credentialsPath){
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        Path path = Path.of(credentialsPath);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var authKey = lines.get(0).split("=")[1];
        var appID = Integer.parseInt(lines.get(1).split("=")[1]);
        this.actor = new UserActor(appID, authKey);
        this.vk = vk;
    }

    public List<Student> requestGenders(List<Student> students) throws InterruptedException {
        for(Student student : students){
            try {
                SearchResponse request = vk
                        .users()
                        .search(actor)
                        .fields(Fields.SEX)
                        .lang(Lang.RU)
                        .q(student.getName())
                        .count(1)
                        .execute();
                if(request.getItems().size() != 0){
                    var sex = request.getItems().get(0).getSex().ordinal();
                    student.setGenderByCode(sex);
                }
            } catch (ApiException e) {
                throw new RuntimeException(e);
            } catch (ClientException e) {
                throw new RuntimeException(e);
            }
            Thread.sleep(260);
        }
        System.out.println();
        return students;
    }
}
