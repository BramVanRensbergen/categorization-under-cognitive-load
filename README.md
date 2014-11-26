Categorization under Cognitive Load Experiment
=========================

Author: Bram Van Rensbergen (mail@bramvanrensbergen.com) 

Source: https://github.com/BramVanRensbergen/categorization-under-cognitive-load

This is the code to an experiment in which participants learn to categorize abstract stimuli, while simultaneously memorizing a dot pattern.
The experiment was created for personal use (ongoing research of a colleague), but anyone may use it if they like.


To use:
* Download the project, compile it using Java Development Kit, have the participant run it on a computer with Java Runtime Environment installed.
* Currently supports only JDK/JRE 7, but I'm in the progress of adding Java 8 support. 
* Options can be set in data/options.txt
* Instructions can be set in data/text.txt
* Button labels etc. are in Dutch, but it is quite easy to translate them: all language displayed to the user is defined in Text.java. Comments, code, output headers, ... are all in English.
* (After any changes to a .java file, you will have to recompile.)


Experiment
--------------

###Training Phase
Overview:
* Participants learn which stimuli belong to which group
* They see an image, and are asked to tell which of the two groups it belongs to
* They receive feedback on whether they were right or not
* They do not see all stimuli, but a subset of each group 
  - 6 of each group, by default
  - Which images are selected for the subset is randomized (per participant)
* After they have categorized both subsets, they are given feedback on their average accuracy on them
* Training continues until participants reach sufficient accuracy on a number of subsequent blocks (2 blocks with over 90% accuracy, by default)
* During this entire phase, participants also memorize and re-produce dot-patterns while they are categorizing stimuli


Flow:
* Participants see a dot pattern for 750ms
* Participants see an image, and indicate which group it belongs to
* Participants receive feedback on their choice (right/wrong)
* Participants see three more images, one by one, categorizing each
* Participants are presented with an empty grid and are asked to reproduce the pattern they saw earlier
* All above steps are repeated until all stimuli have been displayed once
* This means a block is completed, and participants receive info on their mean accuracy during that block
* A new block begins, with the same stimuli, in a newly randomized order
* All above steps continue until sufficient accuracy is obtained for the CATEGORIZATION task


Dot patterns:
* Four dots in a 4x4 grid
* The pattern is either easy (low load) or hard (high load)
* Easy pattern: a row or column of dots
* Hard pattern: a 'complex' arrangement of dots
* Difficulty is counterbalanced between participants, based on subject number
* A hard pattern fulfills three requirements:
  - No two vertically or horizontally adjacent dots, nor three or four dots in either diagonal. 
  - Complexity has to be high enough according to the CRC method described in: Ichikawa, S (1983). Verbal memory span, visual memory span, and their correlations with cognitive tasks. Japanese Psychological Research 25(4), 173-180.
  - Mirroring or rotating the pattern cannot create a duplicate
* See these papers, or the three classes in pattern.validity, for more information.


###Test Phase
Overview:
* Participants categorize the entire stimulus-set, without feedback, and without any dot patterns
* Entire stimulusset is used: 
  - This includes stimuli not seen during training phase by this participant
* Program continues for a set number of blocks, each containing all stimuli once
  - Default: 5 blocks
  
  
Flow:
* Participants see an image, and indicate which group it belongs to
* The above continues until all stimuli have been displayed once
* A new block begins, with all images in a newly randomized order
* The above continues until all blocks are completed (default 5)


###Counterbalance and randomisation
* Group labels are counterbalanced between participants
  - Counterbalanced based on subject number 
  - For half of participants, data/options.txt/group1name refers to group1, and group2name to group2, for the other half, this is reversed
* Participants categorize using two buttons, one for each group
  - Which button is on the left, and which is on the right, is also counterbalanced between participants, based on subject number
  - This done independently of the grouplabel balancing
* Half of the participants receive difficult dot patterns, the other half receive easy dot patterns
  - This is also counterbalanced, based on subject number
* Which subset of stimuli is displayed during the training phase is randomized per participant
  - In other words, not everyone sees the same stimuli
* Trial order
  - During training phase, blocks contain 2 subsets of 6 trials (by default)
  - During test phase, blocks contain 2 sets of 18 stimuli
  - The order of presentation within each block is fully randomized 
  
