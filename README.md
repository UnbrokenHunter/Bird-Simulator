# Bird Simulator

## Overview

This project is a Java implementation of the Boids simulation, an artificial life program developed by Craig Reynolds in 1986, which simulates the flocking behaviour of birds. This implementation uses Java to create a visually engaging simulation demonstrating alignment, cohesion, and separation behaviors among the boids.

## Features

- **Real-time Simulation:** Watch as the flock moves in real-time, exhibiting natural flocking behavior.
- **Customizable Parameters:** Users can adjust parameters such as the number of boids, speed, and the weights of alignment, cohesion, and separation to observe different behaviors via the UI.
- **Resizable Simulation Space:** Users can resize the program's window, which will automatilly adjust the simulation, allowing Boids to move into the new space. 
- **Dynamic Colors:** Boid colors are dynamically changed based on the number of Boids in it's radius. The color pallate can be changed via the Color tab.
- **Hawks:** Users can enable Hawks, of which Boids will try activly avoid, moving away when within their range.
- **Hawk Control:** By clicking the "Control" button in the Hawk tab, the user can control a Hawk with their cursor.
- **Populaton Balance:** Users can allow both Hawks to kill Boids when they get too close, as well as Boids reproducing to keep the population balanced. These can be independantly enabled via their respective tabs.
- **Barriers:** Users can enable barriers that Boids will bounce off of. They can be drawn by clicking on the screen and dragging.
- **Safety Barriers:** Users can also enable safety barriers that Hawks will bounce off of, but Boids can pass through. They can be drawn by clicking on the barriers tab, and switching to Mode 1. Then they can be drawn by clicking on the screen and dragging.
- **Barrier Extras:** A grid can be enabled to allow for more accurate placements. You can also toggle bounce to determine whether the walls should act as barriers, or be able to be passed through. 
- **Music:** Users can enable "Music" that will be randomly generated based off of a selected scale. It will then play a random note frm the scale when a boid comes into contact with a barrier. Bass notes will be played when a Hawk comes into contact with a safety barrier, and a drum sound will be played when a bird is killed by a Hawk.
- **Pause:** Users can pause the simulation and adjust individual Boids parameters by clicking the Pause Button.
- **Restart:** Users can restart the simulation, moving all of the Boids to the center of the screren, but keeping all of their current settings.

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Any Java IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository to your local machine:

```bash
git clone https://github.com/unbroken-hunter/Bird-Simulator.git
```
