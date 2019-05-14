# 518selfproject



Shaochong Wang 518 personal project
Event recommendation system
git: https://github.com/wscwuditiancai/518selfproject
website link: http://ec2-18-191-59-220.us-east-2.compute.amazonaws.com:8080/

Something about this project

This is a class project learning from a online class. Total relization time is about 80 hours, spent additional 40+ hours to add additional features.

Additional changes:
adding email to sql table, change index.html main.js correspondingly
adding auto email function to the website (send email when register)
adding auto chatbot to website(can show my favorite and recommend items)
adding word-cloud to website

User use cases
1.User can register,login,logout
1.1 User will receive a email notification after register
1.2 User can have a profile page, they can reset passwords, update personal information
2.User can see the events based on his location
3.User can like/unlike the events
4.User can see the liked events
5.User will see recommended events based on their liked events
6.User can see a word cloud based on their liked events


Overall Design:

![Image of overall1](https://s3.us-east-2.amazonaws.com/shawnphotoupload/518git1.jpg)

![Image of overall2](https://s3.us-east-2.amazonaws.com/shawnphotoupload/518git2.jpg)


Database Design:

![Image of database](https://s3.us-east-2.amazonaws.com/shawnphotoupload/518git3.jpg)





TicketMaster API Document:
https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/#search-events-v2

Details about recommendation system algorithm
Content based recommendation because not enough information about user-event relations are generated.
I will recommend the events that shares the most similarities to the events the user already liked.

Recall and precision considerations:
Because this is a event recommendation website, we do not worry about the recommending some events to users that this user does not actually like. We want to recommend all events to user that he might likes. As a result, in this project I value Recall over Precision.


Future I can implement the following algorithms:
1.  Item-based method (not included in this project)
This will recommend event A to user ,when this user already likes event B, as we found that there are many users like event B also like event A.
![Image of algorithm](https://s3.us-east-2.amazonaws.com/shawnphotoupload/518git4.jpg)

Q(i,k) means the k events that is similar to event i.
N(u) is the union of events that user u likes.
Sij is the how similar event i to event j.
Ruj is how much the user u likes the event j.

2.User-based method(not in this project )
First we need to know how similar the users are (based on things they like, or some other factors they users identify themselves)
We recommend Items to users based on other users that has similar profile.
Map reduce

algorithm behind






Things to mention:
1.The whole website uses a mvc design idea

2.The design uses  
Singleton
Factory method

Features:
The website hosts sessions for users, they will be redirected to login.html if they did not login or has logged out.
The system uses TicketMaster API to search for events, but when the connections is cutoff users can still see their liked events because they are stored in local database
2nd version uses MongoDB to achieve better …..
Tested with Junit
Tested with Apache Jmeter

Notes:
1.Used Postman for debugging servlet connections
2.Used MAMP for initial Mysql tables creation and easier visualization
3.Initially used Mysql and then upgrade to use MongoDb for ...



