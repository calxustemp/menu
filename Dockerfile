FROM anapsix/alpine-java:8

EXPOSE 4567

ADD target/menu-jar-with-dependencies.jar /menu-jar-with-dependencies.jar

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,address=8568,suspend=n", "-jar", "/menu-jar-with-dependencies.jar"]