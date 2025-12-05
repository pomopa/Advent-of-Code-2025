# 🎄 Advent of Code 2025 🎅🏻
### Author: Pol Monné Parera

Welcome to my [Advent of Code 2025](https://adventofcode.com) repository!  
This project contains my personal solutions for each day's coding challenge, written with clarity, modularity, and scalability in mind.

I aim to provide elegant, well-structured code and useful utilities that allow each puzzle to be solved cleanly and consistently.

> ⚠️ **Important:** I strongly discourage anyone from viewing this repository before attempting their own solutions. Doing so would spoil the challenge and is considered bad practice. Please try the puzzles on your own first!

---

## 📁 Repository Structure

``` bash
.
├── LICENSE
├── README.md
└── src
    ├── Main.java
    ├── core
    │   ├── InputReader.java
    │   ├── Solver.java
    │   └── SolverRegistry.java
    ├── days
    │   ├── day01
    │   │   ├── Day01Input.txt
    │   │   ├── Day01InputTest.txt
    │   │   └── SecretEntrance.java
    │   ├── day02
    │   │   ├── Day02Input.txt
    │   │   ├── Day02InputTest.txt
    │   │   └── GiftShop.java
    │   └── ...
    └── ui
        └── TerminalUI.java
```


**Key points:**

- Each day has its own **class** (e.g., `SecretEntrance.java` for day 1).
- Each puzzle has **two input files**: `dayXXInputTest.txt` and `dayXXInput.txt`.
- A **terminal UI** allows:
  - Selecting the day  
  - Selecting test or real dataset  
  - Choosing silver or gold puzzle
- A general-purpose **solver interface** ensures consistency across all days.

---

## 🧩 Goals of This Repository

- 🚀 Complete all 12 days of Advent of Code 2025  
- 📚 Maintain clean, readable, and well-documented Java code  
- 🧪 Include test inputs, real inputs, and reproducible outputs  
- 🖥️ Provide an interactive terminal interface to run any day  
- 🧱 Offer reusable utilities for parsing, timing, and displaying results

---

## 📅 Progress Tracker
| Day |	Silver | Gold |
|-----|--------|------|
| 01	| ✅ |	✅ |
| 02	| ✅ |	✅ |
| 03	| ✅ |	✅ |
| 04 |	⏳ |	⏳ |

---

## 🖥️ How to Run

1. **Clone the repository**

```bash
git clone https://github.com/pomopa/advent-of-code-2025.git
cd advent-of-code-2025
```
2. **Run from your favorite IDE or terminal**

---

## 📜 License
This repository is available under the MIT License.

---

Merry Christmas! 🎄
