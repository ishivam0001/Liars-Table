# 🎮 Liar's Table

Liar's Table is a console-based card game developed in Java as part of our Object-Oriented Programming (OOP) course project. Inspired by the Roblox game of the same name, this project focuses on bluffing, strategy, and decision-making. Players compete against AI-controlled opponents by claiming to play specific cards while trying to catch others bluffing.

The project was built to strengthen our understanding of Java, OOP concepts, and data structures by applying them to a real-world game.

---

## Features

- Human player vs AI bots
- Bluff and challenge system
- Random table card selection
- Insight Tokens to reveal a hidden card
- Special Sabotage Cards
- Russian Roulette-style penalty system
- Automatic round reset
- Input validation for better gameplay

---

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Java Collections Framework

---

## OOP Concepts Used

This project demonstrates the core principles of Object-Oriented Programming:

- **Abstraction** using abstract classes and interfaces
- **Encapsulation** to protect player and game data
- **Inheritance** for different card types
- **Polymorphism** through method overriding

---

## Data Structures Used

- **Stack** – Used to manage the deck of cards.
- **Queue** – Stores cards played during a turn.
- **LinkedList** – Represents each player's hand.
- **ArrayList** – Maintains the list of players.
- **HashMap** – Keeps track of each player's penalty glasses.

---

## Project Structure

```
Liars-Table/
│
├── src/
│   ├── Main.java
│   ├── Game.java
│   ├── Player.java
│   ├── Deck.java
│   ├── Card.java
│   ├── CardType.java
│   ├── NormalCard.java
│   ├── SabotageCard.java
│   ├── SabotageEffect.java
│   ├── PenaltySystem.java
│   ├── Glass.java
│   └── Playable.java
│
├── README.md
└── .gitignore
```

---

## How to Run

1. Clone this repository.

```bash
git clone https://github.com/your-username/Liars-Table.git
```

2. Open the project in any Java IDE such as IntelliJ IDEA, Eclipse, or Visual Studio Code.

3. Compile the project.

4. Run `Main.java`.

5. Enter your name, choose the number of AI bots, and start playing.

---

## Gameplay

- A random table card (King, Queen, or Ace) is selected at the beginning of each round.
- Players place one to three cards face down while claiming they match the table card.
- The next player can either accept the claim or call a bluff.
- If the bluff is successful, the challenger receives the penalty.
- If the bluff is exposed, the player who lied is penalized.
- The penalty involves choosing one of four glasses, where one contains poison.
- The game continues until only one player remains.

---

## Special Mechanics

### Insight Tokens

Each player starts with two Insight Tokens. These tokens allow a player to secretly look at one previously played card before deciding whether to challenge another player.

### Sabotage Cards

Sabotage cards introduce unexpected twists during the game.

Current effects include:

- Swap Hands
- Reverse Turn Order

---

## Future Improvements

Some features that could be added in future versions include:

- Graphical User Interface (JavaFX or Swing)
- Online multiplayer mode
- Improved AI decision-making
- Additional sabotage cards
- Game statistics and leaderboard
- Save and load game functionality

---

## Contributors

- Shivam Singh
- Jonathan Alexander Soerianto
- Vatry Avient Mahardika
- Dylan Martin Halim
- Amit Thakur
- Anshul Sidana
- Divyanshi Walia

---

## License

This project was developed for educational purposes as part of an Object-Oriented Programming course.
