App Design Project
===

# Neo Alexandria

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
A free and open virtual library, where you will be able to find news, music, videos and of course books, for free. Users can share their opinion of the content with commentaries and give them punctuation that will help the community to find better content. You can also build your personal library, saving all those resources in your account.

### App Evaluation

- **Category:** Educational / Social networking
- **Story:** Create a platform that offers you the resources that you need and show what people think of those resources (rating resource quality and commenting if there was another resource better for them), this can save people a lot of time. As well as saving these resources in the app. And know new resources for different topics.
- **Market:** All people on the internet looking for free music, books and news as well as opinions of these resources.
- **Habit:** People are using this constantly throughout their life searching topics of interest for them. Especially if they are students.
- **Mobile:** Mobile version is better because it would let user easily access to resources that they saved even without internet connection, as well as be capable to upload photos for you profile or posts.
- **Scope:** V1 would let people search and view by title the resources they want and this will be build over the core of the app (require planing and future goals for next versions). V2 would incorporate the save resource feature.  V3 would add comment and rating for every resource as well as a details view. V4 would let users add posts talking about some resources or adding their own ones, as well as asking the community for resources, as well as all the required stories. V5 will implement all the bonus stories.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

---
* [ ] User can login to his or her account
* [ ] The current signed in user is persisted across app restarts
* [ ] Successful login animation <span style="color:red">*</span>
* [ ] User can sign up to create a new account using Parse authentication
* [ ] Users can login with at least one third party service. (like google, facebook, github, etc)<span style="color:red">*</span>
---
* [ ] User can compose posts
* [ ] User can compose resources
* [ ] User can see other users posts
* [ ] User can see other users resources
---
* [ ] Every image uploaded pass through a secure content filter to avoid NSFW content using the maching learning power (from an API) <span style="color:red">*</span>
---
* [ ] Everything posted includes: photo and name of the user who posted it, timestamp and content (it can include pictures).
* [ ] Every post has like icon(actionable) and like count.
* [ ] Every post has comment icon(actionable) and comment count.
* [ ] Every post has save icon(actionable) and saved count.
* [ ] Posts have the same 3 past user stories in details view.
---
* [ ] Every resource has a rating(actionable)
* [ ] Every resource has comment icon(actionable) and comment count.
* [ ] Every resource has save icon(actionable) and saved count.
* [ ] Resources have the same 3 past user stories in details view.
---
* [ ] User can see the last 20 items (posts, resources, commentaries) where needed.
* [ ] User can pull down to refresh the last 20 items.
---
* [ ] Every resource or post is clickable, and when it is clicked, you go to the detail view of that item.
* [ ] In detail view you can see the commentaries of the resource
* [ ] Commentaries can only be liked
---
* [ ] Users will be able to search the resources they want by title and will see them displayed.
* [ ] In Search view you can filter the results by category (All (default), books, music, news,  videos and posts by other users). <span style="color:red">*</span>
---
* [ ] The layout of every item as well as the detail view depends on the type of item (music, news, books, etc). <span style="color:red">*</span>
* [ ] Music Item is playable in the style is like a media player in both item and details view. <span style="color:red">*</span>
* [ ] Music is running even with app at background or/and the smartphone on standby status. <span style="color:red">*</span>
* [ ] User can open a pdf reader view in book deatails book in the same app. <span style="color:red">*</span>
---
* [ ] Users can see the resources he or she saved with the same layout and details view used in search activity in saved activity.
* [ ] Saved resources can be view without internet connection if the user want to save them (download button) <span style="color:red">*</span>
---
* [ ] User can log out to his or her account
* [ ] User can change the profile image
---

* [ ] Bottom navigation bar
* [ ] Custom Toolbar
* [ ] Use a gesture
* [ ] The app incorporates at least one external library to add visual polish
* [ ] Spanish and English available for the app depending of the smartphone configuration language <span style="color:red">*</span>



**Optional Nice-to-have Stories**
* [ ] Users can see other users' profile
* [ ] User can see the description of the user, number of posts and user's posts.
* [ ] User can follow other users
* [ ] Users can see the number of followers and followers, as well as have a follow button in every profile.
---
* [ ] User can see notifications about commentaries in his or her posts.
* [ ] Infinite pagination everywhere it can be used.
* [ ] Follow de UI used in wireframes.
* [ ] Use fragments instead of activities in sections.
* [ ] Easter egg EUREKA
* [ ] Custom app icon
* [ ] Blur video as background in login and signup
* [ ] While sign up assigned predefined random names and profile images
* [ ] Sort and filter resources by rating
* [ ] Users can filter resources just like in the search view in saved items.
* [ ] User can update the app or search for new updates <span style="color:red">*</span>
* [ ] User can set night/dark mode <span style="color:red">*</span>
* [ ] Users can choose their main color (color used in their posts and profile). <span style="color:red">*</span>
* [ ] User receive a notification every time a user's follow account post something <span style="color:red">*</span>
* [ ] Progress bars where needed

