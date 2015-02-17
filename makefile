game:
	if [ ! -e bin/ ];then mkdir bin;fi
	cp -r images/ bin/
	javac -d bin/ src/*.java
jar:
	make game
	jar cfe floodlord.jar MainWindow -C bin .
