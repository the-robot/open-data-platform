.PHONY: build run

build:
	./gradlew build

hot-reload:
	./gradlew -t :bootJar

dev:
	./gradlew bootRun

stop:
	./gradlew --stop

run:
	java -jar build/libs/open-data-platform-0.0.1-SNAPSHOT.jar