### 2. Screen Archetypes

* No specific screen
  * [ ] Every image uploaded pass through a secure content filter to avoid NSFW content using the maching learning power (from an API) <span style="color:red">*</span>
  * [ ] Bottom navigation bar
  * [ ] Custom Toolbar
  * [ ] Use a gesture
  * [ ] The app incorporates at least one external library to add visual polish
  * [ ] Spanish and English available for the app depending of the smartphone configuration language <span style="color:red">*</span>

* Login
  * [ ] User can login to his or her account
  * [ ] The current signed in user is persisted across app restarts
  * [ ] Successful login animation <span style="color:red">*</span>
  * [ ] Users can login with at least one third party service. (like google, facebook, github, etc)<span style="color:red">*</span>

* Signup
  * [ ] User can sign up to create a new account using Parse authentication

* Feed
  * [ ] User can see other users posts
  * [ ] User can see other users resources
  * [ ] Everything posted includes: photo and name of the user who posted it, timestamp and content (it can include pictures).
  * [ ] Every post has like icon(actionable) and like count.
  * [ ] Every post has comment icon(actionable) and comment count.
  * [ ] Every post has save icon(actionable) and saved count.
  * [ ] Posts have the same 3 past user stories in details view.
  * [ ] Every resource has a rating(actionable)
  * [ ] Every resource has comment icon(actionable) and comment count.
  * [ ] Every resource has save icon(actionable) and saved count.
  * [ ] Resources have the same 3 past user stories in details view.
  * [ ] Every resource or post is clickable, and when it is clicked, you go to the detail view of that item.
  * [ ] In detail view you can see the commentaries of the resource
  * [ ] Commentaries can only be liked
  * [ ] User can see the last 20 items (posts, resources, commentaries) where needed.
  * [ ] User can pull down to refresh the last 20 items.
  * [ ] The layout of every item as well as the detail view depends on the type of item (music, news, books, etc). <span style="color:red">*</span>
  * [ ] Music Item is playable in the style is like a media player in both item and details view. <span style="color:red">*</span>
  * [ ] Music is running even with app at background or/and the smartphone on standby status. <span style="color:red">*</span>
  * [ ] User can open a pdf reader view in book deatails book in the same app. <span style="color:red">*</span>

* Compose Activity
  * [ ] User can compose posts
  * [ ] User can compose resources

* Search
  * [ ] Users will be able to search the resources they want by title and will see them displayed.
  * [ ] In Search view you can filter the results by category (All (default), books, music, news,  videos and posts by other users). <span style="color:red">*</span>
  * [ ] Every resource has a rating(actionable)
  * [ ] Every resource has comment icon(actionable) and comment count.
  * [ ] Every resource has save icon(actionable) and saved count.
  * [ ] Every resource or post is clickable, and when it is clicked, you go to the detail view of that item.
  * [ ] In detail view you can see the commentaries of the resource
  * [ ] Commentaries can only be liked
  * [ ] User can see the last 20 items (posts, resources, commentaries) where needed.
  * [ ] User can pull down to refresh the last 20 items.
  * [ ] The layout of every item as well as the detail view depends on the type of item (music, news, books, etc). <span style="color:red">*</span>
  * [ ] Music Item is playable in the style is like a media player in both item and details view. <span style="color:red">*</span>
  * [ ] Music is running even with app at background or/and the smartphone on standby status. <span style="color:red">*</span>
  * [ ] User can open a pdf reader view in book deatails book in the same app. <span style="color:red">*</span>

* Saved
  * [ ] Everything posted includes: photo and name of the user who posted it, timestamp and content (it can include pictures).
  * [ ] Every post has like icon(actionable) and like count.
  * [ ] Every post has comment icon(actionable) and comment count.
  * [ ] Every post has save icon(actionable) and saved count.
  * [ ] Posts have the same 3 past user stories in details view.
  * [ ] Every resource has a rating(actionable)
  * [ ] Every resource has comment icon(actionable) and comment count.
  * [ ] Every resource has save icon(actionable) and saved count.
  * [ ] Resources have the same 3 past user stories in details view.
  * [ ] User can see the last 20 items (posts, resources, commentaries) where needed.
  * [ ] User can pull down to refresh the last 20 items.
  * [ ] Every resource or post is clickable, and when it is clicked, you go to the detail view of that item.
  * [ ] In detail view you can see the commentaries of the resource
  * [ ] Commentaries can only be liked
  * [ ] The layout of every item as well as the detail view depends on the type of item (music, news, books, etc). <span style="color:red">*</span>
  * [ ] Music Item is playable in the style is like a media player in both item and details view. <span style="color:red">*</span>
  * [ ] Music is running even with app at background or/and the smartphone on standby status. <span style="color:red">*</span>
  * [ ] User can open a pdf reader view in book deatails book in the same app. <span style="color:red">*</span>

