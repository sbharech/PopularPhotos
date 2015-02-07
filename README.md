Instagram Photo Viewer Demo

This is an Android demo application for displaying popular photos from instagram.

Time spent: 10 hours spent in total

Completed user stories:
1. User can scroll through current popular photos from Instagram - DONE

2. For each photo displayed, user can see the following details:
  Graphic, Caption, Username
  (Optional) relative timestamp, like count, user profile image - DONE. Additionally there will be a video icon on top right of the image if it is of video type.

3.Advanced: Add pull-to-refresh for popular stream with SwipeRefreshLayout - DONE

4.Advanced: Show latest comment for each photo (bonus: show last 2 comments) - DONE

5.Advanced: Display each photo with the same style and proportions as the real Instagram (see screens below) - DONE

6.Advanced: Display each user profile image using a RoundedImageView - DONE

7.Advanced: Display a nice default placeholder graphic for each image during loading (read more about Picasso) - DONE

8.Advanced: Improve the user interface through styling and coloring  - DONE. Could have done better.

9.Bonus: Allow user to view all comments for an image within a separate screen or a dialog fragment - DONE

10. Additional features:
    i. While scrolling the images, the title remains tills the entire photo item goes up/down.
    ii. Network error information if http client throws error.
    
Questions:

1. If we want to show certain piece of info conditionally (let's say view count/comment count only if they are non zero), we can hide the view by making it invisible. But still it takes up the space. For this, do we have to have 2 layout or we can achieve it programatically.

2. If network error happens, the list is empty. After that pull to refresh is not working. 

Walkthrough of all user stories:
