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
A free and open virtual library, where you will be able to find news, music, videos, pictures and of course books, for free. Here will be gather content of sources such as wikipedia, open library, soundclound, newspapers, and more (**These resources are just ideas that can be changed during the implementation**). You can share your opinion of the content with commentaries and give them punctuation that will help the community to find better content. You can also build your personal library, saving all that resources in your account.

### App Evaluation

- **Category:** Educational / Social networking
- **Story:** Create a platform that offers you the resources that you need and show what people think of those resources (rating resource quality and commenting if there was another resource better for them), this can save people a lot of time. As well as saving these resources in the app. And know new resources for different topics.
- **Market:** All people on the internet looking for free music, books, pictures and news as well as opinions of these resources.
- **Habit:** People are using this constantly throughout their life searching topics of interest for them. Especially if they are students.
- **Mobile:** Mobile version is better because it would let user easily access to resources that you saved, as well as be capable to upload photos for you profile or posts.
- **Scope:** V1 would let people search and view by title the resources they want. V2 would incorporate the save resource feature.  V3 would add comment and rating for every resource as well as a detailed view. V4 would let users add posts talking about some resources or adding their own ones, as well as asking the community for resources, as well as all the required stories. V5 will implement all the bonus stories.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* [ ] User can log in to his or her account
* [ ] The current signed in user is persisted across app restarts
* [ ] Successful login animation
* [ ] User can sign up to create a new account using Parse authentication
* [ ] User can see posts and resources that community have posted
* [ ] Everything posted includes: photo and username of the user who posted it, timestamp and content.
* [ ] Resources has rating, comment icon and number of commentaries, and saved icon with number of times the resources has been saved.
* [ ] Every icon can do their respective functionality in posts and resources.
* [ ] Every post has like, comment and save icon and numbers of each one.
* [ ] User can see the last 20 posted post and/or resources where needed.
* [ ] User can pull to refresh the last 20 posts submitted where needed.
* Details view
   * [ ] Every item has a details view.
   * [ ] Every item is clickable, and when it is clicked, you go to the detail view of that item.
   * [ ] In detail view you can see the commentaries of the resource
   * [ ] Every item can be commented, saved and rated or liked from details view.
   * [ ] User can use scroll gesture to update for newer commentaries, user will see the last 20 commentaries.
(FOR NOW I CANNOT SAY SPECIFICALLY HOW AN ITEM CONTENT WILL BE DUE TO APIS SELECTION)
* [ ] User can compose a post or resource and post it
(Details pending due to APIs selection)
* [ ] Users will be able to search the resources they want by title and will see them displayed.
* [ ] In Search view you can filter the results by category (All (default), books, music, news, pictures, videos and posts by other users).
* [ ] The style of every item as well as the detail view depends on the type of item (music, news, books, etc).
* [ ] Users can see the resources he or she saved with the same layout and details view used in search activity.
* [ ] Users can see a user's profile
* [ ] User can see the description of the user, number of posts and user's posts.
* [ ] User can log out to his or her account
* [ ] User can change the profile image
* [ ] User can see notifications about commentaries in his or her posts.
* [ ] Down toolbar to navigate sections
* [ ] Custom Toolbar
* [ ] Commentaries can only be liked
* [ ] Use a gesture

**Optional Nice-to-have Stories**
* [ ] Spanish and English available for the app depending of the smartphone configuration language
* [ ] User can follow other users
* [ ] Infinite pagination everywhere it can be used.
* [ ] Follow de UI used in wireframes.
* [ ] Use fragments instead of activities in sections.
* [ ] Easter egg EUREKA
* [ ] Custom app icon
* [ ] Blur video as background in login and signup
* [ ] User can hear the music in the app
* [ ] Users can login with at least one third party service.
* [ ] While sign up assigned predefined random names and profile images
* [ ] Sort and filter resources by rating
* [ ] Users can filter resources just like in the search view.
* [ ] Users can see the number of followers and followers, as well as have a follow button in every profile.
* [ ] User can update the app or search for new updates
* [ ] User can set night/dark mode
* [ ] Users can choose their main color (color used in their posts and profile).
* [ ] User receive a notification every time a user's follow account post something



### 2. Screen Archetypes

