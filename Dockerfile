FROM azul/zulu-openjdk:21
ADD target/user-posts-0.0.1-SNAPSHOT.jar user-posts.jar
ENTRYPOINT ["java", "-jar", "user-posts.jar"]
