import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

@RequiredArgsConstructor
public class Watch2GetherAPI {

  /**
   *
   * @param preLoadVideo is the YT-Video which should be loaded
   * @return the URL from the Room.
   * @throws IOException
   * @throws ParseException
   */
  public String createRoom(final String preLoadVideo) throws IOException, ParseException {
    String requestURL = "https://w2g.tv/rooms/create.json";

    W2GEntry entry = new W2GEntry(preLoadVideo);

    URL url = new URL(requestURL);
    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setInstanceFollowRedirects(false);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json; utf-8");
    conn.setRequestProperty("Accept", "application/json");
    conn.setUseCaches(false);
    String json = new Gson().toJson(entry);
    conn.getOutputStream().write(json.getBytes());

    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = (JSONObject) parser.parse(read(reader));
    return "https://w2g.tv/rooms/" + jsonObject.get("streamkey");
  }

  private String read(final Reader reader) throws IOException {
    final StringBuilder stringBuilder = new StringBuilder();

    int i;
    while ((i = reader.read()) != -1) {
      stringBuilder.append((char) i);
    }
    return stringBuilder.toString();
  }

  @RequiredArgsConstructor
  public class W2GEntry {

    private final String share;
    private final String bg_color = "#00ff00";
    private final String bg_opacity = "50";

  }

}
