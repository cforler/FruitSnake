CC=javac
DIR=fs

all: FruitSnake FruitSnake.jar run

FruitSnake:
	$(CC) $(DIR)/*.java


FruitSnake.jar:
	jar cfmv $@ Manifest $(DIR)/*.class  $(DIR)/images/*


run: FruitSnake.jar
	java  -jar FruitSnake.jar

clean: 
	$(RM) $(DIR)/*~  $(DIR)/*.class FruitSnake.jar
