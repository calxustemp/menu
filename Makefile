build-docker : export DOCKER_NAMESPACE = localhost:8080/local
build-docker run-java : export APPLICATION = menu
build-docker run-java : export VERSION = 1.0-SNAPSHOT

build: build-java build-docker
run: run-java

build-java:
	$(info "***** Building menu service *****")
	@mvn clean install

build-docker:
	$(info "***** Building docker image *****")
	@docker build -t $(DOCKER_NAMESPACE)/$(APPLICATION):$(VERSION) .

run-java:
	$(info "***** Sparking jar *****")
	@java -jar target/$(APPLICATION)-$(VERSION).jar