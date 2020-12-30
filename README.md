# Helping-APIs



# Watch2Gether Example

```
Watch2GetherAPI watch2GetherAPI = new Watch2GetherAPI();
String roomURL = watch2GetherAPI.createRoom(URL);
```


# HasteBin Example

```
HasteBinAPI hasteBinAPI = new HasteBinAPI();
String hasteBinURL = hasteBinAPI.createHastebin(text);
String textFromHasteBin = hasteBinAPI.getHasteBin(hasteBinCODE);
```


# StrawPoll Example

```
StrawPollAPI strawPollAPI = new StrawPollAPI();
String URL = strawPollAPI.createStrawPoll("Who am I?", new String[]{"Name 2", "Name 2", "Name 3"});
```
