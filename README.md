
# God Of Cards

## A card-dueling game of skill, intuition, and luck.

***God of Cards*** is a single-player, card-based RPG video game in which players attempt to battle against the ranks of
Greek Gods
to attain reign over Mount Olympus and be deemed the **God of Cards**.  

This is a **turn-based** game in which the player and their opponents will take turns playing cards to deal damage to
one another or apply different effects to help themselves. Only one card is played each turn. As the player progressively
defeats more opponents, their card deck will grow in size with stronger and more strategic cards to flesh out their 
arsenal and contribute to their protection and strength whilst fighting future opponents.  

There are 4 types of cards:

- Attack Cards
  - Deals *x* damage to opponent *(Minimum Damage <= x <= Maximum Damage)*
- Shield Cards *
  - Applies a resistance effect to player which subtracts *x* amount of damage from the next incoming attack from 
  opponent.
- Pierce Cards *
  - Applies a strengthening effect to player which adds *x* amount of damage to player's next outgoing attack.
- Healing Cards
  - Applies a healing effect to player which replenishes *x* amount of health.

**these cards can be stacked to progressively buff up an outgoing attack or weaken an incoming attack.*  

This game is aimed towards an audience of all ages, but specifically for gamers who enjoy strategic turn-based dueling 
games (like Pokemon!). This project is of specific interest to me because of the types of games I grew up playing as a 
kid and the fun I found in outplaying my opponents with proper strategy. *Wizard101* in particular is an MMO RPG video 
game that I played a lot as a kid where players create a wizard and defeat enemies and bosses with their school-specific
card spells. This program is essentially a simplistic, stripped-down version of that game which I thought would be a fun
challenge to program for this project.

## User Stories (Phase 1):

- As a game designer, I want the player to play through multiple boss fights (levels) that incrementally increase in
difficulty, where bosses increase in health and have a stronger arsenal of cards.
- As a game designer, I want there to be a degree of variability in attack damage and a degree of unpredictability as to
what move a boss will choose to take each turn.
- As a user, I want to be able to add multiple different stronger Cards to my CardDeck as I defeat my opponents
and fight future stronger opponents.
- As a user, I want to be given a choice of what new Card I want to add to my CardDeck after each successful battle. 

## User Stories (Phase 2):

- As a user, I want to be given the option to save my progress after each successful battle and be given the option 
to quit the game before the start of my next battle if I want to.
- As a user, when I start up the application, I want to be given the option to load my saved progress and start right
before my next battle or start from scratch.

## User Stories (Phase 3):

- As a user, I want to be able to see a visual representation of my health and individual icons
  of different cards in my card deck during battle.
- As a user, I want to be able to see visual icons of the cards that I can choose from after each successful battle.
- As a game designer, I want to add background images to each battle showing the current Greek God/Goddess that the 
player is fighting along with the opponent's health and individual cards in the opponent's card deck.

## Phase 4: Task 2

![Sample EventLog](./data/OtherImages/EventLog.png)

## Phase 4: Task 3

While the UML Class Diagram below looks appealing and somewhat simplistic, the code behind it was messy and lengthy in 
some instances. If I had a chance to redesign this project, I would...
- Refactor the Character class and make it abstract. I would also make smaller Boss and Player subclasses that extend 
the Character class and deal with more specified behaviour that differed between the two. This was instead dealt with in
the UI class which made it longer than it needed to be.
- Refactor the GUI class and split it up into separate smaller components (classes) to improve the readability and 
cohesion of the code that constructed the GUI (i.e. different classes for the main JPanels used in the GUI).
- Figure out a way to utilize less JOptionPanes in the GUI and more in-frame buttons that provided the same
functionality. This was a challenge for me in this project because the foundational structure of my GUI was based on a 
loop which made waiting for user input difficult without the use of JOptionPanes (which did exactly that).

![Sample EventLog](./data/UML_Design_Diagram.png)
