# Robots-PencilsProblem
This is a simple application that retrieve comments already made in the past in the Comment Main Activity, give users the possibility to 
write their own comment, and save that comment in the database once the user has written its comment. 

It uses different external libraries that are: Firebase-Realtime-Database, RecyclerView, CardView, and Picasso. 

If we would like to modify a comment in the future that is already stored in the databse, all we have to do is to loop through all 
comments that have been made in the past, search for a specific set of keywords that are included in that comment and make sure the user 
name of the actual comment is the same as the one that we would like to retrieve. 

For Example: if Robots & Pencils makes a comment saying: Hi, I'm robot & Pencils and I create cool softwares. 

We can loop through the comment list that we already retrieved from the database, 
search for words within that sentences for instance trying to match the following: "create cool softwares". 
In Addition, if the user name matches the name that we want to look for, in this case: Robots & Pencils, 
Then we can retireve that parent node of that comment, modifiy it's comment content, and re save it in the database. 

THE METHOD IN QUESTION IS CALLED modifyingAnExistingComment from CommentHelper class. 