* Profile
  * [ ] User can log out to his or her account
  * [ ] User can change the profile image


* Settings
  * [ ] Users can see the resources he or she saved with the same layout and details view used in search activity in saved activity.
  * [ ] Saved resources can be view without internet connection if the user want to save them (download button) <span style="color:red">*</span>

**Optional Nice-to-have Stories**
* No specific view
  * [ ] Infinite pagination everywhere it can be used.
  * [ ] Follow de UI used in wireframes.
  * [ ] Use fragments instead of activities in sections.
  * [ ] Easter egg EUREKA
  * [ ] Custom app icon
  * [ ] User can update the app or search for new updates <span style="color:red">*</span>
  * [ ] Progress bars where needed
* Login
  * [ ] Blur video as background in login and signup
* Signup
  * [ ] Blur video as background in login and signup
  * [ ] While sign up assigned predefined random names and profile images
* Search
  * [ ] Users can see other users' profile
  * [ ] Sort and filter resources by rating
* Saved
  * [ ] Users can see other users' profile
  * [ ] Users can filter resources just like in the search view in saved items.
* Profile
  * [ ] User can see the description of the user, number of posts and user's posts.
  * [ ] User can follow other users
  * [ ] Users can see the number of followers and followers, as well as have a follow button in every profile.
* Settings
  * [ ] User can set night/dark mode <span style="color:red">*</span>
  * [ ] Users can choose their main color (color used in their posts and profile). <span style="color:red">*</span>
* Notifications
  * [ ] User can see notifications about commentaries in his or her posts.
  * [ ] User receive a notification every time a user's follow account post something (depends of follow bonus activity) <span style="color:red">*</span>
* Feed
  * [ ] Users can see other users' profile


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Feed
* Notifications
* Search
* Saved
* Profile

**Flow Navigation** (Screen to Screen)

* Login
   * Signup
   * Feed
* Signup
   * Feed
* Feed
   * Compose
   * Notifications
   * Profile
   * Search
   * Saved
* Compose
   * Feed
* Notifications
   * Profile
   * Search
   * Saved
   * Feed
* Search
   * Profile
   * Notifications
   * Saved
   * Feed
   * Details View
* Saved
   * Profile
   * Search
   * Notifications
   * Feed
* Profile
   * Notifications
   * Search
   * Saved
   * Feed
   * Configurations
* Configuration
   * Profile
   * Login
* Details View
   * Feed
   * Notifications
   * Search
   * Saved
   * Profile

## Wireframes
I did it directly on digital using Figma.

### [BONUS] Digital Wireframes & Mockups
<img src="wireframe.jpg" width=600>

### [BONUS] Interactive Prototype
<img src="InteractiveConnections.png" width=600>
<img src='Prototype.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />


## Schema
[This section will be completed in Unit 9]

### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]

* Login (Read/GET)
```java
 ParseUser.logInInBackground("<userName>", "<password>", (user, e) -> {
     if (user != null) {
         // The user is logged in.
     } else {
         // Login failed. Look at the ParseException to see what happened.
         Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
     }
 });
```
* Signup (Create/POST)
```java
ParseUser user = new ParseUser();
user.setUsername("my name");
user.setPassword("my pass");
user.setEmail("email@example.com");
// Other fields can be set just like any other ParseObject,
// using the "put" method, like this: user.put("attribute", "its value");
// If this field does not exists, it will be automatically created
user.signUpInBackground(e -> {
    if (e == null) {
          // Let them use the app now.
    } else {
         // Sign up didn't succeed. Look at the ParseException
         // to figure out what went wrong
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
});
```

To find more code: https://parse-dashboard.back4app.com/


- [OPTIONAL: List endpoints if using existing API such as Yelp]

* ## News API

Installation
Step 1. Add the JitPack repository to your root build.gradle file.
```java
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
Step 2 : Download via Gradle:
```java
implementation 'com.github.KwabenBerko:News-API-Java:1.0.0'
Usage
NewsApiClient newsApiClient = new NewsApiClient("YOUR_API_KEY");

// /v2/everything
newsApiClient.getEverything(
  new EverythingRequest.Builder()
          .q("trump")
          .build(),
  new NewsApiClient.ArticlesResponseCallback() {
      @Override
      public void onSuccess(ArticleResponse response) {
          System.out.println(response.getArticles().get(0).getTitle());
      }

      @Override
      public void onFailure(Throwable throwable) {
          System.out.println(throwable.getMessage());
      }
  }
);

// /v2/top-headlines
newsApiClient.getTopHeadlines(
  new TopHeadlinesRequest.Builder()
    .q("bitcoin")
    .language("en")
    .build(),
  new NewsApiClient.ArticlesResponseCallback() {
    @Override
    public void onSuccess(ArticleResponse response) {
      System.out.println(response.getArticles().get(0).getTitle());
    }

    @Override
    public void onFailure(Throwable throwable) {
      System.out.println(throwable.getMessage());
    }
  }
);

// /v2/top-headlines/sources
newsApiClient.getSources(
  new SourcesRequest.Builder()
    .language("en")
    .country("us")
    .build(),
  new NewsApiClient.SourcesCallback() {
    @Override
    public void onSuccess(SourcesResponse response) {
        System.out.println(response.getSources().get(0).getName());
    }

    @Override
    public void onFailure(Throwable throwable) {
      System.out.println(throwable.getMessage());
    }
  }
);
```
More information on: https://newsapi.org/

* ## Deezer

Example of use:
```
https://api.deezer.com/search?q=eminem
```
More information on: https://developers.deezer.com/api/explorer

* ## BookMeth

```java
AsyncHttpClient client = new DefaultAsyncHttpClient();
//Example
client.prepare("GET", "https://bookmeth1.p.rapidapi.com/?q=Harry%20Potter&start=0&sort=datedesc")
	.setHeader("x-rapidapi-key", "BOOKMETH_API_KEY")
	.setHeader("x-rapidapi-host", "bookmeth1.p.rapidapi.com")
	.execute()
	.toCompletableFuture()
	.thenAccept(System.out::println)
	.join();

client.close();
```
More information on: https://rapidapi.com/arshad2477/api/bookmeth1

* ## NSFW Image Classification
```java
AsyncHttpClient client = new DefaultAsyncHttpClient();
client.prepare("POST", "https://nsfw-image-classification1.p.rapidapi.com/img/nsfw")
	.setHeader("content-type", "application/json")
	.setHeader("x-rapidapi-key", "ANTINSFW_API_KEY")
	.setHeader("x-rapidapi-host", "nsfw-image-classification1.p.rapidapi.com")
	.setBody("{
    \"url\": \"https://www.inferdo.com/img/nsfw-1-raw.jpg\"
}")
	.execute()
	.toCompletableFuture()
	.thenAccept(System.out::println)
	.join();

client.close();
```
More information on: https://rapidapi.com/inferdo/api/nsfw-image-classification1/
* ## MuPDF

The MuPDF library needs Android version 4.1 or newer. Make sure that the minSdkVersion in your app's build.gradle is at least 16.
```gradle
android {
	defaultConfig {
		minSdkVersion 16
		...
	}
	...
}
```
The MuPDF library can be retrieved as a pre-built artifact from our Maven repository. Add the maven repository to your project. In your project's top build.gradle, add the bolded line to to the repositories section:
```gradle
allprojects {
	repositories {
		jcenter()
		maven { url 'http://maven.ghostscript.com' }
		...
	}
}
```
Then add the MuPDF viewer library to your app's dependencies. In your app's build.gradle, add the bolded line to the dependencies section:
```gradle
allprojects {
	repositories {
		jcenter()
		maven { url 'http://maven.ghostscript.com' }
		...
	}
}
```
Once this has been done, you have access to the MuPDF viewer activity. You can now open a document viewing activity by launching an intent, passing the URI of the document you wish to view.

```java
import com.artifex.mupdf.viewer.DocumentActivity;

public void startMuPDFActivity(Uri documentUri) {
	Intent intent = new Intent(this, DocumentActivity.class);
	intent.setAction(Intent.ACTION_VIEW);
	intent.setData(documentUri);
	startActivity(intent);
}
```

The activity supports viewing both file and content scheme URIs.

For example, to open the PDF file in ~/Download/example.pdf:

```java
public void startMuPDFActivityWithExampleFile() {
	File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
	File file = new File(dir, "example.pdf");
	Uri uri = Uri.fromFile(file);
	startMuPDFActivity(uri);
}
```

More information on: https://mupdf.com/docs/android-sdk.html

* ## Youtube
```java
/**
 * Sample Java code for youtube.search.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 */

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;

public class ApiExample {
    private static final String CLIENT_SECRETS= "client_secret.json";
    private static final Collection<String> SCOPES =
        Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl");

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = ApiExample.class.getResourceAsStream(CLIENT_SECRETS);
        GoogleClientSecrets clientSecrets =
          GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
            new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
            .build();
        Credential credential =
            new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static void main(String[] args)
        throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.Search.List request = youtubeService.search()
            .list("snippet");
        SearchListResponse response = request.setMaxResults(25L)
            .setQ("surfing")
            .execute();
        System.out.println(response);
    }
}
```
More information on: https://developers.google.com/youtube/v3/docs/search
commit

Pictures out of project
