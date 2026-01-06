# 🐝 Spelling Bee

A Java implementation of the popular New York Times Spelling Bee word puzzle game, built as part of Fundies 2 coursework at Northeastern University.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)

## 🎮 How to Play

1. Enter a 7-letter puzzle (e.g., "AGILENT") in the Puzzle field
2. The first letter becomes the center letter (required in every word)
3. Form words using only the 7 letters
4. Each word must:
   - Be at least 4 letters long
   - Include the center letter
   - Use only letters from the puzzle (letters can be reused)
5. Score points for each valid word
6. Find the **pangram** (uses all 7 letters) for bonus points!

## ✨ Features

- **Interactive GUI** — Clean graphical interface with hexagonal letter display
- **Word Validation** — Checks against English dictionary (370,000+ words)
- **Scoring System** — Points based on word length with pangram bonus
- **Shuffle** — Randomize letter positions to spot new words
- **Solve** — Reveal all possible words for the puzzle

## 🛠 Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java |
| GUI | SpellingBeeGraphics (edu.willamette) |
| IDE | IntelliJ IDEA |

## 📁 Project Structure

```
SpellingBee/
├── src/
│   └── student/
│       └── SpellingBee.java    # Main game logic
├── res/
│   └── EnglishWords.txt        # Dictionary file
├── classes/
│   └── edu/willamette/cs1/     # Graphics library
└── README.md
```

## 🎯 Game Rules

| Rule | Description |
|------|-------------|
| Minimum Length | Words must be at least 4 letters |
| Center Letter | Every word must contain the center letter |
| Letter Pool | Only use letters from the 7-letter puzzle |
| Reuse | Letters can be used multiple times |
| Pangram | Use all 7 letters for a 7-point bonus |

## 📊 Scoring

- **4-letter word** — 1 point
- **5+ letter word** — 1 point per letter
- **Pangram bonus** — +7 points

## 🖼 Screenshots

![Start Screen](https://github.com/user-attachments/assets/7e94da6d-844f-44e6-9891-23a196037f5b)
![Initial Game](https://github.com/user-attachments/assets/61ef2e12-4318-4f1b-bb73-20ec27af36ff)
![Mid Game](https://github.com/user-attachments/assets/d4fe4a99-658f-4b89-80b2-d53d59db1173)
![Solved Game](https://github.com/user-attachments/assets/b62ff5cc-4349-413c-b03a-1e1ec5fddb79)

## 🤝 Contact

**Timothy Bernardo**  
- GitHub: [@timothybernardo](https://github.com/timothybernardo)
- LinkedIn: [timothybernardo](https://www.linkedin.com/in/timothybernardo)
- Email: bernardo.t@northeastern.edu
