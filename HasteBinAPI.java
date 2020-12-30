import lombok.RequiredArgsConstructor;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class HasteBinAPI {

  /**
   *
   * @param text is the Text/Code from the HasteBin
   * @return the URL
   * @throws IOException
   */
  public String createHastebin(final String text) throws IOException {
    byte[] postData = text.getBytes(StandardCharsets.UTF_8);
    int postDataLength = postData.length;

    String requestURL = "https://hastebin.com/documents";
    URL url = new URL(requestURL);
    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setInstanceFollowRedirects(false);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("User-Agent", "Hastebin Java Api");
    conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
    conn.setUseCaches(false);

    String response = null;
    DataOutputStream wr;
    try {
      wr = new DataOutputStream(conn.getOutputStream());
      wr.write(postData);
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      response = reader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (response.contains("\"key\"")) {
      response = response.substring(response.indexOf(":") + 2, response.length() - 2);

      String postURL = "https://hastebin.com/";
      response = postURL + response;
    }

    return response;
  }

  /**
   * @param hasteBinCode is the HasteBinCode, not the full URL!
   * @return the Text/Code from the HasteBin
   * @throws IOException
   */
  public String getHasteBin(final String hasteBinCode) throws IOException {
    String requestURL = "https://hastebin.com/raw/" + hasteBinCode;
    URL url = new URL(requestURL);
    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setInstanceFollowRedirects(false);
    conn.setRequestMethod("GET");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

    StringBuilder everything = new StringBuilder();
    String line;
    while( (line = bufferedReader.readLine()) != null) {
      everything.append(line + "\n");
    }
    return everything.toString();
  }
}
