# Super Mario Bros Case Study

A custom 2D Java physics engine demonstration project implementing a Super Mario Bros gameplay case study. Built completely from scratch using standard Java libraries (`javax.swing` and `java.awt`), this project features modular engines for rendering, collision detection, rigid body physics, and user input handling.

---

## 🛠️ Requirements

* **Java Development Kit (JDK):** Version 17 or higher (tested and running on Java 21).
* **IDE / Code Editor:** Visual Studio Code or Eclipse IDE.

---

## 🚀 How to Run

### Method 1: Running in VS Code

1. Open **Visual Studio Code**.
2. Go to **File** > **Open Folder...** and select the `MarioBros_Final` directory.
3. Make sure you have the **Extension Pack for Java** installed.
4. In the File Explorer, navigate to:
   `src/studyCase/superMarioBros.java`
5. Click the **Run** button (▶️) located at the top-right corner of the editor, or right-click inside the file and select **Run Java**.

---

### Method 2: Running in Eclipse IDE

1. Import the project into your Eclipse workspace (`File` > `Import...` > `General` > `Existing Projects into Workspace`).
2. In the **Package Explorer**, expand the folder hierarchy:
   `MarioBros_Final` ➔ `src` ➔ `studyCase` ➔ `superMarioBros.java`
3. Select `superMarioBros.java` and click the green **Run** button (▶️) on the top toolbar (or right-click `superMarioBros.java` and select **Run As** > **Java Application**).

---

### Method 3: Running from Terminal (VS Code or Standalone)

1. Open your terminal or PowerShell and navigate to the project root folder `MarioBros_Final`:
   ```bash
   cd MarioBros_Final
   ```
2. Execute the compiled project using the `-cp` (classpath) flag pointing to the `bin` directory:
   ```bash
   java -cp bin studyCase.superMarioBros
   ```

---

## 📂 Project Structure

```text
MarioBros_Final/
├── .classpath                  # Eclipse classpath configuration
├── .project                    # Eclipse project configuration
├── bin/                        # Pre-compiled .class output directory
└── src/                        # Source code files
    ├── game/                   # Framework base classes
    │   ├── Enemy.java
    │   ├── GameObject.java
    │   ├── GameUI.java
    │   ├── Integrator.java
    │   ├── Platform.java
    │   ├── Player.java
    │   ├── PowerUP.java
    │   └── Stage.java
    │
    ├── gameInputs/             # User input handling
    │   └── KeyboardInputs.java
    │
    ├── graphicsEngine/         # Custom rendering and window management
    │   ├── Background.java
    │   ├── FontLoader.java
    │   ├── GamePanel.java
    │   └── GameWindow.java
    │
    ├── images/                 # Game sprites, blocks, and UI textures
    │   ├── 1-UP.png
    │   ├── fontMayus.png
    │   ├── spr_BrickBlock.png
    │   ├── spr_Coin.png
    │   ├── spr_CoinIcon.png
    │   ├── spr_EmptyBlock.png
    │   ├── spr_floorTile.png
    │   ├── spr_Goomba.png
    │   ├── spr_Mario.png
    │   ├── spr_Mushroom.png
    │   ├── spr_Pipe.png
    │   ├── spr_QBlock.png
    │   └── spr_stairBlock.png
    │
    ├── physicsEngine/          # Custom 2D Physics and Collision system
    │   ├── Collider2D.java
    │   ├── CollisionHelper.java
    │   ├── RigidBody.java
    │   ├── RulerOfEverything.java
    │   └── Vector2D.java
    │
    └── studyCase/              # Mario Bros implementation & level logic
        ├── Block.java
        ├── BrickBlock.java
        ├── BrickDebris.java
        ├── Coin.java
        ├── Floor.java
        ├── Goomba.java
        ├── Lvl1s1.java
        ├── Lvltest.java
        ├── Mario.java
        ├── MarioHUD.java
        ├── marioIntegrator.java
        ├── Mushroom.java
        ├── Pipe.java
        ├── QBlock.java
        └── superMarioBros.java # Main entry point
```

---

## 🎮 Controls

* **A / D** or **Left / Right Arrow Keys:** Move Left / Right
* **W / Spacebar / Up Arrow:** Jump
* **Shift:** Sprint / Acceleration

---

## 🏗️ Technical Architecture Highlights

* **Vector Mathematics:** Custom `Vector2D` class handles positional vectors, velocity vectors, and gravity application.
* **Physics & Collisions:** The engine utilizes a central collision supervisor (`RulerOfEverything`) along with `Collider2D` and `RigidBody` components to calculate bounding box collisions and platform resolution.
* **Custom Graphics Engine:** Double-buffered rendering pipeline implemented using Swing's `JPanel` and `Graphics2D` (`GamePanel`), managing custom tilemaps and dynamic visual rendering.


## 👥 Authors
* **Agustín Gradilla**
* Alejandro Fernandez Arias (@alxfdezar)
