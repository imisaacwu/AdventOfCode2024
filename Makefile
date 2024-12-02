DAYS := 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
TODAY := $(shell TZ=America/New_York date +%y%m%d)

.DEFAULT_GOAL := $(or $(filter $(TODAY:2412%=%),$(DAYS)), help)
.PHONY: $(DAYS) all help

input/day%.in:
	./fetch.sh $*

src/day%.java:
	DAY=$* envsubst < src/template.java > $@

$(DAYS): %: src/day%.java input/day%.in
	javac src/day$*.java -d out/
	java -cp out/ day$*

all: $(patsubst src/day%.java, input/day%.in, $(wildcard src/day*.java))
	javac src/day*.java -d out/
	for day in $(DAYS); do java -cp out/ day$$day; done

help:
	@echo 'usage: make [TARGET..]'
	@echo 'Automatically downloads input, sets up files, and runs solutions.'
	@echo
	@echo 'TARGET:'
	@echo '  {01..25}  run a specific day, e.g 01'
	@echo '  all       run all days'
	@echo '  help      show this help text'
	@echo
	@echo 'During the AoC month 'make' will run the current day's solution'
