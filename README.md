User-post

To store the user and post information into mysql.

Running locally

The project can run locally with postman.

Prerequisites

Java 21
Mysql
Kafka

Steps

1.Added the required dependency in pom.xml

2.updated application.yml file with mysql and kafka properties.

3.Added the dto,entity,serivce,controller and config.

4.Added the dockerifle and docker-compose.yml

5.Expose a REST API with the following endpoint
http://localhost:8083/user-posts/gather

6. Read users and posts from the following endpoint asynchronously with RestTemplate and kafka.
https://jsonplaceholder.typicode.com/users
https://jsonplaceholder.typicode.com/posts

7.Stroed in Mysql - user_posts table

8.Get the data from DB with below endpoint

http://localhost:8083/user-posts

{
        "postId": 1,
        "userName": "Leanne Graham",
        "userEmail": "Sincere@april.biz",
        "postTitle": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        "postBody": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    },
    {
        "postId": 2,
        "userName": "Leanne Graham",
        "userEmail": "Sincere@april.biz",
        "postTitle": "qui est esse",
        "postBody": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
    },
    {
        "postId": 3,
        "userName": "Leanne Graham",
        "userEmail": "Sincere@april.biz",
        "postTitle": "ea molestias quasi exercitationem repellat qui ipsa sit aut",
        "postBody": "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut"
    },
 .........