* No specific screen
  * [ ] Down toolbar to navigate sections
  * [ ] Custom Toolbar
  * [ ] Commentaries can only be liked
  * [ ] Always a resource preview has rating, comment icon and number of commentaries, and saved icon with number of times the resources has been saved.
  * [ ] Always a post has like, comment and save icon and numbers of each one.
  * [ ] Use a gesture

* Login
  * [ ] User can log in to his or her account
  * [ ] The current signed in user is persisted across app restarts
  * [ ] Successful login animation

* Signup
  * [ ] User can sign up to create a new account using Parse authentication

* Feed
  * [ ] User can see posts and resources that community have posted
  * [ ] Everything posted includes: photo and username of the user who posted it, timestamp and content.
  * [ ] Posts can be resources.
  * [ ] Every icon can do their respective functionality in posts and resources.
  * [ ] Every post follows what we said in no specific view.
  * [ ] This activity has a floating button to call compose activity
  * [ ] User can see the last 20 posted post and/or resources
  * [ ] User can pull to refresh the last 20 posts submitted
  * Details view
     * [ ] Every item has a details view.
     * [ ] Every item is clickable, and when it is clicked, you go to the detail view of that item.
     * [ ] In detail view you can see the commentaries of the resource
     * [ ] Every item can be commented, saved and rated or liked from details view.
     * [ ] User can use scroll gesture to update for newer commentaries, user will see the last 20 commentaries.
(FOR NOW I CANNOT SAY SPECIFICALLY HOW AN ITEM CONTENT WILL BE DUE TO APIS SELECTION)

* Compose Activity
  * [ ] User can compose a post or resource and post it
(Details pending due to APIs selection)

* Search
  * [ ] Users will be able to search the resources they want by title and will see them displayed.
  * [ ] In Search view you can filter the results by category (All (default), books, music, news, pictures, videos and posts by other users).
 * [ ] Every item show what we said about resources
  * [ ] Every item can be saved, rated or commented from the search view.
  * [ ] The style of every item as well as the detail view depends on the type of item (music, news, books, etc).

  * Details view
     * [ ] Every item has a detailed view.
     * [ ] Every item is clickable, and when it is clicked, you go to the detail view of that item.
     * [ ] In detail view you can see the commentaries of the resource
     * [ ] Every item can be commented, saved and rated from details view.
     * [ ] User can use scroll gesture to update for newer commentaries, user will see the last 20 commentaries.
(FOR NOW I CANNOT SAY SPECIFICALLY HOW AN ITEM CONTENT WILL BE DUE TO APIS SELECTION)

* Saved
  * [ ] Users can see the resources he or she saved with the same layout and details view used in search activity.

* Profile
  * [ ] Users can see a user's profile
  * [ ] User can see the description of the user, number of posts and user's posts.

* Settings
  * [ ] User can log out to his or her account
  * [ ] User can change the profile image

* Notifications
  * [ ] User can see notifications about commentaries in his or her posts.

**Optional Nice-to-have Stories**
* No specific view
  * [ ] Spanish and English available for the app depending of the smartphone configuration language
  * [ ] User can follow other users
  * [ ] Infinite pagination everywhere it can be used.
  * [ ] Follow de UI used in wireframes.
  * [ ] Use fragments instead of activities in sections.
  * [ ] Easter egg EUREKA
  * [ ] Custom app icon
* Login
  * [ ] Blur video as background
  * [ ] Users can login with at least one third party service.
* Signup
  * [ ] Blur video as background
  * [ ] While sign up assigned predefined random names and profile images
* Search
  * [ ] Sort and filter resources by rating
  * [ ] User can play music in the item view 
  * [ ] User can play music in the details view
* Saved
  * [ ] Users can filter resources just like in the search view.
* Profile
  * [ ] Users can see the number of followers and followers, as well as have a follow button in every profile.
* Settings
  * [ ] User can update the app or search for new updates
  * [ ] User can set night/dark mode
  * [ ] Users can choose their main color (color used in their posts and profile).
* Notifications
  * [ ] User receive a notification every time a user's follow account post something

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
- [OPTIONAL: List endpoints if using existing API such as Yelp]

THE FOLLOWING IS JUST A DRAFT, THE COMPLETE VERSION WILL BE AVAILABLE WHEN I HAVE THE INFORMATION ABOUT THE APIS THAT I WILL USE.

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
