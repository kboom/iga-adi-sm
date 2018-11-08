# ex2: Measuring performance

This exercise is meant to provide answers to the questions
regarding the performance aspect of the
*Isogeometric Alternating-Directions Shared Memory Solver* (IGA-ADI-SM).


## Prerequisites

The same as in ex1.

## Performance measurement

We will measure the performance of the solver for various configuration parameters.
Create a script in a language of your choosing that would run the
solver a number of times, each time with different parameters,
collecting the computation times printed by the solver.

Use the following command

```bash
./gradlew run runShadow --args='--problem <PROBLEM_TYPE> --problem-size <PROBLEM_SIZE> --max-threads <MAX_THREADS> --steps 2'
```

The output of running this command is
```
14,143,15,181
```

Can you identify what particular numbers mean?

##### Questions
* Why are we using 2 time steps rather than one?
* Why are we restricting the number of treads?

### Serial execution

Run the above command for all of the following combinations:

> **PROBLEM_TYPE**: projection, heat

> **PROBLEM_SIZE**: 12, 24, 48, 96, 192, 384, 768, 1536

> **MAX_THREADS**: 1

Next, draw the following plots, one for each of the problems evaluated:

> total_time(problem_size)

> creation_time(problem_size)

> initialization_time(problem_size)

> solution_time(problem_size)

##### Questions
* Does the two problems behave any different?
* What stage of the computations takes the most time? Why?
* What is the time complexity?

### Parallel execution

Run the above command for all of the following combinations:

> **PROBLEM_TYPE**: heat

> **PROBLEM_SIZE**: 12, 384, 768, 1536

> **MAX_THREADS**: your_machine_threads * 4 down to 1

Next, plot the following charts (can be in 3D): 

> total_time(problem_size, max_threads)

> creation_time(problem_size, max_threads)

> initialization_time(problem_size, max_threads)

> solution_time(problem_size, max_threads)


##### Questions
* Does the number of available threads affect the computations time? If so, how? Why?
* What is the Amdahl law?
* Show how Amdahl law applies in here
* Based on the above, can you predict what the time complexity for a large number of threads would be?

## Performance optimization

The codebase has spots in which the language usage is not optimal.
Based on your knowledge and expertise try to identify as many as you can.
Next, try to improve the codebase, starting from straightforward changes.

Try to evaluate performance after your changes are introduced.
Can you identify the influence of each change you made on the
computations time or space?

##### Questions
* How can you approach introducing changes to as complex software as this?

