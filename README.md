# Zombie Simulation — How It Works

This document describes how the zombie-sim simulation works and what algorithms it uses.

---

## 1. Overview

The simulation is a **discrete, grid-based agent simulation** where humans and zombies move on a 40×40 grid. Humans follow daily routines (work, home, store) and react to zombies. Zombies seek and infect humans. The simulation runs in real time using a JavaFX `AnimationTimer` that updates every frame (~60 times per second).

---

## 2. Data Structures

### 2.1 Grids

- **`gameGrid`** — 40×40 int array. Each cell holds an organism’s identity:
  - `0` = empty
  - `1–3` = human (child)
  - `4–7` = human (adult)
  - `8–9` = human (elder)
  - `10` = zombie

- **`environmentGrid`** — 40×40 int array for buildings:
  - `0` = empty
  - `1` = School (white)
  - `2` = Store (yellow)
  - `3` = House (light blue)
  - `4` = Work (light green)

### 2.2 Entities

- **`people`** — `ArrayList<Person>` of all humans
- **`zHorde`** — `ArrayList<Zombies>` of all zombies
- **`buildings`** — `ArrayList<Building>` of fixed buildings (schools, stores, houses, work)

---

## 3. Human Behavior (Routine System)

Humans use a **routine-based state machine**. Each human has a `routine` value that determines their goal:

| Routine | Meaning |
|---------|---------|
| 0 | Idle / wander |
| 1 | Go to work |
| 2 | Go to store |
| 3 | Go home |
| 4 | Go to school (children) |
| 5 | Flee from zombie |
| 6 | Attack zombie |
| 10 | Inside building (staying) |

### 3.1 Routine Selection Algorithm

Each frame, if a human has not yet decided (`getDecided() == false`):

1. **Zombie nearby (radius 5)?**
   - 65% chance → routine 6 (attack)
   - 35% chance → routine 5 (flee), and fear/battle power decrease

2. **No zombies in the world?**
   - Night → routine 3 (go home)
   - Day → depends on age:
     - Child (identity 1–3) → routine 4 (school)
     - Adult (identity 4–7) → hunger-based: store (2) or work (1)
     - Elder (identity 8–9) → routine 4 (school)

3. **Zombies exist but not decided yet?**
   - Night → hunger-based: store (2) or home (3)
   - Day → same as above

### 3.2 Movement

Movement is **time-throttled** (every ~300 ms):

- **Routine 6 (attack):** `moveTo` zombie’s location
- **Routine 5 (flee):** `moveAway` from zombie
- **Routines 1, 2, 3, 4:** `moveTo` closest building entrance
- **Routine 0 (idle):** `changeLoc` — random walk

---

## 4. Movement Algorithms

### 4.1 Directed Movement (`moveTo` / `moveAway`)

Uses **axis-aligned step logic**:

- Compare target (x, y) with current (x, y)
- Move one step toward (or away from) target on each axis
- If target cell is occupied or blocked → fall back to random walk (`changeLoc`)

### 4.2 Random Walk (`changeLoc`)

- Up to 7 attempts per call
- Each attempt: 50% chance to move ±1 on x, 50% on y
- Only move if destination is empty (gameGrid and environmentGrid both 0)
- Used when no clear path or when idle

### 4.3 Distance and Pathfinding

- **Manhattan distance** for “closest” queries:
  ```
  d = |x1 - x2| + |y1 - y2|
  ```
- `findClosestPerson`, `findClosestBuilding`, `findClosestBuildingSpec`, `getClosestEntrance` all use this
- No pathfinding; entities move one cell per step toward the chosen target

---

## 5. Zombie Behavior

### 5.1 Outdoor Infection

- If a zombie has a human within radius 1 → 65% chance to infect
- Infection: remove human from `people`, add new zombie at that location, update grid
- **Safeguard:** Only infect if the human is still in `people` (avoids multiple zombies infecting the same human in one frame)

### 5.2 Building Entry

- If a zombie is at a building entrance (radius 1) and not already inside → 2% chance to enter
- On entry: identity set to 0 (invisible), added to building’s `peopleInBuilding` conceptually (zombie is tracked as “in building”)

### 5.3 Building Infection

- Zombies inside a building can infect humans in that building
- 65% chance per zombie per frame
- Only one infection per human per frame (checked via `people.contains(p)`)
- New zombie appears at the infected human’s last location

### 5.4 Leaving Buildings

- After `stayTime` (building-specific, in nanoseconds), zombie leaves
- Identity restored, removed from building, placed back on grid

### 5.5 Zombie Movement

- If a human is within radius 3 → 65% chance to move toward that human
- Otherwise, after 500 ms, perform random walk (`changeLoc`)

---

## 6. Human vs. Zombie Combat

- If a human is in routine 5 or 6 and a zombie is within radius 1:
  - Uses `decideIfHappens(fear)` or `decideIfHappens(battlePower)` (0–9 scale)
  - If successful → zombie removed from `zHorde`, human returns to routine 0
- If human was fleeing (routine 5) and no zombie within radius 8 → stop fleeing, clear target

---

## 7. Probabilistic System (`decideIfHappens`)

Events are driven by a **probability ladder** based on a 0–9 “chance” value:

| Chance | Approx. probability |
|--------|----------------------|
| 0 | ~0.01% |
| 1 | ~15% |
| 2 | ~5% |
| 3 | ~40% |
| 4 | ~60% |
| 5 | ~65% |
| 6 | ~70% |
| 7 | ~80% |
| 8 | ~90% |
| 9 | ~95% |

Implementation: `randNum.nextInt(100000) > threshold`, with threshold chosen per chance level.

---

## 8. Time and Aging

- **Day/night:** Toggles every ~20 seconds (20 billion nanoseconds)
- **Hunger:** Increases every ~3 seconds; affects routine (store vs work)
- **Age:** Human identity increases every ~3 seconds (1→2→3→4→5→6→7), then stops
- **Movement throttle:** ~300 ms between moves for humans; ~300 ms or 500 ms for zombies

---

## 9. Building Types

| Type | Color | Purpose |
|------|-------|---------|
| 1 | White | Work |
| 2 | Yellow | Store |
| 3 | Light blue | House |
| 4 | Light green | School |

Each building has 4 entrances (one per side), a `stayTime`, and a `peopleInBuilding` list. Humans enter by reaching an entrance; zombies can enter and infect people inside.

---

## 10. Summary of Algorithms Used

| Component | Algorithm |
|-----------|-----------|
| Closest entity | Manhattan distance, linear scan |
| Movement | Axis-aligned step, fallback to random walk |
| Routine choice | State machine with probabilistic branches |
| Infection | Radius check + probability + list consistency checks |
| Combat | Radius check + probability (fear/battle power) |
| Random events | Threshold-based RNG (`decideIfHappens`) |


