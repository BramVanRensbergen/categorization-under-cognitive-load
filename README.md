Categorization under Cognitive Load Experiment
=========================

Author: Bram Van Rensbergen (mail@bramvanrensbergen.com) 

Source: https://github.com/BramVanRensbergen/categorization-under-cognitive-load

This is the code to an experiment in which participants categorize abstract stimuli, while simultaneously memorizing a dot pattern.
The experiment was created for personal use (ongoing research), but anyone may use it if they like.

This project is not finished yet.

To use:
* Download the project, compile it using Java Development Kit, have the participant run it on a computer with Java Runtime Environment installed.
* Many program options can be tweaked in Options.java.
* Instructions/buttons/etc during the experiment are in Dutch, but it is quite easy to translate them: all language displayed to the user is defined in Text.java. Comments, code, output headers, ... are all in English.
* (After any changes to a .java file, you will have to recompile.)


Experiment
--------------

Dot patterns:
* Four dots in a 4x4 grid
* The pattern is either easy (low load) or hard (high load)
* Easy pattern: a row or column of dots
* Hard pattern: a 'complex' arrangement of dots
* A hard pattern fulfills three requirements:
  - No two vertically or horizontally adjacent dots, nor three or four dots in either diagonal. 
  - Complexity has to be high enough according to the CRC method described in: Ichikawa, S (1983). Verbal memory span, visual memory span, and their correlations with cognitive tasks. Japanese Psychological Research 25(4), 173-180.
  - Mirroring or rotating the pattern cannot create a duplicate
* See these papers, or the three classes in pattern.validity, for more information.

Experiment flow:
* Participants see a dot pattern for 750ms
* Participants see a cue, and give up to three associations to it
* Participants see four more cues, one by one, giving up to three associations to each
* Participants are presented with an empty grid and are asked to reproduce the pattern they saw earlier
* The above four steps are repeated as long as there cues left






