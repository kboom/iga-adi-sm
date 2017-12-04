# Isogeometric Alternating Directions Implicit Shared Memory Solver (IGA-ADI-SM)

This numerical solver can be used for solving a certain subset of problems
in the field of Isogeometric Analysis.

## Usage

The program can be run by providing configuration properties all of which have default values.

### Common configuration properties

Even if every problem defines its own set of configuration properties
there is a subset common to every problem. Those are:

- **--log** / **-l** => logging detailed results (**off** by default)
- **--plot** / **-p** => plotting results (**on** by default)
- **--problem-size** / **-s** => specifying problem size (**12** by default)
- **--max-threads** / **-t** => enforcing maximum number of threads used (**12** by default)


### Example problems

This software comes with several exemplary problems pre-implemented.
Those are:
- Projection problem (**projection**)
- Terrain mapping incl. SVD approximation (**terrain-svd**)
- Heat Transfer simulation (**heat**)
- Flood simulation (**flood**)

The type of problem is selected by a configuration parameter **--problem**
supplied with the short name from above, for instance **--problem projection**.

#### Projection problem

The projection problem solved is a simple 
```
f(x) = (x - MaxX/2)^2 + (y - MaxY / 2)^2
```
function projection. No additional configuration properties may be provided.

![Projection](docs/projection.png)

#### Heat transfer problem

In this example an exemplary problem of heat transfer is being solved. 
A ball of heat is put into the center of the plane gradually heating the surface. 
The grid size is 24x24 though it can be any value.

Additional configuration properties are:
- **--delta** / **-d** => time step (**0.001** by default)
- **--steps** / **-o** => number of time steps to run simulation for (**100** by default)

![Heat transfer simulation](heat.gif)


#### Terrain mapping

The terrain mapping takes as input the path to the file containing triplets of x,y,z values in each line.
It does the projection of it and then Singular Value Decomposition (SVD). You can choose what rank to use.
If no file is provided the program uses a static terrain generated using a simple math function.

Additional configuration properties are:
- **--terrain-file** => terrain file to use (**none** by default)
- **--scale** => scale (**1** by default)
- **--xOffset** => time step (**0** by default)
- **--yOffset** => time step (**0** by default)
- **--ranks** => time step (**10** by default)

![Terrain2D](docs/terrain2d.png)
![Terrain3D](docs/terrain3d.png)

### Running from sources

The solver can easily be run from sources by launching the *Main* class with proper arguments.
Self-contained *Gradle* distribution contains all necessary dependencies and building steps.

### Running from binaries

Binaries can be downloaded from [here](https://github.com/kboom/iga-adi-sm/tree/master/dist).
Extract and run proper executable file from the *bin* directory.
Make sure *JRE* (1.8+) is installed on the system and available on the *PATH*.


### Solving different problem

Code can be easily modified to solve a different problem. 
If you need to solve a static problem with only one time step then you just need to create proper implementation of **ProblemManager**,
which constructs your unique **Problem** instance (or just uses lambda).


```java
public class ProjectionProblemManager extends AbstractProblemManager {

    public ProjectionProblemManager(SolverConfiguration config) {
        super(config);
    }

    @Override
    public IterativeProblem getProblem() {
        return new SingleRunProblem() {

            @Override
            protected Problem getProblem() {
                return (x, y) -> x + y; // your numeric result
            }

        };
    }

}
```
This way the solver will produce a 3D visualization of the projection done.
If you want to pre-process the input or post-process the results you can override respective methods which are available and contain default implementations.

If you want to solve a time-dependent problem or any iterative problem in general do not use the **SingleRunProblem** class as the base class for your problem.
Rather that that implement the **IterativeProblem** interface.

### Want to contribute?

Feel free to contribute to this project. Merge requests will be reviewed and accepted upon verification.

### Contact & More information

* [http://home.agh.edu.pl/~paszynsk/](http://home.agh.edu.pl/~paszynsk/)
* [http://home.agh.edu.pl/~kbhit/](http://home.agh.edu.pl/~kbhit/)



