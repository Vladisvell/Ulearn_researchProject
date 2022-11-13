import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.SearchResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class VkTestingField {
    public void run() throws IOException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        Path path = Path.of("C:\\Users\\Vladislav\\Desktop\\AccessCredentials.txt");
        var lines = Files.readAllLines(path);
        var authKey = lines.get(0).split("=")[1];
        var appID = Integer.parseInt(lines.get(1).split("=")[1]);

        UserAuthResponse authResponse;

        UserActor actor = new UserActor(appID, authKey);

        SearchResponse example = null;
        try {
            example = vk
                    .users()
                    .search(actor)
                    .fields(Fields.SEX, Fields.BDATE, Fields.HOME_TOWN, Fields.CITY)
                    .lang(Lang.EN)
                    .q("Годзилла")
                    .count(1)
                    .execute();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
        System.out.println(example.getItems());
        System.out.println("Ле экзампуле");
    }
}
