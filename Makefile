
test-python:
	src/test/script/install-python.sh
	mvn clean
	mvn compile
	mvn test-compile
	python3 src/test/script/tests.py -f