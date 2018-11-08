# ex1: Running the simulations

This exercise will show you some examples of what simulations the
*Isogeometric Alternating-Directions Shared Memory Solver* (IGA-ADI-SM)
can perform. The question of *how does it do it* will be answered during the next two laboratories. 

## Prerequisites

Make sure you have JDK 1.8 installed.

``` bash
java -version
java version "1.8.0_172"
Java(TM) SE Runtime Environment (build 1.8.0_172-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.172-b11, mixed mode)
```

If you choose to browse the codebase in an editor,
make sure you have *Lombok* plugin installed and
that *annotation processing* is on. If in doubt,
ask you colleague first.

## Configuration options

Examine the contents of the following type 

> edu.iga.adi.sm.SolverConfiguration

##### Questions
* What are the configuration options available?
* Can you guess which of them are common to all problems?

## Build the solver

Issue the following command.
``` bash
./gradlew shadowJar 
```
A new directory, *dist* should be created at project root,
with a single file *iga-adi-sm.jar*.

##### Questions
* Could you explain what just happened?
* What are the contents of this *jar* file?
* Can you run the program using this archive? How? Try to.

## Solve a simple projection problem

The solver has to do *L2 projection* in every time step. 
We would like to represent a simple function

> f(x,y) = x + y

in a 2D *B-Spline* space.

In order to do so issue
``` bash
./gradlew run runShadow --args='--problem projection --plot'
```

##### Questions
* What is a L2 projection?
* What is it that you see as the result of computations?
* What are the actual results of computations (in math terms)?

## Solve a heat transfer problem

Heat transfer problem, as opposed to the problem above, is a relatively complex,
time-dependent problem. In order to run it issue
``` bash
./gradlew run runShadow --args='--problem heat --problem-size 48 --steps 100 --delta 0.01 --plot'
```

Experiment with input arguments value.
Note that there are only some valid values of the problem sizes:

> 12, 24, 48, 96, 192, 384, 768, 1536, 3072, 6144... 


##### Questions
* Can you explain what is happening on the chart?
* What is the formula for the available problem sizes? Why?
* What does the problem size parameter affect? What is the trade-off here?
* What does the delta parameter affect? What is the trade-off here?


## Solve a flooding problem

Lets see what happens if you pour a theoretical,
huge bucket of water, over our Voivodeship.

First, download a 50MB CSV file which contains coordinates and elevation.
Issue

``` bash
./download.terrain.sh
```

A new file *malopolskie.txt* file should appear in *terrain* directory under project root.

##### Represent it as a linear combination of 2D B-Splines

Before we run the simulations, we can observe the results of the initial L2 projection

``` bash
./gradlew run runShadow --args="--problem-size 1536 --plot --problem flood --steps 1 --terrain-file $(pwd)/terrain/malopolskie.txt --terrain-x-offset 600000 --terrain-y-offset 200000 --terrain-scale 10"
```

Experiment with changing:
* problem-size
* terrain-x-offset
* terrain-y-offset
* terrain-scale

##### See what happens in time

``` bash
./gradlew run runShadow --args="--problem-size 192 --plot --problem flood --steps 100 --delta 0.00001 --terrain-file /Users/kbhit/Sources/phd/iga-adi-sm/terrain/malopolskie.txt --terrain-x-offset 600000 --terrain-y-offset 200000 --terrain-scale 100"
```

* Try to position the bucket over interesting areas. Try changing the scale.
* Try to adjust delta. In particular, try to use larger values. Does this always work?

##### Questions
* Is the value of *delta* unrestricted? What other parameter does it depend on now? Why?

## Solve your own problem

The solver is flexible and extensible.
All of the above simulations, various in nature, are represented using the same core.
The architecture of the solver (especially the contents of the core package) will be analyzed in the next exercise.

Fow now, try to follow the structure of the already available problems

> edu.iga.adi.sm.problems.{projection,heat}

and try to design your own problem to solve.
Start with solving a simple projection of your choosing.
