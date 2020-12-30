import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
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

public class StrawPollAPI {

  /**
   *
   * @param question is your title/question.
   * @param answers are the answers for your question
   * @return the URL from your StrawPoll.
   * @throws IOException
   * @throws ParseException
   */
  public String createStrawPoll(final String question, final String... answers) throws IOException, ParseException {
    final String requestURL = "https://strawpoll.com/api/poll";
    final Poll poll = new Poll(new StrawPollEntry(question, (answers)));
    final URL url = new URL(requestURL);

    final HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setInstanceFollowRedirects(false);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json; utf-8");
    conn.setRequestProperty("Accept", "application/json");
    conn.setUseCaches(false);

    final String json = new Gson().toJson(poll);
    conn.getOutputStream().write(json.getBytes());

    final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    final JSONParser parser = new JSONParser();
    final JSONObject jsonObject = (JSONObject) parser.parse(read(reader));
    return "https://strawpoll.com/" + jsonObject.get("content_id");
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
  private class Poll {

    @SerializedName("poll")
    private final StrawPollEntry poll;

  }

  @RequiredArgsConstructor
  private class StrawPollEntry {

    @SerializedName("title")
    private final String title;
    @SerializedName("answers")
    private final String[] answers;
    @SerializedName("ma")
    private final boolean ma = true;

  }
}
